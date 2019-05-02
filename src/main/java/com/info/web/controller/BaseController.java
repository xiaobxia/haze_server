package com.info.web.controller;

import java.util.*;

import javax.servlet.http.*;

import com.google.gson.Gson;
import com.info.back.utils.MD5codingLowCase;
import com.info.back.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import redis.clients.jedis.JedisCluster;

import com.info.back.utils.RequestUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.User;

@Slf4j
public class BaseController {

    public static final String MESSAGE = "message";
    public static final String LOGING_DEVICE_PREFIX = "device_";
    public static final int FRONT_USER_EXIST_TIME = 259200;// 3*24*60*60
    public static final int BACK_USER_EXIST_TIME = 259200;// 3*24*60*60

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 获取IP地址
     *
     */
    public String getIpAddr(HttpServletRequest request) {
        return RequestUtils.getIpAddr(request);
    }


    /**
     * 得到session中的admin user对象
     */
    public BackUser loginAdminUser(HttpServletRequest request) {
        return this.getSessionUser(request);
    }

    protected BackUser getSessionUser(HttpServletRequest request) {
        BackUser backUser;
        Cookie cookie = this.getSessionCookie(request);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            try {
                String userJson = jedisCluster.get(sessionId);
                Gson gson = new Gson();
                backUser = gson.fromJson(userJson, BackUser.class);
                return backUser;
            } catch (Exception e) {
                log.error(" loadAdminUser is error:{}", e);
                return null;
            }
        }
        return null;
    }

    protected void setSessionUser(HttpServletResponse response, BackUser backUser) {
        Gson gson = new Gson();
        String userJson = gson.toJson(backUser);
        String cookieValue = MD5codingLowCase.MD5(userJson);
        Cookie sessionCookie = new Cookie(Constant.SESSION_ID, cookieValue);
        response.addCookie(sessionCookie);
        jedisCluster.setex(cookieValue, Constant.SESSION_EXPIRE_SECOND, userJson);

    }

    protected void updateSessionUser(HttpServletRequest request, BackUser user) {
        Cookie cookie = this.getSessionCookie(request);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            jedisCluster.setex(sessionId, Constant.SESSION_EXPIRE_SECOND, userJson);
        }
    }

    protected void removeSessionUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = this.getSessionCookie(request);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            jedisCluster.del(sessionId);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    private Cookie getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (Constant.SESSION_ID.equalsIgnoreCase(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 得到session中的ChannelInfo对象
     */
    public ChannelInfo loginChannelInfo(HttpServletRequest request) {
        byte[] channelInfo = jedisCluster.get((Constant.CHANNEL_INFO + request.getSession().getId()).getBytes());
        if (null == channelInfo) {
            return null;
        }
        return (ChannelInfo) SerializeUtil.unserialize(channelInfo);
    }

    public User loginFrontUserByDeiceId(HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        log.info("设备号deviceId=:{}",deviceId);
        if (StringUtils.isBlank(deviceId)) {
            return null;
        }
        byte[] userInfo = jedisCluster.get((LOGING_DEVICE_PREFIX + deviceId).getBytes());

        if (null == userInfo) {
            return null;
        }

        User zbUser = (User) SerializeUtil.unserialize(userInfo);

        log.info("loginFrontUserByDeiceId key :{},userId:{}" ,(LOGING_DEVICE_PREFIX + deviceId).getBytes(),zbUser.getId());
        return zbUser;
    }

    /**
     * 验证码
     *
     */
    public boolean validateSubmit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            String value ="";
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (Constant.JCAPTCHA_CODE.equalsIgnoreCase(c.getName())) {
                        cookie =  c;
                    }
                }
            }
            if(cookie!= null){
                String key = cookie.getValue();
                log.info("send sms redis key:{}",key);
//            String key = request.getSession(false).getId();
//                String key = MD5codingLowCase.MD5(new Date().toString());
                value = jedisCluster.get(key);
                log.info("get code:{}",value);
                log.info("request captcha :{}",request.getParameter("captcha").toLowerCase());
                jedisCluster.del(key);
            }
            return request.getParameter("captcha").toLowerCase().equals(value);
        } catch (Exception e) {
            log.error("validateSubmit error:{}",e);
            return false;
        }
    }

    /**
     * 获得request中的参数
     *
     * @return string object类型的map
     */
    public HashMap<String, Object> getParametersO(HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (request == null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        Map req = request.getParameterMap();
        if ((req != null) && (!req.isEmpty())) {
            Map<String, Object> p = new HashMap<>();
            Collection keys = req.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                Object value = req.get(key);
                Object v;
                if ((value.getClass().isArray()) && (((Object[]) value).length > 0)) {
                    if (((Object[]) value).length > 1) {
                        v = value;
                    } else {
                        v = ((Object[]) value)[0];
                    }
                } else {
                    v = value;
                }
                if ((v != null) && ((v instanceof String)) && !"\"\"".equals(v)) {
                    String s = ((String) v).trim();
                    if (s.length() > 0) {
                        p.put(key, s);
                    }
                }
            }
            hashMap.putAll(p);
            // 读取cookie
            hashMap.putAll(ReadCookieMap(request));

        }
        return hashMap;
    }

    /**
     * 得到页面传递的参数封装成map
     */

    public Map<String, String> getParameters(HttpServletRequest request) {
        if (request == null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        Map<String, String> p = new HashMap<>();
        Map req = request.getParameterMap();
        if ((req != null) && (!req.isEmpty())) {

            Collection keys = req.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                Object value = req.get(key);
                Object v = null;
                if ((value.getClass().isArray()) && (((Object[]) value).length > 0)) {
                    v = ((Object[]) value)[0];
                } else {
                    v = value;
                }
                if ((v != null) && ((v instanceof String)) && !"\"\"".equals(v)) {
                    String s = (String) v;
                    if (s.length() > 0) {
                        p.put(key, s);
                    }
                }
            }
            // 读取cookie
            p.putAll(ReadCookieMap(request));
            return p;
        }
        return p;
    }

    /**
     * 将cookie封装到Map里面
     *
     */
    private static Map<String, String> ReadCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }

    /**
     * @return true:设置成功（代表是没有超发），false：设置失败（超发）
     */
    public boolean checkForFront(String key, String flag) {
        log.info("checkForFront key:{},flag:{}" ,key ,flag);
        String expireKey = key + flag;
        log.info("checkForFront expireKey:{}",expireKey);
        Long tooMuchFlag = jedisCluster.setnx(expireKey, "1");
        log.info("checkForFront key:{},result:{}",expireKey ,tooMuchFlag);
        if (tooMuchFlag <= 0) {
            return false;
        }
        jedisCluster.expire(expireKey, 60);
        return true;
    }

    public boolean delCheckForFront(String key, String flag) {
        log.info("delCheckForFront key:{},flag:{}",key ,flag);
        String expireKey = key + flag;
        log.info("delCheckForFront expireKey:{}",expireKey);
        jedisCluster.del(expireKey);
        return true;
    }

}