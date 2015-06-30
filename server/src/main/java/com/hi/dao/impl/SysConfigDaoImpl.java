package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.SysConfigDao;
import com.hi.model.SysConfig;

@Repository("sysConfigDao")
public class SysConfigDaoImpl extends AbstractDao implements SysConfigDao {

	@Override
	public SysConfig getSysConfig(String paramType, String paramCode) {
		String sql = "select s.typeCode as \"typeCode\", s.paramType as \"paramType\", s.paramCode as \"paramCode\", "
				+ " s.paramName as \"paramName\", s.paramSrc as \"paramSrc\", "
				+ " s.startValue as \"startValue\", s.endValue as \"endValue\" "
				+ " from T_CATER_SYSCONFIG s "
				+ " where s.paramType = :paramType and s.paramCode = :paramCode and s.status = '1' and s.paramSrc in('0', '2') ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paramType", paramType);
		params.put("paramCode", paramCode);
		return this.getBeanBySql(SysConfig.class, sql, params);
	}
	
	@Override
	public List<SysConfig> getSysConfigs(String paramType, String paramCode){
		String sql = "select s.typeCode as \"typeCode\", s.paramType as \"paramType\", s.paramCode as \"paramCode\", "
				+ " s.paramName as \"paramName\", s.paramSrc as \"paramSrc\", "
				+ " s.startValue as \"startValue\", s.endValue as \"endValue\" "
				+ " from T_CATER_SYSCONFIG s "
				+ " where s.paramType = :paramType and s.paramCode = :paramCode and s.status = '1' and s.paramSrc in('0', '2') "
				+ " order by s.id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paramType", paramType);
		params.put("paramCode", paramCode);
		return this.getBeansBySql(SysConfig.class, sql, params);
	}
}