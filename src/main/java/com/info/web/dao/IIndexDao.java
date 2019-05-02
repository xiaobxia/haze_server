package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.InfoIndex;
import com.info.web.pojo.InfoIndexInfo;
import com.info.web.pojo.InfoNotice;
import com.info.web.pojo.User;
import com.info.web.pojo.UserCardInfo;
import com.info.web.pojo.UserInfoOld;
import com.info.web.pojo.index.IndexDto;

public interface IIndexDao {
	
	/**
	 * 查询首页内容
	 * @param map
	 * @return
	 */
	public InfoIndex searchInfoIndex(HashMap<String, Object> map);
	/**
	 * 查询公告
	 * @param map
	 * @return
	 */
	public List<InfoNotice> searchInfoNoticeByIndex(HashMap<String, Object> map);
	/**
	 * 保存首页信息
	 * @param indexDto
	 */
	public void saveIndexDto(IndexDto indexDto);
	/**
	 * 查询首页信息
	 * @param map
	 * @return
	 */
	public IndexDto searchIndexDto(HashMap<String, Object> map);
	/**
	 * 动态查询用户信息
	 * @param map
	 * @return
	 */
	public InfoIndexInfo searchInfoIndexInfo(HashMap<String, Object> map);
	/**
	 * 查询借款信息
	 * @param map
	 * @return
	 */
	public BorrowOrder searchBorrowOrderByIndex(HashMap<String, Object> map);
	
	/**
	 * 根据用户ID更新IndexInfo信息
	 * @param indexInfo
	 * @return
	 */
	public int updateIndexInfoByUserId(InfoIndexInfo indexInfo);
	/**
	 * 保存用户动态数据
	 * @param indexInfo
	 */
	public void saveInfoIndexInfo(InfoIndexInfo indexInfo);
	/**
	 * 查询银行卡
	 * @param map
	 * @return
	 */
	public UserCardInfo searchUserCardInfo(HashMap<String, Object> map);
	/**
	 * 处理button按钮事件
	 * @param map
	 */
	public void updateInfoUserInfoBorrowStatus(HashMap<String, Object> map);
	/**
	 * 通过id查询用户
	 * @param map
	 * @return
	 */
	public User searchUserByIndex(HashMap<String, Object> map);
	/**
	 * 查询老用户
	 * @param map
	 * @return
	 */
	public UserInfoOld searchUserInfoOld(HashMap<String, Object> map);
	/**
	 * 更新老用户
	 * @param map
	 */
	public void updateUserInfoOld(HashMap<String, Object> map);

	/**
	 * 保存infoNotice信息
	 * @param map
	 */
    public void saveInfoNotice(HashMap<String, Object> map);
}
