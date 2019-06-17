package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXRegionItem {

    @JsonProperty("region_loc")
    private String regionLoc;

    @JsonProperty("region_uniq_num_cnt")
    private Long regionUniqNumCnt;

    @JsonProperty("region_call_cnt")
    private Long regionCallCnt;

    @JsonProperty("region_call_time")
    private Long regionCallTime;

    @JsonProperty("region_dial_cnt")
    private Long regionDialCnt;

    @JsonProperty("region_dial_time")
    private Long regionDialTime;

    @JsonProperty("region_dialed_cnt")
    private Long regionDialedCnt;

    @JsonProperty("region_dialed_time")
    private Long regionDialedTime;

    @JsonProperty("region_avg_dial_time")
    private Long regionAvgDialTime;

    @JsonProperty("region_avg_dialed_time")
    private Long regionAvgDialedTime;

    @JsonProperty("region_dial_cnt_pct")
    private Double regionDialCntPct;

    @JsonProperty("region_dial_time_pct")
    private Double regionDialTimePct;

    @JsonProperty("region_dialed_cnt_pct")
    private Double regionDialedCntPct;

    @JsonProperty("region_dialed_time_pct")
    private Double regionDialedTimePct;

}
