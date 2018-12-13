package com.zongzhang.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zongzhang
 * @since 1.0
 * <p>
 * create on 2018-12-13
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final ChannelGroup group;

    private static final ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public TextWebSocketFrameHandler() {
        this.group = null;
    }

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            System.out.println(text);
            allChannels.stream()
                    .filter(c -> c != ctx.channel())
                    .forEach(c -> {
                        msg.retain();
                        c.writeAndFlush(msg.duplicate());
                    });
        } else {
            throw new UnsupportedOperationException("Invalid websocket frame received");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        allChannels.add(ctx.channel());
    }


}
