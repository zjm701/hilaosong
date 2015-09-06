package com.hi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.RecieptDeptDao;
import com.hi.model.RecieptDept;
import com.hi.service.RecieptDeptService;

@Service("recieptDeptService")
@Transactional
public class RecieptDeptServiceImpl implements RecieptDeptService {

	@Autowired
	private RecieptDeptDao dao;

	@Override
	public boolean createRecieptDept(String customerId, String recieptType, String department) {
		RecieptDept rd = dao.getRecieptDept(customerId, recieptType, department);
		if (rd != null) {
			return true;
		} else {
			return dao.createRecieptDept(customerId, recieptType, department);
		}
	}
}
