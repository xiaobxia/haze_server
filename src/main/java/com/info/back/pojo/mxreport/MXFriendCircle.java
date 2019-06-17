package com.info.back.pojo.mxreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangliang on 17/2/28.
 */
@Data
public class MXFriendCircle {

    private List<MXBasicInfo> summary;

    @JsonProperty("peer_num_top_list")
    private List<MXPeerNumTopList> peerNumTopList;

    @JsonProperty("location_top_list")
    private List<MXLocationTopList> locationTopList;

}
