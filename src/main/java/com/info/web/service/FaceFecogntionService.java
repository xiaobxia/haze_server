package com.info.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IFaceRecognitionDao;
import com.info.web.pojo.FaceRecognition;
@Service
public class FaceFecogntionService implements IFaceFecogntionService{
	@Autowired
	public IFaceRecognitionDao faceRecognitionDao;
	@Override
	public boolean saveFaceRecognitionDao(FaceRecognition face) {
		return faceRecognitionDao.saveFaceRecognitionDao(face);
	}

	@Override
	public FaceRecognition selectByUserId(Integer userId) {
		return faceRecognitionDao.selectByUserId(userId);
	}

	@Override
	public boolean updateFaceRecognition(FaceRecognition face) {
		return faceRecognitionDao.updateFaceRecognition(face);
	}
}
