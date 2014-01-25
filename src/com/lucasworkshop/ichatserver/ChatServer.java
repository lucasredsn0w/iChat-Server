package com.lucasworkshop.ichatserver;
import java.net.*;

import org.jboss.netty.*;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


import java.util.*;
import java.util.concurrent.Executors;
public class ChatServer {
    public static final int PORT = 12973;
    ServerBootstrap bootstrap;
    ChannelGroup channelgroup;
    public void init(){
    	bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        final ChatServerHandler handler = new ChatServerHandler();
    }
}
