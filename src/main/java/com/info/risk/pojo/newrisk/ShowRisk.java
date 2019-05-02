package com.info.risk.pojo.newrisk;

import com.info.risk.pojo.Advice;

import java.util.List;

public class ShowRisk {
    private List<String> itemsList;
    private Advice advice;

    public List<String> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<String> itemsList) {
        this.itemsList = itemsList;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
