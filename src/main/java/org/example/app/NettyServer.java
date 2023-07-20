package org.example.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //监听端口，accept 新连接的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); //处理每一条连接的数据读写的线程组

        ServerBootstrap serverBootstrap = new ServerBootstrap(); //启动引导类
        serverBootstrap.group(bossGroup)
                .channel(NioServerSocketChannel.class) //指定IO 模型为 NIO
                .attr(AttributeKey.newInstance("serverName"), "nettyServer") //给服务端的channel，也就是NioServerSocketChannel指定一些自定义属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) //每条连接对应的channel
                .childOption(ChannelOption.TCP_NODELAY, true) //是否开启Nagle算法
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel ch) {
                        System.out.println("服务端启动中");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println("客户端连接成功");
                    }
                });

        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess())
                    System.out.println("端口绑定成功");
                else
                    System.out.println("端口绑定失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
