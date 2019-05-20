package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.back.dao.IBackUserDao;
import com.info.back.dao.IBackUserRoleDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackLog;
import com.info.web.pojo.BackUser;
import com.info.web.util.PageConfig;

@Service
public class BackUserService implements IBackUserService {
	@Autowired
	private IBackUserDao backUserDao;
	@Autowired
	private IBackUserRoleDao backUserRoleDao;
	@Autowired
	private IPaginationDao paginationDao;

	@Override
	public List<BackUser> findAll(HashMap<String, Object> params) {
		return backUserDao.findAll(params);
	}

	@Override
	public BackUser findOneUser(HashMap<String, Object> params) {
		List<BackUser> list = this.findAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insert(BackUser backUser) {
		backUserDao.insert(backUser);
	}

	@Override
	public void updateById(BackUser backUser) {
		backUserDao.updateById(backUser);
	}

	@Override
	public void deleteById(Integer id) {
		backUserDao.deleteById(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<BackUser> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BackUser");
		PageConfig<BackUser> pageConfig = new PageConfig<BackUser>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,
				"back");
		return pageConfig;

	}

	@Override
	public void addUserRole(HashMap<String, Object> params) {
		backUserRoleDao.deleteUserRoleByUserId(Integer.valueOf(String
				.valueOf(params.get("id"))));
		String[] roleIds = (String[]) params.get("roleIds");
		if (null != roleIds && roleIds.length > 0) {
			backUserRoleDao.inserUserRoleList(params);
		}
	}

	@Override
	public void updatePwdById(BackUser backUser) {
		backUserDao.updatePwdById(backUser);
	}

	@Override
	public int insertLog(BackLog backLog) {
		return backUserDao.insertLog(backLog);
	}


	@Override
	public List<BackUser> findKeFuList(HashMap<String, Object> hashMap) {
		return backUserDao.findKeFuList(hashMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<BackUser> queryCustomerList(HashMap<String,Object> params){
		params.put("roleId",Constant.ROLE_CUSTOMER_SERVER);
		params.put(Constant.NAME_SPACE,"BackUserRole");
		return paginationDao.findPage("queryBackUser","queryBackUserCount",params,"back");
	}

	@Override
	public void pyhsicalDeleteById(Integer id){
		backUserDao.deleteById(id);
		backUserDao.pyhsicalDeleteById(id);
	}

	@Override
	public int inserUserRole(BackUserRole backUserRole){
		return backUserRoleDao.inserUserRole(backUserRole);
	}

	@Override
	public void updateAllById(BackUser backUser){
		backUserDao.updateAllById(backUser);
	}

	@Override
	public String getBackUserIdByPhone(String phone) {
		return backUserDao.getBackUserIdByPhone(phone);
	}

	@Override
	public List<String> selectBackUserNameByIds(String[] ids) {
		return backUserDao.selectBackUserNameByIds(ids);
	}

	@Override
	public List<BackUser> selectBackUserByIds(String[] ids) {
		return backUserDao.selectBackUserByIds(ids);
	}

	@Override
	public BackUser selectUserById(Integer userId){
		return backUserDao.findByUserId(userId);
	}

	@Override
	public boolean loginUserIsSuperAdmin(String userId) {
		String s = backUserDao.selectSuperAdminStrByRoleId(10001);
		String admins[]=s.split(",",-1);
		for(String admin:admins){
			if(userId.equals(admin)){
				return true;
			}
		}
		return false;
	}
}
