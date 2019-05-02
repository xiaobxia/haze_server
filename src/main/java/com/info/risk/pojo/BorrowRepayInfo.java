package com.info.risk.pojo;

import com.tan66.datawarehouse.openapi.model.asset.MbgAssetBorrowOrder;
import com.tan66.datawarehouse.openapi.model.asset.MbgAssetRepayment;

/**
 * @author jintian
 * @date 2018/10/24 11:36
 */
public class BorrowRepayInfo {

    private MbgAssetBorrowOrder borrowOrder;

    private MbgAssetRepayment repayment;

    public BorrowRepayInfo(MbgAssetBorrowOrder borrowOrder, MbgAssetRepayment repayment) {
        this.borrowOrder = borrowOrder;
        this.repayment = repayment;
    }

    public BorrowRepayInfo() {
    }

    public MbgAssetBorrowOrder getBorrowOrder() {
        return borrowOrder;
    }

    public void setBorrowOrder(MbgAssetBorrowOrder borrowOrder) {
        this.borrowOrder = borrowOrder;
    }

    public MbgAssetRepayment getRepayment() {
        return repayment;
    }

    public void setRepayment(MbgAssetRepayment repayment) {
        this.repayment = repayment;
    }
}
