package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 *
 * 用于控制 callAnalysisDialPoint callAnalysisDialedPoint 是否在响应结果中显示
 * Created by zhangliang on 17/2/28.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MXCallAnalysisPoint {

    @JsonProperty("call_cnt_1m")
    private Long callCnt1Month;

    @JsonProperty("call_cnt_3m")
    private Long callCnt3Month;

    @JsonProperty("call_cnt_6m")
    private Long callCnt6Month;

    @JsonProperty("avg_call_cnt_3m")
    private Double avgCallCnt3Month;

    @JsonProperty("avg_call_cnt_6m")
    private Double avgCallCnt6Month;

    @JsonProperty("call_time_1m")
    private Long callTime1Month;

    @JsonProperty("call_time_3m")
    private Long callTime3Month;

    @JsonProperty("call_time_6m")
    private Long callTime6Month;

    @JsonProperty("avg_call_time_3m")
    private Long avgCallTime3Month;

    @JsonProperty("avg_call_time_6m")
    private Long avgCallTime6Month;

    @JsonProperty("call_analysis_dial_point")
    private MXCallAnalysisDialPoint callAnalysisDialPoint;

    @JsonProperty("call_analysis_dialed_point")
    private MXCallAnalysisDialedPoint callAnalysisDialedPoint;

    public static class MXRiskPointBuilder {

        private MXCallAnalysisPoint riskPoint;

        public MXRiskPointBuilder() {
            this.riskPoint = new MXCallAnalysisPoint();
        }

        public static MXRiskPointBuilder newBuilder() {
            return new MXRiskPointBuilder();
        }

        public MXRiskPointBuilder withCallCnt1Month(long callCnt1Month) {
            riskPoint.callCnt1Month = callCnt1Month;
            return this;
        }

        public MXRiskPointBuilder withCallCnt3Month(long callCnt3Month) {
            riskPoint.callCnt3Month = callCnt3Month;
            return this;
        }

        public MXRiskPointBuilder withCallCnt6Month(long callCnt6Month) {
            riskPoint.callCnt6Month = callCnt6Month;
            return this;
        }

        public MXRiskPointBuilder withAvgCallCnt3Month(double avgCallCnt3Month) {
            riskPoint.avgCallCnt3Month = avgCallCnt3Month;
            return this;
        }

        public MXRiskPointBuilder withAvgCallCnt6Month(double avgCallCnt6Month) {
            riskPoint.avgCallCnt6Month = avgCallCnt6Month;
            return this;
        }

        public MXRiskPointBuilder withCallTime1Month(long callTime1Month) {
            riskPoint.callTime1Month = callTime1Month;
            return this;
        }

        public MXRiskPointBuilder withCallTime3Month(long callTime3Month) {
            riskPoint.callTime3Month = callTime3Month;
            return this;
        }

        public MXRiskPointBuilder withCallTime6Month(long callTime6Month) {
            riskPoint.callTime6Month = callTime6Month;
            return this;
        }

        public MXRiskPointBuilder withAvgCallTime3Month(long avgCallTime3Month) {
            riskPoint.avgCallTime3Month = avgCallTime3Month;
            return this;
        }

        public MXRiskPointBuilder withAvgCallTime6Month(long avgCallTime6Month) {
            riskPoint.avgCallTime6Month = avgCallTime6Month;
            return this;
        }

        public MXRiskPointBuilder withCallAnalysisDialPoint(MXCallAnalysisDialPoint callAnalysisDialPoint) {
            riskPoint.callAnalysisDialPoint = callAnalysisDialPoint;
            return this;
        }

        public MXRiskPointBuilder withCallAnalysisDialedPoint(MXCallAnalysisDialedPoint callAnalysisDialedPoint) {
            riskPoint.callAnalysisDialedPoint = callAnalysisDialedPoint;
            return this;
        }

        public MXCallAnalysisPoint build() {
            return riskPoint;
        }
    }
}
