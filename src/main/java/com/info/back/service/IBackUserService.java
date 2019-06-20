package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BackLog;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BackUserRole;
import com.info.web.util.PageConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface IBackUserService {
	/**
	 * 插入日志
	 * 
	 * @param backLog
	 * @return
	 */
	public int insertLog(BackLog backLog);
	/**
	 * 根据条件查询用户
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态
	 * @return 角色list
	 */
	public List<BackUser> findAll(HashMap<String, Object> params);

	/**
	 * 根据条件查询用户<br>
	 * 只返回第一个用户对象<br>
	 * 
	 * @param map
	 *            参数名 ：userAccount，含义：用户名 <br>
	 *            参数名 ：status 含义：状态 参数名：id 含义：用户主键
	 * @return 用户对象
	 */
	public BackUser findOneUser(HashMap<String, Object> params);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 插入用户对象
	 * 
	 * @param backUser
	 */
	public void insert(BackUser backUser);

	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateById(BackUser backUser);

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	PageConfig<BackUser> findPage(HashMap<String, Object> params);

	/**
	 * 用户授权
	 * 
	 * @param params
	 */
	void addUserRole(HashMap<String, Object> params);

	/**
	 * 更新密码
	 * 
	 * @param backUser
	 */
	public void updatePwdById(BackUser backUser);

	/**
	 * 根据指定的roleName查找人员
	 * @param hashMap
	 * @return
	 */
	public List<BackUser> findKeFuList(HashMap<String, Object> hashMap);

    /**
     * 根据条件查询用户表
     * */
    PageConfig<BackUser> queryCustomerList(HashMap<String, Object> hashMap);

    void pyhsicalDeleteById(Integer id);

    int inserUserRole(BackUserRole backUserRole);

    void updateAllById(BackUser backUser);

    String getBackUserIdByPhone(String phone);

	String getBackUserIdByUserName(String jobName);

    List<String> selectBackUserNameByIds(String[] ids);

    List<BackUser> selectBackUserByIds(String[] ids);

	BackUser selectUserById(Integer userId);

	boolean loginUserIsSuperAdmin(String userId);

	int findRoleByUserId(Integer id );

	int findRoleKfM(Integer userId);


}
