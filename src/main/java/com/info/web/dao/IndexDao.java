package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.InfoIndex;
import com.info.web.pojo.InfoIndexInfo;
import com.info.web.pojo.InfoNotice;
import com.info.web.pojo.User;
import com.info.web.pojo.UserCardInfo;
import com.info.web.pojo.UserInfoOld;
import com.info.web.pojo.index.IndexDto;

@Repository
public class IndexDao extends BaseDao implements IIndexDao {

	/**
	 * 查询首页内容
	 * @param map
	 * @return
	 */
	public InfoIndex searchInfoIndex(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchInfoIndex", map);
	}
	/**
	 * 查询公告
	 * @param map
	 * @return
	 */
	public List<InfoNotice> searchInfoNoticeByIndex(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectList("searchInfoNoticeByIndex", map);
	}
	/**
	 * 保存首页信息
	 * @param indexDto
	 */
	public void saveIndexDto(IndexDto indexDto){
		getSqlSessionTemplate().delete("delIndexDto");
		getSqlSessionTemplate().insert("saveIndexDto", indexDto);
	}
	/**
	 * 查询首页信息
	 * @param map
	 * @return
	 */
	public IndexDto searchIndexDto(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchIndexDto", map);
	}
	/**
	 * 动态查询用户信息
	 * @param map
	 * @return
	 */
	public InfoIndexInfo searchInfoIndexInfo(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchInfoIndexInfo", map);
	}
	/**
	 * 查询借款信息
	 * @param map
	 * @return
	 */
	public BorrowOrder searchBorrowOrderByIndex(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchBorrowOrderByIndex", map);
	}
	
	
	/* (non-Javadoc)
	 * @see com.info.web.dao.IIndexDao#updateIndexInfoByUserId(com.info.web.pojo.InfoIndexInfo)
	 */
	public int updateIndexInfoByUserId(InfoIndexInfo indexInfo) {
		return getSqlSessionTemplate().update("updateIndexInfoByUsrId",indexInfo);
	}
	/**
	 * 保存用户动态数据
	 * @param indexInfo
	 */
	public void saveInfoIndexInfo(InfoIndexInfo indexInfo){
		getSqlSessionTemplate().insert("saveInfoIndexInfo", indexInfo);
	}
	/**
	 * 查询银行卡
	 * @param map
	 * @return
	 */
	public UserCardInfo searchUserCardInfo(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchUserCardInfo", map);
	}
	/**
	 * 处理button按钮事件
	 * @param map
	 */
	public void updateInfoUserInfoBorrowStatus(HashMap<String,Object> map){
		getSqlSessionTemplate().update("updateInfoUserInfoBorrowStatus", map);
	}
	/**
	 * 通过id查询用户
	 * @param map
	 * @return
	 */
	public User searchUserByIndex(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchUserByIndex", map);
	}
	/**
	 * 查询老用户
	 * @param map
	 * @return
	 */
	public UserInfoOld searchUserInfoOld(HashMap<String,Object> map){
		return getSqlSessionTemplate().selectOne("searchUserInfoOld", map);
	}
	/**
	 * 更新老用户
	 * @param map
	 */
	public void updateUserInfoOld(HashMap<String,Object> map){
		getSqlSessionTemplate().update("updateUserInfoOld", map);
	}

	public void saveInfoNotice(HashMap<String,Object> map) {
		getSqlSessionTemplate().update("saveInfoNotice", map);
	}
}
