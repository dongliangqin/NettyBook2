package com.phei.netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Copyright(C),2019-2022,Code For ONE PIECE
 * FileName: MsgpackEncoder
 * Author: dongliangqin
 * Date: 2022/2/13 16:12
 * Description :
 * History:
 * <author>          <time>           <version>          <desc>
 * 作者姓名           修改时间           版本号               描述
 */

public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] bytes = pack.write(o);
        byteBuf.writeBytes(bytes);
    }
}
