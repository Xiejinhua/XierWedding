package com.mazhang.xierwedding.Utils;


import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import timber.log.Timber;


public class SignUtil {
    /**
     * 加密签名
     * @param object
     * @param key
     * @return
     */
    public static String sign(Object object, String key) {
        Map<String, Object> data = BeanUtil.objectToMap(object);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return sign(data, key);
    }

    /**
     * 签名
     *
     * @param data
     * @param key  appid
     * @return
     */
    public static String sign(Map<String, Object> data, String key) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        TreeMap<String, Object> map = new TreeMap<String, Object>(data);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object k = entry.getKey();
            if ("class".equals(k) || "key".equals(k) || "sign".equals(k)) {
                continue;
            }
            Object v = entry.getValue(); // 非空
            if (v == null || "".equals(v.toString())) {
                continue;
            }
            buf.append(k);
            buf.append("=");
            buf.append(v);
            buf.append("&");
        }
        buf.append("key=").append(key);
        Timber.d("-------------- SHA256签名前 -------------");
        Timber.d(buf.toString());
        return ShaUtil.getSHA256(buf.toString()).toUpperCase();
    }

    /**
     * @param data      验签的参数
     * @param keySecret 密钥
     * @return String 签名后的值
     * @Description： 停简单请求参数签名算法
     * @author Xiquan.Liu@desay-svautomotive.com
     * @Date 2019年7月31日上午10:03:58
     */
    public static String easyParkingSign(Map<String, Object> data, String keySecret) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> map = new TreeMap<String, Object>(data);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object k = entry.getKey();
            if ("signType".equals(k) || "sign".equals(k)) {
                continue;
            }
            Object v = entry.getValue(); // 非空
            if (v == null || "".equals(v.toString())) {
                continue;
            }
            sb.append(k);
            sb.append("=");
            sb.append(v);
            sb.append("&");
        }
        String strA = sb.substring(0, sb.length() - 1);
        // TODO 密钥需要提供
        String strB = strA + keySecret;
        String sign = ShaUtil.getSHA256(strB).toLowerCase();
        return sign;
    }
}

