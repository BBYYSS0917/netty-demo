package org.example.serialize.impl;

import com.alibaba.fastjson.JSON;
import org.example.serialize.Serializer;
import org.example.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz); // 从字节数组中反序列化出对象
    }
}
