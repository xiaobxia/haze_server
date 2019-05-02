package com.info.back.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface IApplyBorrowStatisticDao {
	
	public long applyBorrowOrderSumCount();
	
	public long applyBorrowOrderTodayCount();
	
	public long applySuccTodayCount();
	
}
