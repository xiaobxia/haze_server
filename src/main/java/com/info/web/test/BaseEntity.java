package com.info.web.test;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * entity 父类
 * @author liutq
 *
 */
@SuppressWarnings("serial")
public class BaseEntity implements Serializable{

	public String toString(){
		return ToStringBuilder.reflectionToString(this,
						ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
