package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXBasicCheckItem {

    @JsonProperty("check_item")
    private String checkItem;

    private String result;

    private String comment;

    public static class MXCheckPointBuilder {

        private MXBasicCheckItem checkPoint;

        public MXCheckPointBuilder() {
            this.checkPoint = new MXBasicCheckItem();
        }

        public static MXCheckPointBuilder newBuilder() {
            return new MXCheckPointBuilder();
        }

        public MXCheckPointBuilder withCheckItem(String checkItem) {
            checkPoint.checkItem = checkItem;
            return this;
        }

        public MXCheckPointBuilder withResult(String result) {
            checkPoint.result = result;
            return this;
        }

        public MXCheckPointBuilder withComment(String comment) {
            checkPoint.comment = comment;
            return this;
        }

        public MXBasicCheckItem build() {
            return checkPoint;
        }
    }
}
