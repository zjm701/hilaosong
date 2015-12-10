package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.Pagination;
import com.hi.common.SystemSetting;
import com.hi.dao.AbstractDao;
import com.hi.dao.DishDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.Pack;
import com.hi.model.PackDish;

@Repository("dishDao")
public class DishDaoImpl extends AbstractDao implements DishDao {
	
//	private static String sql1 = " and storeId = :storeId and dishsourcetype = '2' and isDisplay in ('2','3') and isStopped = '1'";
	private static String sql1 = " and storeId = :storeId and isDisplay in ('2','3') and isStopped = '1'";

	private static String sql2 = " and dishcategory = :categoryId " + sql1;

	public List<DishType> getCategories4Dish(String storeId) {
		String sql = "select t.dishtypeid as \"dishTypeId\", t.dishtypename as \"dishTypeName\" " 
				+ " from T_CATER_DISHTYPE t "
				+ " where t.identifier = '1' and t.dishtypeid not in (" + SystemSetting.getOutOfDishCategories() 
				+ " ) and t.dishtypeid in ("
				+ " select distinct d.dishcategory from T_CATER_DISH d where d.type = '1' and d.dishShareType in ('1', '3') " + sql1 
				+ " ) order by t.dishtypeid asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		return this.getBeansBySql(DishType.class, sql, params);
	}

	public List<DishType> getCategories4Wine(String storeId) {
		String sql = "select t.dishtypeid as \"dishTypeId\", t.dishtypename as \"dishTypeName\" "
				+ " from T_CATER_DISHTYPE t " 
				+ " where t.identifier = '1' and t.dishtypeid not in (" + SystemSetting.getOutOfDishCategories() 
				+ " ) and t.dishtypeid in ("
				+ " select distinct d.dishclass from T_CATER_DISH d where d.type = '1' and d.dishShareType in ('1', '3') " + sql1
				+ " and d.dishcategory = " + SystemSetting.getWine() 
				+ " ) order by dishtypeid asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		return this.getBeansBySql(DishType.class, sql, params);
	}
	
	public int countDishes(String storeId, String categoryId) {
		String sql = "select * from T_CATER_DISH d where d.type = '1'  and d.dishShareType in ('1', '3') " + sql2;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("categoryId", categoryId);

		return this.countBySql(sql, params);
	}
	public int countDishesByDishCass(String storeId, String categoryId) {
		String sql = "select * from T_CATER_DISH d where d.type = '1' and d.dishclass = :dishclass and d.dishShareType in ('1', '3') " + sql2;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		if(categoryId.indexOf("006") == 0){
			params.put("categoryId", "006");
			params.put("dishclass", categoryId);
		}else{
			params.put("categoryId", categoryId);
			params.put("dishclass", categoryId+"001");
		}

		return this.countBySql(sql, params);
	}
	
	public List<Dish> getDishes(String storeId, String categoryId, int pageIndex, int pageSize) {
		System.out.println("==> storeId:" + storeId + ", categoryId:" + categoryId);
		String sql = "select d.dishId as \"dishId\", d.storeDishId as \"storeDishId\", d.storeDishName as \"storeDishName\", d.unitPrice as \"unitPrice\", "
				+ " i.imgurl as \"bigImageAddr\", d.type as \"type\", hd.dishId as \"halfDishId\", hd.storeDishId as \"halfStoreDishId\", hd.unitPrice as \"halfPrice\" "
				+ " from T_CATER_DISH d "
				+ " inner join t_cater_dishtype dt on d.dishCategory = dt.dishTypeID "
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
	            + " left join (select d2.linkStoreDishId, d2.dishId, d2.storeDishId, d2.unitPrice from T_CATER_DISH d2"
				+ "		where d2.type = '1' and d2.dishShareType = '2' " + sql2 + " ) hd"
				+ "	on hd.linkStoreDishId = d.storeDishId "
				+ " where d.type = '1'  and d.dishShareType in ('1', '3') " + sql2 + " order by dishSort asc, d.dishId asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);//dishclass
		params.put("categoryId", categoryId);
//		params.put("dishclass", categoryId+"001");

		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);
		pagn.setPageSize(pageSize);

