package com.info.back.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IBackModuleDao;
import com.info.back.utils.DwzResult;
import com.info.back.utils.PropertiesUtil;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackModule;
import com.info.web.pojo.BackTree;
import com.info.web.util.PageConfig;
@Slf4j
@Service
public class BackModuleService implements IBackModuleService {
	@Autowired
	private IBackModuleDao backModuleDao;

	@Autowired
	private IPaginationDao paginationDao;

	@Autowired
	private IBackUserService backUserService;

	@Override
	public List<BackModule> findAllModule(HashMap<String, Object> params) {
		List<BackModule> list = new ArrayList<>();
		//userId条件 && 是否超级管理员
		if (params.containsKey("userId") 
//				&& Constant.ADMINISTRATOR_ID.intValue() == Integer.valueOf(String.valueOf(params.get("userId"))).intValue()
				&&	backUserService.loginUserIsSuperAdmin(String.valueOf(params.get("userId")))
				) {
			//获取超级管理员的所有菜单
			list = backModuleDao.findAdminAll(params);
		} else {
			if (params.get("parentId") != null) {
				//获取普通户的全部菜单
				list = backModuleDao.findUserAll(params);
			}
		}
		return list;
	}

	@Override
	public List<BackTree> findModuleTree(HashMap<String, Object> params) {
		if (params.containsKey("userId") &&

				backUserService.loginUserIsSuperAdmin(String.valueOf(params.get("userId")))) {
			return backModuleDao.findAdminTree(params);
		} else {
			return backModuleDao.findUserTree(params);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<BackModule> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BackModule");
		PageConfig<BackModule> pageConfig = new PageConfig<BackModule>();
		if (params.containsKey("userId") && backUserService.loginUserIsSuperAdmin(String.valueOf(params.get("userId")))) {
			pageConfig = paginationDao.findPage("findAdminAll", "findAdminCount", params, "back");
		} else {
			pageConfig = paginationDao.findPage("findUserAll", "findUserCount", params, "back");
		}
		return pageConfig;

	}

	@Override
	public BackModule findById(Integer id) {
		return backModuleDao.findById(id);
	}

	@Override
	public void updateById(BackModule backModule) {
		backModuleDao.updateById(backModule);
	}

	@Override
	public void deleteById(Integer id) {
		backModuleDao.deleteById(id);
	}

	@Override
	public void insert(BackModule backModule) {
		backModuleDao.insert(backModule);
	}

	@Override
	public int findModuleByUrl(HashMap<String, Object> params) {
		return backModuleDao.findModuleByUrl(params);
	}

	@Override
	public DwzResult updateCacheOthers(String currentIpPort) {

		DwzResult dwzResult = new DwzResult();
		boolean succ = false;

		String msg = "刷新缓存成功";
		Set<String> failUrl = new HashSet<String>();
		try {

			String urlString = PropertiesUtil.get("BACK_URLS");
			updateOther(failUrl, urlString, currentIpPort);
			if ((failUrl == null || failUrl.size() == 0)) {
				succ = true;
			}

			succ = true;
		} catch (Exception e) {
			log.error("updateCacheOthers error:{}",e);
			succ = false;
			msg = "刷新缓存失败";
		} finally {

		}

		if (failUrl != null && failUrl.size() > 0) {
			succ = false;
			msg = "刷新失败，失败的服务器列表：" + failUrl.toString();
		}

		dwzResult.setStatusCode(succ ? "200" : "500");
		dwzResult.setMessage(msg);
		return dwzResult;

	}

	public void updateOther(Set<String> failUrl, String urlString, String currentIp) {

		if (StringUtils.isNotBlank(urlString)) {
			String method = PropertiesUtil.get("UPDATE_METHOD");
			String[] urls = urlString.split(",");
			for (String string : urls) {
				try {
					if (string.indexOf(currentIp) > 0) {// 不刷新本机
						continue;
					}
					URL url = new URL(string + method);
					// 将url 以 open方法返回的urlConnection
					// 连接强转为HttpURLConnection连接 (标识一个url所引用的远程对象连接)
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中

					// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
					connection.setDoOutput(true);

					// 设置连接输入流为true
					connection.setDoInput(true);

					// 设置请求方式为post
					connection.setRequestMethod("POST");

					// post请求缓存设为false
					connection.setUseCaches(false);

					// 设置该HttpURLConnection实例是否自动执行重定向
					connection.setInstanceFollowRedirects(true);
					connection.setConnectTimeout(3000);
					connection.setReadTimeout(3000);
					// 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
					// application/x-javascript text/xml->xml数据
					// application/x-javascript->json对象
					// application/x-www-form-urlencoded->表单数据
					connection.setRequestProperty("Content-Type", "application/x-javascript");

					// 建立连接
					// (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
					connection.connect();

					// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
					DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
					// String parm = "storeId="
					// + URLEncoder.encode("32", "utf-8"); //
					// URLEncoder.encode()方法
					// 为字符串进行编码

					// 将参数输出到连接
					// dataout.writeBytes(parm);

					// 输出完成后刷新并关闭流
					dataout.flush();
					dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
					int reponseCode = connection.getResponseCode();
					if (200 != reponseCode) {
						failUrl.add(string);
					}

					// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
					BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String result = bf.readLine();
					if (result != null && !"".equals(result)) {
						JSONObject json = JSONObject.fromObject(result);
						DwzResult dwzResult = (DwzResult) JSONObject.toBean(json, DwzResult.class);
						if (!(dwzResult != null && "200".equals(dwzResult.getStatusCode()))) {
							failUrl.add(string);
						}
					} else {
						failUrl.add(string);
					}
					bf.close(); // 重要且易忽略步骤 (关闭流,切记!)
					connection.disconnect(); // 销毁连接

				} catch (Exception e) {
					log.error("updateOther error:{}",e);
					failUrl.add(string);
				}
			}
		}
	}

 

}
