package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 风险指标-呼出
 *
 * @author zhangpengfei
 * @version Id: MXCallAnalysisInPoint.java,V1.0 17/5/26 12:17 zhangpengfei Exp $
 */
@Data
public class MXCallAnalysisDialPoint {

    @JsonProperty("call_dial_cnt_1m")
    private Long callDialCnt1Month;

    @JsonProperty("call_dial_cnt_3m")
    private Long callDialCnt3Month;

    @JsonProperty("call_dial_cnt_6m")
    private Long callDialCnt6Month;

    @JsonProperty("avg_call_dial_cnt_3m")
    private Double avgCallDialCnt3Month;

    @JsonProperty("avg_call_dial_cnt_6m")
    private Double avgCallDialCnt6Month;

    @JsonProperty("call_dial_time_1m")
    private Long callDialTime1Month;

    @JsonProperty("call_dial_time_3m")
    private Long callDialTime3Month;

    @JsonProperty("call_dial_time_6m")
    private Long callDialTime6Month;

    @JsonProperty("avg_call_dial_time_3m")
    private Long avgCallDialTime3Month;

    @JsonProperty("avg_call_dial_time_6m")
    private Long avgCallDialTime6Month;

    public static class MXCallAnalysisDialPointBuilder {

        private MXCallAnalysisDialPoint riskDialPoint;

        public MXCallAnalysisDialPointBuilder() {
            this.riskDialPoint = new MXCallAnalysisDialPoint();
        }

        public static MXCallAnalysisDialPointBuilder newBuilder() {
            return new MXCallAnalysisDialPointBuilder();
        }

        public MXCallAnalysisDialPointBuilder withCallDialCnt1Month(long callDialCnt1Month) {
            riskDialPoint.callDialCnt1Month = callDialCnt1Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withCallDialCnt3Month(long callDialCnt3Month) {
            riskDialPoint.callDialCnt3Month = callDialCnt3Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withCallDialCnt6Month(long callDialCnt6Month) {
            riskDialPoint.callDialCnt6Month = callDialCnt6Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withAvgCallDialCnt3Month(double avgCallDialCnt3Month) {
            riskDialPoint.avgCallDialCnt3Month = avgCallDialCnt3Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withAvgCallDialCnt6Month(double avgCallDialCnt6Month) {
            riskDialPoint.avgCallDialCnt6Month = avgCallDialCnt6Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withCallDialTime1Month(long callDialTime1Month) {
            riskDialPoint.callDialTime1Month = callDialTime1Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withCallDialTime3Month(long callDialTime3Month) {
            riskDialPoint.callDialTime3Month = callDialTime3Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withCallDialTime6Month(long callDialTime6Month) {
            riskDialPoint.callDialTime6Month = callDialTime6Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withAvgCallDialTime3Month(long avgCallDialTime3Month) {
            riskDialPoint.avgCallDialTime3Month = avgCallDialTime3Month;
            return this;
        }

        public MXCallAnalysisDialPointBuilder withAvgCallDialTime6Month(long avgCallDialTime6Month) {
            riskDialPoint.avgCallDialTime6Month = avgCallDialTime6Month;
            return this;
        }

        public MXCallAnalysisDialPoint build() {
            return riskDialPoint;
        }
    }
}
