package com.switchvov.network.chat.serializer;

/**
 * @author switch
 * @since 2019/10/12
 */
public interface SerializerAlgorithm {
    /**
     * json 序列化标识
     */
    byte JSON = 1;

    /**
     * protostuff 序列化标识
     */
    byte PROTO_STUFF = 2;

    /**
     * kryo 序列化标识
     */
    byte KRYO = 3;
}
