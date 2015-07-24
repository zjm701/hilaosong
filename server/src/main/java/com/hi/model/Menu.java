package com.hi.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	public static int FIRST_LEVEL = 1;

	public static int SECOND_LEVEL = 2;

	private String id;

	private String name;

	private int level = FIRST_LEVEL; // 菜单级别

	private String dishTypeId;

	private List<Menu> children = null;

	public Menu() {
	}

	public Menu(String id, String name) {
		this(id, name, SECOND_LEVEL);// 默认都是第二级菜单
	}

	public Menu(String id, String name, int level) {
		this.id = id;
		this.name = name;
		this.level = level;
	}

	public Menu(DishType dish) {
		this(dish, SECOND_LEVEL);// 默认都是第二级菜单
	}

	public Menu(DishType dish, int level) {
		this.name = dish.getDishTypeName();
		this.dishTypeId = dish.getDishTypeId();
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDishTypeId() {
		return dishTypeId;
	}

	public void setDishTypeId(String dishTypeId) {
		this.dishTypeId = dishTypeId;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void addChild(Menu menu) {
		if (this.children == null) {
			this.children = new ArrayList<Menu>();
		}
		this.children.add(menu);
	}

	public void removeChildren() {
		if (this.children != null) {
			this.children.clear();
		}
	}
}
