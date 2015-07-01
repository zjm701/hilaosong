package com.hi.model;

import java.math.BigDecimal;

public class Count {
	private BigDecimal count;

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public long longValue() {
		return this.count.longValue();
	}
}
