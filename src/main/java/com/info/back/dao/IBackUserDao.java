package com.info.back.dao;

import com.info.web.pojo.BackLog;
import com.info.web.pojo.BackUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
@Repository
public interface IBackUserDao {
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
	 * 插入用户对象
	 * 
	 * @param backUser
	 */
	public void insert(BackUser backUser);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 更新用户对象
	 * 
	 * @param backUser
	 */
	public void updateById(BackUser backUser);

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

	List<BackUser> findReviewerByModuleUrl(String moduleUrl);

	BackUser findReviewerById(Integer id);

    void updateAllById(BackUser backUser);

    void pyhsicalDeleteById(Integer id);

	String getBackUserIdByPhone(@Param("phone")String phone);
    List<String> selectBackUserNameByIds(@Param("ids")String[] ids);
    List<BackUser> selectBackUserByIds(@Param("ids")String[] ids);

	BackUser findByUserId(Integer userId);

	String selectSuperAdminStrByRoleId(@Param("roleId") Integer roleId);
}
