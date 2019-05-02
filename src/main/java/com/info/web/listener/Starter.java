package com.info.web.listener;

import javax.servlet.ServletContext;

/**
 * 
 * @author zed
 * 
 */
public interface Starter {
	/**
	 * servlet context init
	 * @param ctx ctx
	 */
	void init(ServletContext ctx);

}
