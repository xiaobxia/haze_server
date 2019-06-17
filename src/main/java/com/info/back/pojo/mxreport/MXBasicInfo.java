package com.info.back.pojo.mxreport;

import com.info.web.util.StringDateUtils;

/**
 * Created by zhangliang on 17/2/27.
 */
public class MXBasicInfo {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = StringDateUtils.underlineToCamel(key);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class MXBasicInfoBuilder {

        private MXBasicInfo basicInfo;

        public MXBasicInfoBuilder() {
            this.basicInfo = new MXBasicInfo();
        }

        public static MXBasicInfoBuilder newBuilder() {
            return new MXBasicInfoBuilder();
        }

        public MXBasicInfoBuilder withKey(String key) {
            basicInfo.key = key;
            return this;
        }

        public MXBasicInfoBuilder withValue(String value) {
            basicInfo.value = value;
            return this;
        }

        public MXBasicInfo build() {
            return basicInfo;
        }
    }
}
