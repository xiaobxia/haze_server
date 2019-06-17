package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
public class MXMobileTopItem {
    @JsonProperty("mobile_top")
    private String mobileTop;
    @JsonProperty("mobile_top_cnt")
    private int mobileTopCnt;

    public static class MXTopItemBuilder{
        private MXMobileTopItem mXTopItem;

        public MXTopItemBuilder(){
            this.mXTopItem = new MXMobileTopItem();
        }

        public static MXTopItemBuilder newBuilder() {
            return new MXTopItemBuilder();
        }

        public MXTopItemBuilder withMobileTop(String mobileTop) {
            mXTopItem.mobileTop = mobileTop;
            return this;
        }

        public MXTopItemBuilder withMobileTopCnt(int mobileTopCnt) {
            mXTopItem.mobileTopCnt = mobileTopCnt;
            return this;
        }


        public MXMobileTopItem build() {
            return mXTopItem;
        }
    }
}
