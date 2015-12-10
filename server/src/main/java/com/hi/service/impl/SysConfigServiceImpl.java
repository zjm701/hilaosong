package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.SysConfigDao;
import com.hi.model.SysConfig;
import com.hi.service.SysConfigService;

@Service("sysConfigService")
@Transactional
public class SysConfigServiceImpl implements SysConfigService {


	@Autowired
	private SysConfigDao cdao;
	public List<SysConfig> getSysConfigs(String paramType, String paramCode, String paramSrc){
		List<SysConfig> scs = cdao.getSysConfigs(paramType, paramCode, paramSrc);
		for (int i = 0; i < scs.size(); i++) {
			scs.get(i).setStartValueS(null);
		}
		return scs;
	}
}
