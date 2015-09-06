package com.hi.dao;

import com.hi.model.RecieptDept;

public interface RecieptDeptDao {
	RecieptDept getRecieptDept(String customerId, String recieptType, String department);

	boolean createRecieptDept(String customerId, String recieptType, String department);
}
