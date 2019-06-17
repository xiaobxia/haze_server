package com.info.back.pojo.mxreport;

public enum MXAnalysisItemEnums {
    macao("Macao", "澳门地区"),
    police("110", "110"),
    emergency("120", "120"),
    lawyer("Lawyer", "律师"),
    loan("Loan", "贷款"),
    bank("Bank", "银行"),
    credit_card("CreditCard", "信用卡"),
    collection("Collection", "催收公司"),
    agency("Agency", "中介"),
    fraud("Fraud", "诈骗电话"),
    nuisance("Nuisance", "骚扰电话"),
    railway_airway("RailwayAirway", "航旅机构"),
    special_service("SpecialService", "特种服务"),
    express("Express", "快递公司"),
    ins_fin("InsFin", "保险公司"),
    car_firm("CarFirm", "汽车公司"),
    carrier("Carrier", "通信服务机构"),
    court("Court", "法院");

    /** 指标列名 */
    private String code;

    /** 指标中文名 */
    private String desc;

    MXAnalysisItemEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
