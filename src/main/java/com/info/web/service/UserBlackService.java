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
            String contrayType = "";
            if(userType.equals("0")){
                contrayType = "1";
            }else{
                contrayType = "0";
            }
            //查出相反的库数据集合
            List<UserBlack>  contraryList = iUserBlackDao.userBlackList(Integer.valueOf(contrayType));
            List<UserBlack> updateList = new ArrayList<>();
            List<UserBlack> addList = new ArrayList<>();
            //与相反的库里取交集
            if (contraryList.size() >= collect.size()){
                for(UserBlack  userBlack : contraryList){
                    for(UserBlack blackUser : collect){
                        if(userBlack.getUserPhone().equals(blackUser.getUserPhone())
                                && userBlack.getUserName().equals(blackUser.getUserName())
                                && userBlack.getIdNumber().equals(blackUser.getIdNumber())){
                            updateList.add(userBlack);
                        }
                    }
                }
            }else{
                for(UserBlack blackUser : collect){
                    for(UserBlack userBlack : contraryList){
                        if(userBlack.getUserPhone().equals(blackUser.getUserPhone())
                                && userBlack.getUserName().equals(blackUser.getUserName())
                                && userBlack.getIdNumber().equals(blackUser.getIdNumber())){
                            updateList.add(userBlack);
                        }
                    }
                }
            }
            //若白/黑名单的存在要导入的黑/白名单数据 则修改这部分数据
            for(UserBlack userBlack:updateList){
                iUserBlackDao.updateUserType(Integer.valueOf(userType),userBlack.getId());
            }
            //execl 与本地黑/白名单库 取差集
            List<UserBlack> resultList = collect.stream() .filter(item -> !updateList.stream() .map(e -> e.getUserName()+ e.getUserPhone() + e.getIdNumber())
                    .collect(Collectors.toList()) .contains(item.getUserName() + item.getUserPhone() + item.getIdNumber())) .collect(Collectors.toList());
            if(list.size()>0){
                //execl 与本地黑/白名单库 取差集
                List<UserBlack> distinctByUniqueList = resultList.stream() .filter(item -> !list.stream() .map(e -> e.getUserName()+ e.getUserPhone() + e.getIdNumber())
                        .collect(Collectors.toList()) .contains(item.getUserName() + item.getUserPhone() + item.getIdNumber())) .collect(Collectors.toList());
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


}