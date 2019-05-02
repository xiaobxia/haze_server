package com.info.web.dao.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.info.web.dao.BaseDao;
import com.info.web.dao.IFaceRecognitionDao;
import com.info.web.pojo.FaceRecognition;
@Repository
public class FaceRecognitionDao extends BaseDao implements IFaceRecognitionDao{

	@Override
	public boolean saveFaceRecognitionDao(FaceRecognition face) {
		return getSqlSessionTemplate().insert("com.info.web.dao.IFaceRecognitionDao.saveFaceRecognition", face)<=0?false:true;
	}

	@Override
	public FaceRecognition selectByUserId(Integer userId) {
		HashMap<String, Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		return getSqlSessionTemplate().selectOne("com.info.web.dao.IFaceRecognitionDao.selectByFaceRecognitionUserId", params);
	}

	@Override
	public boolean updateFaceRecognition(FaceRecognition face) {
		return getSqlSessionTemplate().update("com.info.web.dao.IFaceRecognitionDao.updateByUserId", face)<=0?false:true;
	}

}
