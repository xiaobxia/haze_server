package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/15.
 */
@Data
@ToString(callSuper = true)
public class MXLocationTopItem {
    @JsonProperty("location_top")
    private String locationTop;
    @JsonProperty("location_top_cnt")
    private int locationTopCnt;

    public static class MXLocationTopItemBuilder{
        private MXLocationTopItem mXTopItem;

        public MXLocationTopItemBuilder(){
            this.mXTopItem = new MXLocationTopItem();
        }

        public static MXLocationTopItemBuilder newBuilder() {
            return new MXLocationTopItemBuilder();
        }

        public MXLocationTopItemBuilder withLocationTop(String locationTop) {
            mXTopItem.locationTop = locationTop;
            return this;
        }

        public MXLocationTopItemBuilder withLocationTopCnt(int locationTopCnt) {
            mXTopItem.locationTopCnt = locationTopCnt;
            return this;
        }

        public MXLocationTopItem build() {
            return mXTopItem;
        }
    }
}
