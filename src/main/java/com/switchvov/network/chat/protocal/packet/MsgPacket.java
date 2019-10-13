package com.switchvov.network.chat.protocal.packet;

import lombok.*;

import static com.switchvov.network.chat.protocal.commond.Commond.MSG;

/**
 * @author switch
 * @since 2019/10/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MsgPacket extends Packet {
    /**
     * 序号
     */
    private Integer no;

    /**
     * 会话ID
     */
    private Integer session;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    @Override
    public Byte getCommand() {
        return MSG;
    }
}
