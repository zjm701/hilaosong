package com.hi.dao;

import java.util.List;

import com.hi.model.SysConfig;

public interface SysConfigDao {

	SysConfig getSysConfig(String paramType, String paramCode);

	SysConfig getSysConfig(String paramType, String paramCode, String paramSrc);
	
	List<SysConfig> getSysConfigs(String paramType, String paramCode);
}
