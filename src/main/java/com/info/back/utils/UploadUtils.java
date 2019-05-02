package com.info.back.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.context.ContextLoader;

/**
 * 上传文件工具类
 * 
 * @author God
 * 
 */
public class UploadUtils {

	/**
	 * 附件路径
	 */
	private static final String FILE_PATH = "files";

	/**
	 * 获得上传目录的相对地址
	 * 
	 */
	public static String getRelatedPath() {
		return "/" + FILE_PATH + "/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获得上传目录的完整路径
	 */
	public static String getRealPath() {

		return getRealPath(getRelatedPath());
	}

	/**
	 * 获得上传目录的完整路径
	 * 
	 */
	public static String getRealPath(String path) {
		String fileRealPathDir = ContextLoader
				.getCurrentWebApplicationContext().getServletContext()
				.getRealPath(path);
		File pathFile = new File(fileRealPathDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		return fileRealPathDir;
	}

}
