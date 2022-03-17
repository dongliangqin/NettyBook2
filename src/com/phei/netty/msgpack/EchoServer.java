package com.phei.netty.msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Copyright(C),2019-2022,Code For ONE PIECE
 * FileName: EchoServer
 * Author: dongliangqin
 * Date: 2022/2/13 16:21
 * Description :
 * History:
 * <author>          <time>           <version>          <desc>
 * 作者姓名           修改时间           版本号               描述
 */
public class EchoServer {
    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                    .addLast("decoder",new MsgPackDecoder())
                                    .addLast("frameEncoder",new LengthFieldPrepender(2))
                                    .addLast("encoder",new MsgPackEncoder())
                                    .addLast(new EchoServerHandler());
                        }
                    });
//          绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
//          等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args.length>0&&args!=null){
            port = Integer.parseInt(args[0]);
        }
        new EchoServer().bind(port);

    }
}
