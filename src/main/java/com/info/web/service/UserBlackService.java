package com.info.web.service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IUserBlackDao;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.ReadExecl;
import com.info.web.pojo.UserBlack;
import org.springframework.web.multipart.MultipartFile;

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
    public boolean batchImport(String name, MultipartFile file) {
        boolean b = false;
        try{
            //创建处理EXCEL
            ReadExecl readExcel=new ReadExecl();
            //解析excel，获取客户信息集合。
            List<UserBlack> customerList = readExcel.getExcelInfo(name ,file);
            //execl中的结果去重
            List<UserBlack> collect = customerList.stream().distinct().collect(Collectors.toList());
            //查出数据库现在的黑名单用户
            List<UserBlack> list = iUserBlackDao.userBlackList();
            //execl 与本地库 取差集
            List<UserBlack> distinctByUniqueList = collect.stream() .filter(item -> !list.stream() .map(e -> e.getUserName()+ e.getUserPhone() + e.getIdNumber())
                    .collect(Collectors.toList()) .contains(item.getUserName() + item.getUserPhone() + item.getIdNumber())) .collect(Collectors.toList());
            if(distinctByUniqueList != null){
                b = true;
            }
            for(UserBlack userBlack:distinctByUniqueList){
                userBlack.setCreateTime(new Date());
                iUserBlackDao.addUserBlack(userBlack);
            }
        }catch (Exception e){
           log.error("批量导入黑名单",e.getMessage());
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