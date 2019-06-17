package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/15.
 */
@Data
@ToString(callSuper = true)
public class MXLocationCntItem {
    @JsonProperty("location_top")
    private String locationTop;
    @JsonProperty("location_cnt")
    private int locationCnt;

    public static class MXLocationCntItemBuilder{
        private MXLocationCntItem mXLocationCntItem;

        public MXLocationCntItemBuilder(){
            this.mXLocationCntItem = new MXLocationCntItem();
        }

        public static MXLocationCntItemBuilder newBuilder() {
            return new MXLocationCntItemBuilder();
        }

        public MXLocationCntItemBuilder withLocationTop(String locationTop) {
            mXLocationCntItem.locationTop = locationTop;
            return this;
        }

        public MXLocationCntItemBuilder withLocationCnt(int locationCnt) {
            mXLocationCntItem.locationCnt = locationCnt;
            return this;
        }


        public MXLocationCntItem build() {
            return mXLocationCntItem;
        }
    }

}
