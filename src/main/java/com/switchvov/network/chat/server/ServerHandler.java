package com.switchvov.network.chat.server;

import com.switchvov.network.chat.common.MsgCounter;
import com.switchvov.network.chat.common.MsgRepository;
import com.switchvov.network.chat.protocal.codec.PacketCodeC;
import com.switchvov.network.chat.protocal.packet.MsgPacket;
import com.switchvov.network.chat.protocal.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Optional;

import static com.switchvov.network.chat.common.MsgConstant.*;

/**
 * @author switch
 * @since 2019/10/12
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("CLIENT: " + getRemoteAddress(ctx) + " 接入连接");
        // 往channel map中添加channel信息
        ZhangServer.putChannel(getHostString(ctx), ctx.channel());
    }

    private static String getRemoteAddress(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx.channel().remoteAddress().toString()).orElse("").replace("/", "");
    }

    private static String getHostString(ChannelHandlerContext ctx) {
        String address = getRemoteAddress(ctx);
        return address.substring(0, address.indexOf(":"));
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
                MsgCounter.count();
                printMsg(msgPacket);
                break;
            }
            case MSG_SESSION_TWO: {
                MsgCounter.count();
                printMsg(msgPacket);
                sendMsg(ctx, MSG_SESSION_TWO);
                break;
            }
            case MSG_SESSION_THREE: {
                MsgCounter.count();
                printMsg(msgPacket);
                sendMsg(ctx, MSG_SESSION_THREE);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void sendMsg(ChannelHandlerContext ctx, Integer sessionId) {
        MsgPacket liMsgPacket = MsgRepository.getInstance().getZhangMsgPacket(sessionId);
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(liMsgPacket);
        ctx.writeAndFlush(byteBuf);
    }

    private void printMsg(MsgPacket packet) {
//        System.out.println("李大爷说：【" + packet.getSession() + ":" + packet.getContent() + "】");
    }
}
