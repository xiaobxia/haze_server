package com.info.web.controller;

import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单派送模块权限控制〉
 *
 * @author Liubing
 * @create 2018/3/15
 * @since 1.0.0
 */
public class AssignController extends BaseController {

    @Resource
    private IUserService userService;

    @ModelAttribute
    public Map<String,Object> authorityControl(HttpServletRequest request, Map<String,Object> map){
        HashMap<String,Object> params = getParametersO(request);
        BackUser backUser = loginAdminUser(request);
        //map.put("params",params);
        List<Integer> roleIds = userService.getRoleByUserId(backUser.getId());
        if(CollectionUtils.isNotEmpty(roleIds)){
            for(Integer roleId:roleIds){
                if(Constant.ROLE_CUSTOMER_SERVER.equals(roleId)){
                    params.put("jobId",backUser.getId());
                    map.put("params",params);
                    return map;
                }
            }
        }
        map.put("params",params);
        return map;
    }
}
