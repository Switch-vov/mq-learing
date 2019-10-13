package com.switchvov.network.chat.protocal.packet;

import lombok.Data;

/**
 * @author switch
 * @since 2019/10/12
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();
}
