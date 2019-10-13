package com.switchvov.network.chat.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.switchvov.network.chat.serializer.Serializer;
import com.switchvov.network.chat.serializer.SerializerAlgorithm;

/**
 * @author switch
 * @since 2019/10/12
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
