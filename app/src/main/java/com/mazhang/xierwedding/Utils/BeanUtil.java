package com.mazhang.xierwedding.Utils;


import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;


public class BeanUtil {

    /**
     * 通过反射获取obj对应的map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Gson gson = new Gson();
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.get(obj) instanceof List) {
                    map.put(field.getName(), gson.toJson(field.get(obj)));
                    continue;
                }
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Timber.e(e.toString());
        }
        return map;
    }
}
