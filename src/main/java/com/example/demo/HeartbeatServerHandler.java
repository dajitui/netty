package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {


    private int loss_connect_time = 0;

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if(evt instanceof IdleStateEvent){
            //服务端对应着读事件，当为READER_IDLE时触发
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.READER_IDLE){
                loss_connect_time++;
                System.out.println("接收消息超时");
                if(loss_connect_time > 2){
                    System.out.println("关闭不活动的链接");
                    ctx.channel().close();
                }
            }else{
                super.userEventTriggered(ctx,evt);
            }
        }
    }
}
