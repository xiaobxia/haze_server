package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXMobileCnt {
    private MXItem item;
    @JsonProperty("app_point")
    private String appPoint;
    @JsonProperty("app_point_zh")
    private String appPointZh;

    public static class MXMobileCntBuilder{
        private MXMobileCnt mxMobileCnt;

        public MXMobileCntBuilder(){
            this.mxMobileCnt = new MXMobileCnt();
        }

        public static MXMobileCntBuilder newBuilder() {
            return new MXMobileCntBuilder();
        }

        public MXMobileCntBuilder withItem(MXItem item) {
            mxMobileCnt.item = item;
            return this;
        }

        public MXMobileCntBuilder withAppPoint(String appPoint) {
            mxMobileCnt.appPoint = appPoint;
            return this;
        }

        public MXMobileCntBuilder withAppPonitZh(String appPointZh) {
            mxMobileCnt.appPointZh = appPointZh;
            return this;
        }

        public MXMobileCnt build() {
            return mxMobileCnt;
        }
    }
}
