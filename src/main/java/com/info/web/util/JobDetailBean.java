package com.info.web.util;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
@Slf4j
@PersistJobDataAfterExecution  
@DisallowConcurrentExecution 
public class JobDetailBean extends QuartzJobBean {
	private String targetObject;
	private String targetMethod;
	private ApplicationContext ctx;
	@Override
	protected void executeInternal(JobExecutionContext context) {
		Object bean;
		Method m;
		try {
			log.info("bean :{}",targetObject);
			log.info("method :{}",targetMethod);
			bean = ctx.getBean(targetObject);
			m = bean.getClass().getMethod(targetMethod);
			m.invoke(bean);
		} catch (Exception e) {
			log.error("executeInternal error:{}", e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
}
