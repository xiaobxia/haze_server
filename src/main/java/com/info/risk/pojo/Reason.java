package com.info.risk.pojo;


import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhang on 2017-11-06.
 */
public class Reason implements Comparable<Reason>, Serializable {
    private static final long serialVersionUID = -1049415888315399405L;
    //根据来源排序，来源必须有值
    private String supplier;//第三方来源
    private Advice advice;//建议
    private String explain = "";//解释
    private Map<String, String> items = new TreeMap<String, String>();//细项

    public static Reason getReasonWithException(String explain, Supplier supplier) {
        Reason reason = new Reason();
        reason.setAdvice(Advice.REVIEW);
        reason.setExplain(explain);
        reason.setSupplier(supplier.toString());
        return reason;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Advice getAdvice() {
        return this.advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public int compareTo(Reason o) {
        String s1 = this.getSupplier();
        String s2 = o.getSupplier();
        char[] cs1 = s1.toCharArray();
        char[] cs2 = s2.toCharArray();
        if (s1.length() == s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                if (cs1[i] < cs2[i]) {
                    return -1;
                }
                if (cs1[i] > cs2[i]) {
                    return 1;
                }
            }
        }
        if (s1.length() < s2.length()) {
            return -1;
        }
        if (s1.length() > s2.length()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Reason{" +
                "supplier='" + supplier + '\'' +
                ", advice=" + advice +
                ", explain='" + explain + '\'' +
                ", items=" + items +
                '}';
    }
}
