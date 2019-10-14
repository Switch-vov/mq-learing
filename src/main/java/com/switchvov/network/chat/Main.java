package com.switchvov.network.chat;

import com.switchvov.network.chat.client.LiClient;
import com.switchvov.network.chat.common.MsgRepository;
import com.switchvov.network.chat.protocal.codec.PacketCodeC;
import com.switchvov.network.chat.protocal.packet.MsgPacket;
import com.switchvov.network.chat.server.ZhangServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import static com.switchvov.network.chat.client.LiClient.*;
import static com.switchvov.network.chat.common.MsgConstant.*;

/**
 * @author switch
 * @since 2019/10/13
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = ZhangServer.bootstrap();
        ChannelFuture serverChannelFuture = ZhangServer.bind(serverBootstrap, PORT);
        Bootstrap clientBootstrap = LiClient.bootstrap();
        ChannelFuture clientChannelFuture = LiClient.connect(clientBootstrap, HOST, PORT, MAX_RETRY);
        serverChannelFuture.await();
        clientChannelFuture.await();
        for (int i = 0; i < COUNT_LEVEL_2; i++) {
            MsgPacket one = MsgRepository.getInstance().getZhangMsgPacket(MSG_SESSION_ONE);
            MsgPacket two = MsgRepository.getInstance().getLiMsgPacket(MSG_SESSION_TWO);
            MsgPacket three = MsgRepository.getInstance().getLiMsgPacket(MSG_SESSION_THREE);
            sendMsg(ZhangServer.getChannel(HOST), one);
            sendMsg(clientChannelFuture.channel(), two);
            sendMsg(clientChannelFuture.channel(), three);
        }
    }

    private static void sendMsg(Channel channel, MsgPacket packet) {
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(packet);
        channel.writeAndFlush(byteBuf);
    }
}
