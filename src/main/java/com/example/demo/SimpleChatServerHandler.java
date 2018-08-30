package com.example.demo;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<String,SocketChannel> map=null;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        String uuid = ctx.channel().id().asLongText();
        //map.put(uuid, (SocketChannel) ctx.channel());
        new GatewayService().addGatewayChannel(uuid, (SocketChannel) incoming);
        map=GatewayService.getChannels();
        System.out.println("a new connect come in: " + uuid);
        System.out.println("map:"+map);
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        //Thread.sleep(1000000);
        System.out.println(LocalTime.now()+" "+incoming.remoteAddress()+":"+s);
        /*if(s.equals("hi")){
            SimpleChatServerHandler.send();
        }*/
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");

//                stringBuilder./*StringBuilder stringBuilder=new StringBuilder();
//                String[] s1=s.split("\\|",3);
//                stringBuilder.append(s1[0]);
//                channel.writeAndFlush("[" + stringBuilder.toString() + "]" + stringBuilder.toString().substring(s1[0].length()+1,stringBuilder.toString().length()) + "\n");*/append();
            } else {
                channel.writeAndFlush("[you]" + s + "\n");
            }
        }
    }

    public static Map getMap() {
        return map;
    }

    public static void send(){
        //服务端给客户端主动发消息
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                SocketChannel obj = map.get(key);
                System.out.println("channel id is: " + key);
                System.out.println("channel: " + obj.isActive());
                obj.writeAndFlush("hello, it is Server test header ping").addListener(new ChannelFutureListener(){

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("发送成功");
                        } else {
                            System.out.println("发送失败");
                        }
                    }});
        }
    }
}


