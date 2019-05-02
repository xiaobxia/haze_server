package com.info.web.pojo;

import lombok.Data;

/**
 * 标签占比
 *
 * @author cqry_2016
 * @create 2018-11-12 15:28
 **/
@Data
public class LabelCountPageResult {
    private String name;
    private Integer jobKind;
    private Integer num = 0;
    private Double rate = 0d;
    private Double morningRate = 0d;
    private Double nightRate = 0d;
    private String mapK;



    public String getMapK(Integer type) {
        return type == null ? String.join("-",name,jobKind == null ? "" : jobKind.toString())
                : String.join("-",name,type.toString());
    }

    public String getMapK() {
        return String.join("-",name,jobKind == null ? "" : jobKind.toString());
    }

}
