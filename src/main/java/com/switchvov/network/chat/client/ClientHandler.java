package com.switchvov.network.chat.client;

import com.switchvov.network.chat.common.MsgRepository;
import com.switchvov.network.chat.protocal.codec.PacketCodeC;
import com.switchvov.network.chat.protocal.packet.MsgPacket;
import com.switchvov.network.chat.protocal.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.switchvov.network.chat.common.MsgConstant.*;

/**
 * @author switch
 * @since 2019/10/12
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger counter = new AtomicInteger();
    private long startTime;
    private Map<Integer, Long> countTimeMapping = new LinkedHashMap<>(3);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        // 判断是否是消息请求数据包
        if (!(packet instanceof MsgPacket)) {
            return;
        }
        MsgPacket msgPacket = (MsgPacket) packet;
        Integer session = msgPacket.getSession();
        switch (session) {
            case MSG_SESSION_ONE: {
                printMsg(msgPacket);
                sendMsg(ctx, MSG_SESSION_ONE);
                sendMsg(ctx, MSG_SESSION_TWO);
                sendMsg(ctx, MSG_SESSION_THREE);
                break;
            }
            case MSG_SESSION_TWO: {
                printMsg(msgPacket);
                break;
            }
            case MSG_SESSION_THREE: {
                printMsg(msgPacket);
                int count = counter.incrementAndGet();
                if (count == COUNT_LEVEL_1 || count == COUNT_LEVEL_2 || count == COUNT_LEVEL_3) {
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    System.out.println("遍历" + count + "次，花费:" + time + "ms");
                    countTimeMapping.put(count, time);
                    System.out.println(countTimeMapping);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void sendMsg(ChannelHandlerContext ctx, Integer sessionId) {
        MsgPacket liMsgPacket = MsgRepository.getInstance().getLiMsgPacket(sessionId);
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(liMsgPacket);
        ctx.writeAndFlush(byteBuf);
    }

    private void printMsg(MsgPacket packet) {
//        System.out.println("张大爷说：【" + packet.getSession() + ":" + packet.getContent() + "】");
    }

    public Map<Integer, Long> getCountTimeMapping() {
        return countTimeMapping;
    }
}
