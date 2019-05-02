package com.info.back.interceptor;

import com.google.gson.Gson;
import com.info.back.service.IBackModuleService;
import com.info.back.service.IBackUserService;
import com.info.back.utils.RequestUtils;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BackUser;
import com.info.web.service.IBackConfigParamsService;
import com.info.web.util.ConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：后台拦截器，包括session验证和权限信息 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-27 下午05:38:34 <br>
 */
@Slf4j
public class AdminContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    IBackUserService backUserService;
    @Autowired
    IBackModuleService backModuleService;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    IBackConfigParamsService backConfigParamsService;

    @SuppressWarnings("unused")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String uri = getURI(request);
            // 不在验证的范围内
            if (exclude2(uri)) {
                return true;
            }
            BackUser user;
            // 正常状态
            user = this.getSessionUser(request,response);
            boolean allow = true;
            String value = jedisCluster.get(Constant.ALLOW_IP);
            if (value == null) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("sysKey", "SYSTEM_IP");
                List<BackConfigParams> list = backConfigParamsService.findParams(params);
                if (list != null && list.size() > 0) {
                    value = list.get(0).getSysValueAuto();
                    if (StringUtils.isNotBlank(value)) {
                        jedisCluster.setex(Constant.ALLOW_IP, 60 * 10, value);
                    }
                }
            }
            if (StringUtils.isNotBlank(value)) {
                String[] ips = value.split(",");
                List<String> list = Arrays.asList(ips);
                String ip = RequestUtils.getIpAddr(request);
                if (!list.contains(ip)) {
                    allow = false;
                }
            }
            if (!"/err".equals(uri)) {
                if (!allow) {
                    if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
                    {
                        response.setHeader("statusCode", "303");
                    } else {
                        response.sendRedirect(ConfigConstant.getConstant(Constant.BACK_URL) + "err");
                    }
//                    request.getSession().removeAttribute(Constant.BACK_USER);
                    this.removeSessionUser(request);

                    return false;

                }
            } else {
                if (!allow) {
                    return true;
                }
            }
            // 不在验证的范围内
            if (exclude(uri)) {
                return true;
            }

            // 用户为null跳转到登录页面
            if (user == null) {
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
                {
                    response.setHeader("statusCode", "301");// 在响应头设置session状态
                    return false;
                }
                response.sendRedirect(request.getContextPath()+loginUrl);
                return false;
            }
            // 用户的登陆密码与session不一致强制退出登陆
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", user.getId());
            BackUser currentUser = backUserService.findOneUser(params);
            if (!user.getUserPassword().equals(currentUser.getUserPassword())) {
//                request.getSession().removeAttribute(Constant.BACK_USER);
                this.removeSessionUser(request);
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
                {
                    response.setHeader("statusCode", "301");// 在响应头设置session状态
                    return false;
                }
                response.sendRedirect(request.getContextPath()+loginUrl);
                return false;
            }
            if (currentUser.getStatus().intValue() != BackUser.STATUS_USE.intValue()) {
                request.setAttribute("message", "用户已被删除");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            if (!SpringUtils.loginUserIsSuperAdmin(String.valueOf(user.getId()))) {
                String u;
                int i;
                i = uri.indexOf("/");
                if (i == -1) {
                    throw new RuntimeException("uri must start width '/':" + uri);
                }
                u = uri.substring(i + 1);
                params.put("moduleUrl", u);
                int count = backModuleService.findModuleByUrl(params);
                if (count > 0) {
                    return true;
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }

            } else {
                return true;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("admin interrupt error:{}", e);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }

    private String getLoginUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (loginUrl.startsWith("/")) {
            String ctx = request.getContextPath();
            if (!StringUtils.isBlank(ctx)) {
                buff.append(ctx);
            }
        }
        buff.append(loginUrl).append("?");
        buff.append("returnUrl").append("=").append(returnUrl);
        if (!StringUtils.isBlank(processUrl)) {
            buff.append("&").append("processUrl").append("=").append(getProcessUrl(request));
        }
        return buff.toString();
    }

    private String getProcessUrl(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder();
        if (loginUrl.startsWith("/")) {
            String ctx = request.getContextPath();
            if (!StringUtils.isBlank(ctx)) {
                buff.append(ctx);
            }
        }
        buff.append(processUrl);
        return buff.toString();
    }

    private boolean exclude2(String uri) {
        if (noIp != null) {
            for (String exc : noIp) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断该请求是否是允许不经过session验证的路径
     */
    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得第二个路径分隔符的位置
     *
     * @throws IllegalStateException 访问路径错误，没有二(三)个'/'
     */
    private static String getURI(HttpServletRequest request) throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 1;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }
        if (start <= 0) {
            throw new IllegalStateException("admin access path not like '/back/...' pattern: " + uri);
        }
        return uri.substring(start);
    }

    private String[] excludeUrls;
    /**
     * 不用经过IP验证的请求
     */
    private List<String> noIp;

    public void setNoIp(List<String> noIp) {
        this.noIp = noIp;
    }

    private String loginUrl;
    private String processUrl;
    private String returnUrl;
    private String outCall;

    public String getOutCall() {
        return outCall;
    }

    public void setOutCall(String outCall) {
        this.outCall = outCall;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getProcessUrl() {
        return processUrl;
    }

    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    private BackUser getSessionUser(HttpServletRequest request,HttpServletResponse response) {
        Cookie sessionCookie = this.getSessionCookie(request);
        if (sessionCookie != null) {
            String sessionId = sessionCookie.getValue();
            String backUserJson = jedisCluster.get(sessionId);
            if (StringUtils.isEmpty(backUserJson)) {
                sessionCookie.setMaxAge(0);
                response.addCookie(sessionCookie);
                return null;
            }
            try {
                Gson gson = new Gson();
                BackUser backUser = gson.fromJson(backUserJson, BackUser.class);
                jedisCluster.expire(sessionId, Constant.SESSION_EXPIRE_SECOND);
                return backUser;
            } catch (Exception e) {
                log.error("parser user error:{}", e);
                return null;
            }
        }
        return null;
    }

    private void removeSessionUser(HttpServletRequest request) {
        Cookie sessionCookie = this.getSessionCookie(request);
        if (sessionCookie != null) {
            String sessionId = sessionCookie.getValue();
            jedisCluster.del(sessionId);
        }
    }

    private Cookie getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie c : cookies) {
            String cName = c.getName();
            if (Constant.SESSION_ID.equalsIgnoreCase(cName)) {
                return c;
            }
        }
        return null;
    }
}