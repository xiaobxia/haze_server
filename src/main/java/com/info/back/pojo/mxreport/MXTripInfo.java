package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/8.
 */
@Data
public class MXTripInfo {

    @JsonProperty("trip_dest")
    private String tripDest;

    @JsonProperty("trip_start_time")
    private String tripStartTime;

    @JsonProperty("trip_end_time")
    private String tripEndTime;

    @JsonProperty("trip_leave")
    private String tripLeave;

    @JsonProperty("trip_type")
    private String tripType;
}
