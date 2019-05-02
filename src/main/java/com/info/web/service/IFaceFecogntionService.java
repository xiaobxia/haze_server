package com.info.web.service;

import com.info.web.pojo.FaceRecognition;

public interface IFaceFecogntionService {
	/**
	 * 添加人脸识别参数
	 * @param face
	 * @return
	 */
	public boolean saveFaceRecognitionDao(FaceRecognition face);
	/**
	 * 根据用户编号查询人脸参数
	 */
	public FaceRecognition selectByUserId(Integer userId);
	/**
	 * 修改人脸参数
	 * @param face
	 * @return
	 */
	public boolean updateFaceRecognition(FaceRecognition face);
}
