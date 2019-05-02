package com.info.web.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabelCountBase {
    private Integer id;

    private Date countDate;

    private Byte customerType;

    private Byte userFrom;

    private Byte userType;

    private Integer userNum;

    private List<LabelCountPageResult> pageResults;

}