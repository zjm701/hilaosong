package com.hi.dao;

import java.util.List;

import com.hi.model.DiyGuodi;

public interface DiyGuodiDao {

	long countDiyGuodis(String userId);
	
	List<DiyGuodi> getDiyGuodis(String userId, int pageIndex);

	boolean createDiyGuodi(DiyGuodi g);

	boolean deleteDiyGuodi(long id);
}
