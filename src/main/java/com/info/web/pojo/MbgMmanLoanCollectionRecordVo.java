package com.info.web.pojo;

import com.tan66.datawarehouse.openapi.model.mman.MbgMmanLoanCollectionRecord;

public class MbgMmanLoanCollectionRecordVo extends MbgMmanLoanCollectionRecord {

    private String overdueDays;

    private String repaymentDescript;

    private String reductionDescript;

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getRepaymentDescript() {
        return repaymentDescript;
    }

    public void setRepaymentDescript(String repaymentDescript) {
        this.repaymentDescript = repaymentDescript;
    }

    public String getReductionDescript() {
        return reductionDescript;
    }

    public void setReductionDescript(String reductionDescript) {
        this.reductionDescript = reductionDescript;
    }

    @Override
    public String toString() {

        return super.toString().concat("MbgMmanLoanCollectionRecordVo{" +
                "overdueDays='" + this.overdueDays + '\'' +
                ", repaymentDescript='" + this.repaymentDescript + '\'' +
                ", reductionDescript='" + this.reductionDescript + '\'' +
                '}');
    }
}
