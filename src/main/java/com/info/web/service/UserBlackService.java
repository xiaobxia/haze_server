package com.info.web.service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserBlackDao;
import com.info.web.pojo.User;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.ReadExecl;
import com.info.web.pojo.UserBlack;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserBlackService implements  IUserBlackService{

    @Autowired
    private IUserBlackDao iUserBlackDao;

    @Autowired
    private IPaginationDao paginationDao;



    @Override
    public boolean batchImport(String name, MultipartFile file,String userType) {
        boolean b = false;
        try{
            //创建处理EXCEL
            ReadExecl readExcel=new ReadExecl();
            //解析excel，获取客户信息集合。
            List<UserBlack> customerList = readExcel.getExcelInfo(name ,file);
            //execl中的结果去重
            List<UserBlack> collect = customerList.stream().distinct().collect(Collectors.toList());
            //查出数据库现在的黑/白名单用户
            List<UserBlack>  list = iUserBlackDao.userBlackList(Integer.valueOf(userType));
            /*String contrayType = "";
            if(userType.equals("0")){
                contrayType = "1";
            }else{
                contrayType = "0";
            }*/
            //查出数据集合
            List<UserBlack> contraryList = iUserBlackDao.userBlackList(null);
            List<UserBlack> updateList = new ArrayList<>();
            //与库里取交集
            if (contraryList.size() >= collect.size()){
                for(UserBlack  userBlack : contraryList){
                    for(UserBlack blackUser : collect){
                        if(userBlack.getUserPhone().equals(blackUser.getUserPhone())){
                            blackUser.setId(userBlack.getId());
                            blackUser.setUserType(Integer.valueOf(userType));
                            updateList.add(blackUser);
                        }
                    }
                }
            }else{
                for(UserBlack blackUser : collect){
                    for(UserBlack userBlack : contraryList){
                        if(userBlack.getUserPhone().equals(blackUser.getUserPhone())){
                            blackUser.setId(userBlack.getId());
                            blackUser.setUserType(Integer.valueOf(userType));
                            updateList.add(blackUser);
                        }
                    }
                }
            }
            //若存在要导入数据 则修改这部分数据
            for(UserBlack userBlack:updateList){
                iUserBlackDao.updateUserBlack(userBlack);
               /* iUserBlackDao.updateUserType(Integer.valueOf(userType),userBlack.getId());*/
            }
            //execl 与本地黑/白名单库 取差集
            List<UserBlack> resultList = collect.stream() .filter(item -> !updateList.stream() .map(e -> e.getUserPhone())
                    .collect(Collectors.toList()) .contains(item.getUserPhone())) .collect(Collectors.toList());
            if(list.size()>0){
                //execl 与本地黑/白名单库 取差集
                List<UserBlack> distinctByUniqueList = resultList.stream() .filter(item -> !list.stream() .map(e -> e.getUserPhone())
                        .collect(Collectors.toList()) .contains(item.getUserPhone())) .collect(Collectors.toList());
                if(distinctByUniqueList != null){
                    b = true;
                }
                for(UserBlack userBlack:distinctByUniqueList){
                    userBlack.setUserType(Integer.valueOf(userType));
                    userBlack.setCreateTime(new Date());
                    iUserBlackDao.addUserBlack(userBlack);
                }
            }else{
                if(resultList != null){
                    b = true;
                }
                for(UserBlack userBlack:resultList){
                    userBlack.setUserType(Integer.valueOf(userType));
                    userBlack.setCreateTime(new Date());
                    iUserBlackDao.addUserBlack(userBlack);
                }
            }
        }catch (Exception e){
           log.error("批量导入黑名单失败",e.getMessage());
           b = false;
        }

        return b;
    }

    @Override
    public void updateUserBlackStatus(Integer id) {
        iUserBlackDao.updateUserBlackStatus(id);
    }

    @Override
    public void addUserBlack(UserBlack userBlack) {
        iUserBlackDao.addUserBlack(userBlack);
    }

    /**
     * 后台查询 分页
     */
    @SuppressWarnings("unchecked")
    public PageConfig<UserBlack> getUserPage(HashMap<String, Object> params){
        params.put(Constant.NAME_SPACE, "UserBlack");
        return paginationDao.findPage("getBackUserList", "getBackUserCount", params,"web");
    }

    @Override
    public UserBlack findBlackUserByParams(Integer id, Integer userType,String userPhone) {
        UserBlack userBlack = iUserBlackDao.findBlackUserByParams(id,userType,userPhone);
        return userBlack;
    }


}