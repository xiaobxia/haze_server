package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXCallAnalysis {

    @JsonProperty("analysis_item")
    private String analysisItem;

    @JsonProperty("analysis_desc")
    private String analysisDesc;

    @JsonProperty("analysis_point")
    private MXCallAnalysisPoint analysisPoint;

    public MXCallAnalysis(){};
    
    public MXCallAnalysis(String analysisItem, String analysisDesc, MXCallAnalysisPoint analysisPoint) {
        this.analysisItem = analysisItem;
        this.analysisDesc = analysisDesc;
        this.analysisPoint = analysisPoint;
    }

}
