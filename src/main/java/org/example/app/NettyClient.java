package org.example.app;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); //处理每一条连接的数据读写的线程组

        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class) //指定IO 模型为 NIO
                .attr(AttributeKey.newInstance("clientName"), "nettyClient") //给服务端的channel，也就是NioServerSocketChannel指定一些自定义属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //连接的超时时间
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    //指定连接数据读写逻辑
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
//        bootstrap.connect("juejin.cn", 80).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("连接成功");
//            } else {
//                System.out.println("连接失败");
//            }
//        });
        connect(bootstrap, "juejin.cn", 80);
    }

    private static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败");
                connect(bootstrap, host, port);
            }
        });
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.out.println("重试次数已用完，放弃连接");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.out.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
