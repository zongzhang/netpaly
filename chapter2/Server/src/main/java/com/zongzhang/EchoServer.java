package com.zongzhang;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author zongzhang
 * @since 1.0
 * <p>
 * create on 2018/5/24
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: ");
//            return;
//        }
        int port = Integer.parseInt("8989");
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 1.创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            // 3.指定所使用的NIO传输Channel
            b.group(group).channel(NioServerSocketChannel.class)
                    // 4.使用指定的端口
                    .localAddress(new InetSocketAddress(port))
                    // 5.添加一个EchoServerHandler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 6.异步绑定服务器；调用sync()方法直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 7.获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 8.关闭EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
