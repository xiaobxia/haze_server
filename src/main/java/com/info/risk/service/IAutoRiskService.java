package com.info.risk.service;
import com.info.risk.pojo.CreditReport;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.pojo.Supplier;
import com.info.risk.pojo.newrisk.ShowRisk;
import com.info.risk.pojo.newrisk.ShuJuMoHeVo;

import java.util.List;
import java.util.Map;

public interface IAutoRiskService {
    //public void autoRisk();

    /**
     *
     * @param creditReport
     * @param supplier
     * @return 返回页面报文的数据
     */
    public Map<String,Object> getRiskData(CreditReport creditReport, String supplier);


    public ShuJuMoHeVo getShuJuMoHe(RiskOrders riskOrders);

    public ShowRisk getShowRiskFromSupplier(List<Supplier> supplierList, CreditReport creditReport);
}
