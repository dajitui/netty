package com.example.demo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.JavassistTypeParameterMatcherGenerator;

import java.util.Iterator;
import java.util.Map;

import static io.netty.util.internal.PlatformDependent.getClassLoader;

public class hi {
    public static void main(String[] args) {
        /*Map map = SimpleChatServerHandler.getMap();
        System.out.println(map);*/

       SimpleChatServerHandler.send();
        System.out.println(SimpleChatServerHandler.getMap());
        //System.out.println(GatewayService.getChannels());
        /*Channel channel=GatewayService.getGatewayChannel("68f728fffe3aea32-000009f8-00000001-0f8aed4b023a591d-d3f9328d");
        if(channel.isActive()){
            System.out.println("1");
        }else{
            System.out.println("2");
        }*/

        /*new Test().bb();
        System.out.println(Test.map);*/
        /*Runnable sendTask = new Runnable() {
            @Override
            public void run() {
                sendTaskLoop:
                for(;;){
                    System.out.println("task is beginning...");
                    try{
                        Map<String, SocketChannel> map = GatewayService.getChannels();
                        System.out.println(map);
                        Iterator<String> it = map.keySet().iterator();
                        while (it.hasNext()) {
                            String key = it.next();
                            SocketChannel obj = map.get(key);
                            System.out.println("channel id is: " + key);
                            System.out.println("channel: " + obj.isActive());
                            obj.writeAndFlush("hello, it is Server test header ping").addListener(new ChannelFutureListener() {

                                @Override
                                public void operationComplete(ChannelFuture future) throws Exception {
                                    if (future.isSuccess()) {
                                        System.out.println("发送成功");
                                    } else {
                                        System.out.println("发送失败");
                                    }
                                }
                            });;
                        }
                    }catch(Exception e){break sendTaskLoop;}
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(sendTask).start();*/
    }
}
