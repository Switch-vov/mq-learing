package com.switchvov.network.chat.common;

import com.switchvov.network.chat.protocal.packet.MsgPacket;

import java.util.HashMap;
import java.util.Map;

import static com.switchvov.network.chat.common.MsgConstant.*;

/**
 * @author switch
 * @since 2019/10/12
 */
public class MsgRepository {
    private static MsgRepository repository = new MsgRepository();
    private static Map<Integer, MsgPacket> zhangMsgMapping;
    private static Map<Integer, MsgPacket> liMsgMapping;

    static {
        zhangMsgMapping = new HashMap<>(3);
        zhangMsgMapping.put(MSG_SESSION_ONE, new MsgPacket(1, MSG_SESSION_ONE, USER_ID_ZHANG, "吃了没，您呐？"));
        zhangMsgMapping.put(MSG_SESSION_TWO, new MsgPacket(2, MSG_SESSION_TWO, USER_ID_ZHANG, "嗨，没事儿溜溜弯儿。"));
        zhangMsgMapping.put(MSG_SESSION_THREE, new MsgPacket(3, MSG_SESSION_THREE, USER_ID_ZHANG, "回头去给老太太请安！"));

        liMsgMapping = new HashMap<>(3);
        liMsgMapping.put(MSG_SESSION_ONE, new MsgPacket(4, MSG_SESSION_ONE, USER_ID_LI, "刚吃。"));
        liMsgMapping.put(MSG_SESSION_TWO, new MsgPacket(5, MSG_SESSION_TWO, USER_ID_LI, "您这，嘛去？"));
        liMsgMapping.put(MSG_SESSION_THREE, new MsgPacket(6, MSG_SESSION_THREE, USER_ID_LI, "有空家里坐坐啊。"));
    }

    private MsgRepository() {

    }

    public static MsgRepository getInstance() {
        return repository;
    }

    public MsgPacket getZhangMsgPacket(Integer sessionId) {
        return zhangMsgMapping.get(sessionId);
    }

    public MsgPacket getLiMsgPacket(Integer sessionId) {
        return liMsgMapping.get(sessionId);
    }
}
