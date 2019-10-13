package com.switchvov.network.chat.protocal.codec;

import com.switchvov.network.chat.protocal.packet.MsgPacket;
import com.switchvov.network.chat.protocal.packet.Packet;
import com.switchvov.network.chat.serializer.Serializer;
import com.switchvov.network.chat.serializer.impl.JSONSerializer;
import com.switchvov.network.chat.serializer.impl.ProtoStuffSerializer;
import com.switchvov.network.chat.serializer.impl.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.switchvov.network.chat.protocal.commond.Commond.MSG;

/**
 * Packet编解码类
 * <br/>
 * 通信协议
 * 魔数0x12345678(4字节)|版本号(1字节)|序列化算法(1字节)|指令(1字节)|数据长度(4字节)|数据(N字节)
 *
 * @author switch
 * @since 2019/10/12
 */
public class PacketCodeC {
    public static final PacketCodeC INSTANCE = new PacketCodeC(new ProtoStuffSerializer());

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(MSG, MsgPacket.class);

        serializerMap = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
        Serializer protoStuffSerializer = new ProtoStuffSerializer();
        serializerMap.put(protoStuffSerializer.getSerializerAlgorithm(), protoStuffSerializer);
        Serializer kryoSerializer = new KryoSerializer();
        serializerMap.put(kryoSerializer.getSerializerAlgorithm(), kryoSerializer);
    }


    private Serializer serializer;

    public PacketCodeC(Serializer serializer) {
        this.serializer = serializer;
    }

    public ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = serializer.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(serializer.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
