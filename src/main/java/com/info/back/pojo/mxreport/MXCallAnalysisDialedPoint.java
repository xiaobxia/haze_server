package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 风险指标-被叫
 *
 * @author zhangpengfei
 * @version Id: MXCallAnalysisInPoint.java,V1.0 17/5/26 12:17 zhangpengfei Exp $
 */
@Data
public class MXCallAnalysisDialedPoint {

    @JsonProperty("call_dialed_cnt_1m")
    private Long callDialedCnt1Month;

    @JsonProperty("call_dialed_cnt_3m")
    private Long callDialedCnt3Month;

    @JsonProperty("call_dialed_cnt_6m")
    private Long callDialedCnt6Month;

    @JsonProperty("avg_call_dialed_cnt_3m")
    private Double avgCallDialedCnt3Month;

    @JsonProperty("avg_call_dialed_cnt_6m")
    private Double avgCallDialedCnt6Month;

    @JsonProperty("call_dialed_time_1m")
    private Long callDialedTime1Month;

    @JsonProperty("call_dialed_time_3m")
    private Long callDialedTime3Month;

    @JsonProperty("call_dialed_time_6m")
    private Long callDialedTime6Month;

    @JsonProperty("avg_call_dialed_time_3m")
    private Long avgCallDialedTime3Month;

    @JsonProperty("avg_call_dialed_time_6m")
    private Long avgCallDialedTime6Month;

    public static class MXCallAnalysisDialedPointBuilder {

        private MXCallAnalysisDialedPoint riskDialedPoint;

        public MXCallAnalysisDialedPointBuilder() {
            this.riskDialedPoint = new MXCallAnalysisDialedPoint();
        }

        public static MXCallAnalysisDialedPointBuilder newBuilder() {
            return new MXCallAnalysisDialedPointBuilder();
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedCnt1Month(long callDialedCnt1Month) {
            riskDialedPoint.callDialedCnt1Month = callDialedCnt1Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedCnt3Month(long callDialedCnt3Month) {
            riskDialedPoint.callDialedCnt3Month = callDialedCnt3Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedCnt6Month(long callDialedCnt6Month) {
            riskDialedPoint.callDialedCnt6Month = callDialedCnt6Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withAvgCallDialedCnt3Month(double avgCallDialedCnt3Month) {
            riskDialedPoint.avgCallDialedCnt3Month = avgCallDialedCnt3Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withAvgCallDialedCnt6Month(double avgCallDialedCnt6Month) {
            riskDialedPoint.avgCallDialedCnt6Month = avgCallDialedCnt6Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedTime1Month(long callDialedTime1Month) {
            riskDialedPoint.callDialedTime1Month = callDialedTime1Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedTime3Month(long callDialedTime3Month) {
            riskDialedPoint.callDialedTime3Month = callDialedTime3Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withCallDialedTime6Month(long callDialedTime6Month) {
            riskDialedPoint.callDialedTime6Month = callDialedTime6Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withAvgCallDialedTime3Month(long avgCallDialedTime3Month) {
            riskDialedPoint.avgCallDialedTime3Month = avgCallDialedTime3Month;
            return this;
        }

        public MXCallAnalysisDialedPointBuilder withAvgCallDialedTime6Month(long avgCallDialedTime6Month) {
            riskDialedPoint.avgCallDialedTime6Month = avgCallDialedTime6Month;
            return this;
        }

        public MXCallAnalysisDialedPoint build() {
            return riskDialedPoint;
        }
    }
}
