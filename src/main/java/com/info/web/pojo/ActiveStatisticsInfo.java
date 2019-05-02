/**   
 * <p>Title: ActiveStatisticsInfo.java</p>
 * @Package com.info.web.pojo 
 * <p>Description: 活动数据统计实体类</p> 
 * <p>Company:小鱼儿</p>
 * @author lixingxing
 * @since 2017年3月14日 上午11:44:26 
 * @version V1.0   
 */
package com.info.web.pojo;

import java.util.Date;

/** 
 * <p>Description: 活动数据统计实体类</p> 
 * <p>Company:小鱼儿</p>
 * @author lixingxing
 * @version V1.0 
 */
public class ActiveStatisticsInfo
{
   
    private int id;//自增id
    
    private String activeType;//活动类型 1-你来借我来还
    
    private long clickNum;//页面访问人数
    
    private long applyNum;//申请借款人数
    
    private long borrowNum;//借款成功人数
    
    private Date createDate;//创建时间
    
    private Date updateDate;//更新时间
    
    private String isDel;//是否删除标志
    
    private String statisticsDate;//免单时间

    /** 
     * @return id 
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /** 
     * @return activeType 
     */
    public String getActiveType()
    {
        return activeType;
    }

    /**
     * @param activeType the activeType to set
     */
    public void setActiveType(String activeType)
    {
        this.activeType = activeType;
    }

    /** 
     * @return createDate 
     */
    public Date getCreateDate()
    {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    /** 
     * @return updateDate 
     */
    public Date getUpdateDate()
    {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    /** 
     * @return isDel 
     */
    public String getIsDel()
    {
        return isDel;
    }

    /**
     * @param isDel the isDel to set
     */
    public void setIsDel(String isDel)
    {
        this.isDel = isDel;
    }

    /** 
     * @return statisticsDate 
     */
    public String getStatisticsDate()
    {
        return statisticsDate;
    }

    /**
     * @param statisticsDate the statisticsDate to set
     */
    public void setStatisticsDate(String statisticsDate)
    {
        this.statisticsDate = statisticsDate;
    }

    /** 
     * @return clickNum 
     */
    public long getClickNum()
    {
        return clickNum;
    }

    /**
     * @param clickNum the clickNum to set
     */
    public void setClickNum(long clickNum)
    {
        this.clickNum = clickNum;
    }

    /** 
     * @return applyNum 
     */
    public long getApplyNum()
    {
        return applyNum;
    }

    /**
     * @param applyNum the applyNum to set
     */
    public void setApplyNum(long applyNum)
    {
        this.applyNum = applyNum;
    }

    /** 
     * @return borrowNum 
     */
    public long getBorrowNum()
    {
        return borrowNum;
    }

    /**
     * @param borrowNum the borrowNum to set
     */
    public void setBorrowNum(long borrowNum)
    {
        this.borrowNum = borrowNum;
    }
    
    
    
}
