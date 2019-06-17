package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by zhangliang on 17/2/8.
 */
@Data
public class MXCheckBlackInfo {

    @JsonProperty("phone_gray_score")
    private Long phoneGrayScore;

    @JsonProperty("contacts_class1_blacklist_cnt")
    private Long contactsClass1BlacklistCnt;

    @JsonProperty("contacts_class2_blacklist_cnt")
    private Long contactsClass2BlacklistCnt;

    @JsonProperty("contacts_class1_cnt")
    private Long contactsClass1Cnt;

    @JsonProperty("contacts_router_cnt")
    private Long contactsRouterCnt;

    @JsonProperty("contacts_router_ratio")
    private Double contactsRouterRatio;

    // Add by zhangpengfei 2017-05-26
    public static class MXCheckBlackInfoBuilder {

        private MXCheckBlackInfo checkBlackInfo;

        public MXCheckBlackInfoBuilder() {
            this.checkBlackInfo = new MXCheckBlackInfo();
        }

        public static MXCheckBlackInfoBuilder newBuilder() {
            return new MXCheckBlackInfoBuilder();
        }

        public MXCheckBlackInfoBuilder withValue(Long phoneGrayScore, Long contactsClass1BlacklistCnt, Long contactsClass2BlacklistCnt,
                                                 Long contactsClass1Cnt, Long contactsRouterCnt, Double contactsRouterRatio) {
            checkBlackInfo.phoneGrayScore = phoneGrayScore;
            checkBlackInfo.contactsClass1BlacklistCnt = contactsClass1BlacklistCnt;
            checkBlackInfo.contactsClass2BlacklistCnt = contactsClass2BlacklistCnt;
            checkBlackInfo.contactsClass1Cnt = contactsClass1Cnt;
            checkBlackInfo.contactsRouterCnt = contactsRouterCnt;
            checkBlackInfo.contactsRouterRatio = contactsRouterRatio;
            return this;
        }

        public MXCheckBlackInfoBuilder withPhoneGrayScore(Long phoneGrayScore) {
            checkBlackInfo.phoneGrayScore = phoneGrayScore;
            return this;
        }

        public MXCheckBlackInfoBuilder withContactsClass1BlacklistCnt(Long contactsClass1BlacklistCnt) {
            checkBlackInfo.contactsClass1BlacklistCnt = contactsClass1BlacklistCnt;
            return this;
        }

        public MXCheckBlackInfoBuilder withContactsClass2BlacklistCnt(Long contactsClass2BlacklistCnt) {
            checkBlackInfo.contactsClass2BlacklistCnt = contactsClass2BlacklistCnt;
            return this;
        }

        public MXCheckBlackInfoBuilder withContactsClass1Cnt(Long contactsClass1Cnt) {
            checkBlackInfo.contactsClass1Cnt = contactsClass1Cnt;
            return this;
        }

        public MXCheckBlackInfoBuilder withContactsRouterCnt(Long contactsRouterCnt) {
            checkBlackInfo.contactsRouterCnt = contactsRouterCnt;
            return this;
        }

        public MXCheckBlackInfoBuilder withContactsRouterRatio(Double contactsRouterRatio) {
            checkBlackInfo.contactsRouterRatio = contactsRouterRatio;
            return this;
        }

        public MXCheckBlackInfo build() {
            return checkBlackInfo;
        }
    }
}
