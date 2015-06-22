package com.hi.model;

import java.math.BigDecimal;

public abstract class Pagenation {
	/**
	 * 用于oracle，分页，必须带，否则报错
	 */
	private BigDecimal ROWNUM_;

	public BigDecimal getROWNUM_() {
		return ROWNUM_;
	}

	public void setROWNUM_(BigDecimal rOWNUM_) {
		ROWNUM_ = rOWNUM_;
	}
	
	
}
