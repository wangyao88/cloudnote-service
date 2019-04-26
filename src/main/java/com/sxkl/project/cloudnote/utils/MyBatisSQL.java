package com.sxkl.project.cloudnote.utils;

import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import org.apache.ibatis.jdbc.AbstractSQL;

/**
 * @author wangyao
 */
public class MyBatisSQL extends AbstractSQL<MyBatisSQL> {

	public MyBatisSQL whereIfNotNull(Object param, String conditions) {
		if(ObjectUtils.isNotNull(param)){
			super.WHERE(conditions);
		}
		return this;
	}

	public MyBatisSQL setIfNotNull(Object param, String conditions) {
		if(ObjectUtils.isNotNull(param)){
			super.SET(conditions);
		}
		return this;
	}

	@Override
	public MyBatisSQL getSelf() {
		return this;
	}

	public static AbstractSQL<MyBatisSQL> builder() {
		return new MyBatisSQL();
	}

	public String build() {
		return this.toString();
	}
}
