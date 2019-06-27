package com.info.web.listener;

import com.info.constant.Constant;
import com.info.risk.utils.ConstantRisk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.util.*;

/**
 * 首页init
 * 
 * @author gaoyuhai 2016-12-10 下午05:23:32
 */
@Slf4j
public class IndexInit implements ServletContextListener {

	private List<Object> starters = new ArrayList<>();

	public IndexInit() {
		addStarter(new SystemConfigStarter());

	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try{
			while(DriverManager.getDrivers().hasMoreElements()){
				DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public TreeSet<String> getKeys(String pattern, JedisCluster jedisCluster) {
		TreeSet<String> keys = new TreeSet<>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				log.error("getKeys error:{}",e);
			} finally {
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		return keys;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		JedisCluster jedisCluster = (JedisCluster) ctx.getBean("jedisCluster");
		// initIndex(indexService, jedisCluster);
		jedisCluster.del(Constant.ALLOW_IP);
		Iterator<Object> it = starters.iterator();
		while (it.hasNext()) {
			Starter s = (Starter) it.next();
			s.init(arg0.getServletContext());
		}
		jedisCluster.del(ConstantRisk.RISK_ANALYSIS);
		jedisCluster.del(ConstantRisk.JXL_WAIT_ANALYSIS);
		jedisCluster.del(ConstantRisk.CAL_MONEY);
		jedisCluster.del("channel_report");
		TreeSet<String> keys = getKeys(ConstantRisk.REVIEW_BORROW + "*", jedisCluster);
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				jedisCluster.del(key);
			}
		}
		keys = getKeys(ConstantRisk.JXL_REPORT + "*", jedisCluster);
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				jedisCluster.del(key);
			}
		}
		keys = getKeys(ConstantRisk.USER_MONEY + "*", jedisCluster);
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				jedisCluster.del(key);
			}
		}
		//开启放款任务
//		I  = (ITaskJob) ctx.getBean("taskJob");
//		loanMoneyAbout(taskJob);

	}

	void addStarter(Starter startup) {
		starters.add(startup);
	}

	/**
	 * 更新所有缓存
	 */
	public void updateCache() {
		ServletContext ctx = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		Iterator<Object> it = starters.iterator();
		while (it.hasNext()) {
			Starter s = (Starter) it.next();
			s.init(ctx);
		}
	}
//
//	public void updateRiskCache() {
//		ServletContext ctx = ContextLoader.getCurrentWebApplicationContext().getServletContext();
//		Iterator<Object> it = starters.iterator();
//		while (it.hasNext()) {
//			Starter s = (Starter) it.next();
//			s.risk(ctx);
//		}
//	}
}
