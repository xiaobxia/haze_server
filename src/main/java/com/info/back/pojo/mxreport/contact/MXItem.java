package com.info.back.pojo.mxreport.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by vivien on 2017/9/13.
 */
@Data
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MXItem {
    @JsonProperty("item_1m")
    private String item1m;
    @JsonProperty("item_3m")
    private String item3m;
    @JsonProperty("item_6m")
    private String item6m;


    public static class MXItemBuilder{
        private MXItem mXItem;

        public MXItemBuilder(){
            this.mXItem = new MXItem();
        }

        public static MXItemBuilder newBuilder() {
            return new MXItemBuilder();
        }

        public MXItemBuilder withItem1m(String item1m) {
            mXItem.item1m = item1m;
            return this;
        }

        public MXItemBuilder withItem3m(String item3m) {
            mXItem.item3m = item3m;
            return this;
        }

        public MXItemBuilder withItem6m(String item6m) {
            mXItem.item6m = item6m;
            return this;
        }

        public MXItem build() {
            return mXItem;
        }
    }
}
