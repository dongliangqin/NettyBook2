package com.phei.netty.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Copyright(C),2019-2022,Code For ONE PIECE
 * FileName: EchoClientV2
 * Author: dongliangqin
 * Date: 2022/2/13 17:25
 * Description :
 * History:
 * <author>          <time>           <version>          <desc>
 * 作者姓名           修改时间           版本号               描述
 */

public class EchoClientV2 {
    public void connection(int port,String host) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                    .addLast("msgpack decoder",new MsgPackDecoder())
                                    .addLast("frameEncoder",new LengthFieldPrepender(2))
                                    .addLast("msgpack encoder",new MsgPackEncoder())
                                    .addLast(new EchoClientHandler());
//
                        }
                    });
//            发起异步连接操作
            ChannelFuture f = b.connect(host,port).sync();
//                          等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args.length>0&&args!=null){
            System.out.println(args[0]);
            port = Integer.parseInt(args[0]);
        }
        new EchoClient().connection(port,"127.0.0.1");
    }
}

