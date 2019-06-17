package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by vivien on 2017/9/18.
 */
@Data
public class MXTimeDivide {

    @JsonProperty("time_interval")
    private String timeInterval;

    @JsonProperty("peer_num_cnt")
    private Long peerNumCnt;

    public static class MXTimeDivideBuilder {

        private MXTimeDivide timeDivide;

        public MXTimeDivideBuilder() {
            this.timeDivide = new MXTimeDivide();
        }

        public static MXTimeDivideBuilder newBuilder() {
            return new MXTimeDivideBuilder();
        }

        public MXTimeDivideBuilder withTimeInterval(String timeInterval) {
            timeDivide.timeInterval = timeInterval;
            return this;
        }

        public MXTimeDivideBuilder withPeerNumCnt(Long peerNumCnt) {
            timeDivide.peerNumCnt = peerNumCnt;
            return this;
        }

        public MXTimeDivide build() {
            return timeDivide;
        }
    }
}
