package com.zongzhang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
 * create on 2018/6/14
 */
public class FirClient {
//    public static void main(String[] args) {
//    }
//    public void start() throws Exception {
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(8989))
//                    .childHandler(
//                            new ChannelInitializer<SocketChannel>() {
//                                @Override
//                                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                                    socketChannel.pipeline().addLast();
//                                }
//                            }
//                    );
//            ChannelFuture f = b.bind().sync();
//            f.channel().closeFuture().sync();
//        } finally {
//            group.shutdownGracefully().sync();
//        }
//
//
//    }
}
