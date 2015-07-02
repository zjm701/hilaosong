package com.hi.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.Pagination;
import com.hi.dao.AbstractDao;
import com.hi.dao.DiyGuodiDao;
import com.hi.model.DiyGuodi;

@Repository("diyGuodiDao")
public class DiyGuodiDaoImpl extends AbstractDao implements DiyGuodiDao {

	@Override
	public long countDiyGuodis(String userId) {
		String sql = "select * from T_CATER_GUODIDIY g where g.deltag = '0' and g.userId = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.countBySql(sql, params);
	}

	@Override
	public List<DiyGuodi> getDiyGuodis(String userId, int pageIndex) {
		String sql = "select g.id as \"guodiId\", g.guodidiyname as \"guodiName\", g.main_guodiid as \"dishId\", g.assist_guodiid as \"dishId2\", "
				+ " d1.bigimageaddr as \"bigImageAddr\", d1.type as \"type\", case when (d2.unitprice is null) or (d1.unitprice >= d2.unitprice) then d1.unitprice else d2.unitprice end as \"unitPrice\", "
				+ " case when d1.unitprice is null then d2.dishname when d2.unitprice is null then d1.dishname when d1.unitprice >= d2.unitprice then d1.dishname || '+' || d2.dishname else d2.dishname || '+' || d1.dishname end as \"dishName\" "
				+ " from T_CATER_GUODIDIY g "
				+ " inner join t_cater_dish d1 on g.main_guodiid = d1.dishid "
				+ " left outer join t_cater_dish d2 on g.assist_guodiid = d2.dishid " + " where g.deltag = '0' and userId = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);
		return this.getBeansBySql(DiyGuodi.class, sql, params, pagn);
	}

	@Override
	public boolean createDiyGuodi(DiyGuodi g) {
		g.setGuodiId(new BigDecimal(getGuodiId()));
		StringBuilder sb = new StringBuilder();
		sb.append("insert into T_CATER_GUODIDIY ")
				.append("(id, guodidiyname, main_guodiid, assist_guodiid, detail, userid, deltag, type) values (")
				.append(g.getGuodiId().longValue()).append(", '").append(g.getGuodiName()).append("', '").append(g.getDishId())
				.append("', '").append(g.getDishId2()).append("', ").append("(select d.dishname from T_CATER_DISH d where d.dishid = '")
				.append(g.getDishId()).append("') || '+' || (select d2.dishname from t_cater_dish d2 where d2.dishid = '")
				.append(g.getDishId2()).append("'), '").append(g.getUserId()).append("', '0' , '1') ");
		return (this.executiveSql(sb.toString(), null) == 1);
	}

	@Override
	public boolean updateDiyGuodi(DiyGuodi g) {
		StringBuilder sb = new StringBuilder();
		sb.append("update T_CATER_GUODIDIY g set g.guodidiyname = '").append(g.getGuodiName()).append("', g.main_guodiid = '")
				.append(g.getDishId()).append("', g.assist_guodiid = '").append(g.getDishId2())
				.append("', g.detail = (select d.dishname from t_cater_dish d where d.dishid = '").append(g.getDishId())
				.append("') || '+' || (select d2.dishname from t_cater_dish d2 where d2.dishid = '").append(g.getDishId2())
				.append("') where g.id = ").append(g.getGuodiId().longValue());
		return (this.executiveSql(sb.toString(), null) == 1);
	}

	/* 
	 * soft delete
	 * 
	 */
	@Override
	public boolean deleteDiyGuodi(long id) {
		String sql = "update T_CATER_GUODIDIY g set g.deltag = '-1' where g.id = :id"; 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return (this.executiveSql(sql, params) == 1);
	}

	private long getGuodiId() {
		return this.getNextvalBySeqName("SEQ_CATER_GUODIDIY");
	}
}