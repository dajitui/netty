package com.example.demo;

import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GatewayService {

    private static Map<String, SocketChannel> map = null;

    public  void addGatewayChannel(String id, SocketChannel gateway_channel){
        if(map==null){
            map=new ConcurrentHashMap<>();
        }
        map.put(id, gateway_channel);
    }

    public static Map<String, SocketChannel> getChannels(){
        return map;
    }

    public  SocketChannel getGatewayChannel(String id){
        if(map==null||map.isEmpty()){
            return null;
        }
        return map.get(id);
    }

    public  int removeGatewayChannel(String id){
        if(map.containsKey(id)){
            map.remove(id);
            return 1;
        }else{
            return 0;
        }
    }
}