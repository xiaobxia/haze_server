package com.info.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IRegisterStatisticDao {
	public long registerSumCount();
	
	public long registerTodayCount();
	public long findAllApprove();
	public Long findOneApprove1();
	public Long findOneApprove2();
	public Long findOneApprove3();
	public Long findOneApprove4();

	public Long findOneApprove5();
	
	
	
	
	public long registerThisdayCount(String nowDate);
	public long registerThisWeekCount(String nowDate);
	public long registerThisMonthCount(String nowDate);
}