		return this.getBeansBySql(Dish.class, sql, params, pagn);
	}

	public List<Dish> getDishesByDishCass(String storeId, String categoryId, int pageIndex, int pageSize) {
		System.out.println("==> storeId:" + storeId + ", categoryId:" + categoryId);
		String sql = "select d.dishId as \"dishId\", d.storeDishId as \"storeDishId\", d.storeDishName as \"storeDishName\", d.unitPrice as \"unitPrice\", "
				+ " i.imgurl as \"bigImageAddr\", d.type as \"type\", hd.dishId as \"halfDishId\", hd.storeDishId as \"halfStoreDishId\", hd.unitPrice as \"halfPrice\" "
				+ " from T_CATER_DISH d "
				+ " inner join t_cater_dishtype dt on d.dishCategory = dt.dishTypeID "
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
	            + " left join (select d2.linkStoreDishId, d2.dishId, d2.storeDishId, d2.unitPrice from T_CATER_DISH d2"
				+ "		where d2.type = '1' and d2.dishclass = :dishclass and d2.dishShareType = '2' " + sql2 + " ) hd"
				+ "	on hd.linkStoreDishId = d.storeDishId "
				+ " where d.type = '1' and d.dishclass = :dishclass and d.dishShareType in ('1', '3') " + sql2 + " order by dishSort asc, d.dishId asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);//dishclass
		if(categoryId.indexOf("006") == 0){
			params.put("categoryId", "006");
			params.put("dishclass", categoryId);
		}else{
			params.put("categoryId", categoryId);
			params.put("dishclass", categoryId+"001");
		}

		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);
		pagn.setPageSize(pageSize);

		return this.getBeansBySql(Dish.class, sql, params, pagn);
	}
	private static String DIY_SQL1 = " and dishclass = '001006' and storeId = :storeId and isDisplay in ('2','3') and isStopped = '1'";

	//private static String DIY_SQL2 = " and dishcategory = :categoryId " + DIY_SQL1;
	/**
	 * diy锅底
	 *  d.storeid = '020115'                                     
        and d.dishClass = '001006'                    
        and d.isstopped = 1 
        and d.type = 1;
	 */
	@Override
	public List<Dish> getDiyDishes(String storeId, String categoryId, int pageIndex,
			int pageSize) {
		
		System.out.println("==> storeId:" + storeId + ", categoryId:" + categoryId);
		String sql = "select d.dishId as \"dishId\", d.storeDishId as \"storeDishId\", d.storeDishName as \"storeDishName\", d.unitPrice as \"unitPrice\", "
				+ " i.imgurl as \"bigImageAddr\", d.type as \"type\", hd.dishId as \"halfDishId\", hd.storeDishId as \"halfStoreDishId\", hd.unitPrice as \"halfPrice\" "
				+ " from T_CATER_DISH d "
				+ " inner join t_cater_dishtype dt on d.dishCategory = dt.dishTypeID "
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
	            + " left join (select d2.linkStoreDishId, d2.dishId, d2.storeDishId, d2.unitPrice from T_CATER_DISH d2"
				+ "		where d2.type = '1' and d2.dishShareType = '2' " + DIY_SQL1 + " ) hd"
				+ "	on hd.linkStoreDishId = d.storeDishId "
				+ " where d.dishType='01' and d.type = '1' and d.dishShareType in ('1', '3') " 
				+ DIY_SQL1 + " order by dishSort asc, d.dishId asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
//		params.put("categoryId", "012");
//		params.put("dishClass", "001006");
//		params.put("isStopped", "01");

		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);
		pagn.setPageSize(pageSize);

		return this.getBeansBySql(Dish.class, sql, params, pagn);
	}
	public Dish getDishDetail(String dishId) {
		String sql = "select d.dishid as \"dishId\", d.dishname as \"dishName\", d.guideprice as \"guidePrice\", d.unitprice as \"unitPrice\", "
				+ " d.description as \"description\", d.isrequired as \"isRequired\", d.dishunit as \"dishUnit\", d.dishweight as \"dishWeight\", "
				+ " d.dishsharetype as \"dishShareType\", d.isrecommend as \"isRecommend\", d.linkstoredishid as \"linkStoreDishId\", d.type as \"type\", "
				+ " i.imgurl as \"bigImageAddr\", d.mediumImageAddr as \"mediumImageAddr\", d.storeDishId as \"storeDishId\" "
				+ " from T_CATER_DISH d "
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
				+ " where d.dishId = :dishId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dishId", dishId);
		return this.getFirstBeanBySql(Dish.class, sql, params);
	}
	
	public int countPacks(String storeId, String categoryId) {
		String sql = "select * from T_CATER_DISH d where d.type = '2' and d.packType = '1' " + sql2;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("categoryId", categoryId);

		return this.countBySql(sql, params);
	}
	/**
	 * 套餐
	 */
	public List<Pack> getPacks(String storeId, String categoryId, int pageIndex) {
		System.out.println("==> storeId:" + storeId + ", categoryId:" + categoryId);
		String sql = "select d.dishId as \"packId\", d.dishId as \"dishId\", d.storeDishId as \"storeDishId\", d.storeDishName as \"storeDishName\", "
				+ "  d.unitPrice as \"unitPrice\", i.imgurl as \"bigImageAddr\", d.type as \"type\" "
				+ " from T_CATER_DISH d "
				+ " inner join T_CATER_DISHTYPE dt on d.dishCategory = dt.dishTypeID "
				+ " inner join T_CATER_DISHTYPE dt on d.dishCategory = dt.dishTypeID "
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
				+ " where d.type = '2' and d.packType = '1' and storeId = :storeId  and isStopped = '1' order by d.dishSort asc, d.dishId asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		//params.put("categoryId", categoryId);

		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);

		return this.getBeansBySql(Pack.class, sql, params, pagn);
	}
	
	public List<PackDish> getPackDishes(String packId) {
		String sql = "select p.packId as \"packId\", p.dishId as \"dishId\", p.dishNumber as \"dishNumber\", "
				+ " p.innerId as \"innerId\", p.innerNumber as \"innerNumber\", p.innerName as \"innerName\", "
				+ " d.dishName as \"dishName\", d.unitPrice as \"unitPrice\", "
				+ " i.imgurl as \"bigImageAddr\", d.mediumImageAddr as \"mediumImageAddr\" " + " from T_CATER_PACKDISH p "
				+ " inner join T_CATER_DISH d on d.dishId = p.dishId " 
				+ "	left join T_CATER_IMAGE i on d.dishId = i.dishId "
				+ " where p.packId = :packId ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("packId", packId);
		List<PackDish> dishes = this.getBeansBySql(PackDish.class, sql, params);
		return dishes;
	}
}
