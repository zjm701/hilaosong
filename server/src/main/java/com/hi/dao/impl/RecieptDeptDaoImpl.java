package com.hi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.RecieptDeptDao;
import com.hi.model.RecieptDept;

@Repository("recieptDeptDao")
public class RecieptDeptDaoImpl extends AbstractDao implements RecieptDeptDao {

	@Override
	public RecieptDept getRecieptDept(String customerId, String recieptType, String department) {
		String sql = "select r.deptId as \"deptId\", r.customerId as \"customerId\", r.recieptType as \"recieptType\", r.department as \"department\" "
				+ " from T_CATER_RECIEPTDEPT r where r.customerId = :customerId and r.recieptType = :recieptType and r.department = :department order by deptId desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("recieptType", recieptType);
		params.put("department", department);
		return this.getFirstBeanBySql(RecieptDept.class, sql, params);
	}

	@Override
	public boolean createRecieptDept(String customerId, String recieptType, String department) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into T_CATER_RECIEPTDEPT ").append("(deptId, customerId, recieptType, department) ")
				.append("values (seq_cater_recieptdept.nextval, '").append(customerId).append("', '").append(recieptType).append("', '")
				.append(department).append("') ");
		return (this.executiveSql(sb.toString(), null) == 1);
	}
}