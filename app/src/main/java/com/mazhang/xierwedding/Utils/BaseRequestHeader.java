package com.mazhang.xierwedding.Utils;

import java.io.Serializable;


public class BaseRequestHeader implements Serializable {
    /**
     * 后台提供给调用者的应用Id
     */
    private String appKey;
    /**
     * 签名值,防参数篡改,签名业务参数
     */
    private String sign;
    /**
     * 毫秒级别时间戳(13位)
     */
    private long timestamp;

    public BaseRequestHeader(String appKey, String sign, long timestamp) {
        this.appKey = appKey;
        this.sign = sign;
        this.timestamp = timestamp;
    }
}
