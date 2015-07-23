package com.hi.dao;

import java.util.List;

import com.hi.model.DiyGuodi;

public interface DiyGuodiDao {

	int countDiyGuodis(String userId);
	
	List<DiyGuodi> getDiyGuodis(String userId, int pageIndex);

	boolean createDiyGuodi(DiyGuodi g);
	
	boolean updateDiyGuodi(DiyGuodi g);

	boolean deleteDiyGuodi(long id);
}
