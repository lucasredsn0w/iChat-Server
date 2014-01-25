package com.lucasworkshop.ichatserver;
import java.util.Date;
import java.util.Scanner;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class ServerHandler extends SimpleChannelHandler {
    public static final ChannelGroup channelGroup = new DefaultChannelGroup(
	    "server-channel-group");
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	    throws Exception {
	e.getCause().printStackTrace();
	Channel channel = e.getChannel();
	channel.close();
	if (channelGroup.contains(channel)) {
	    channelGroup.remove(channel);
	}
    }
    @SuppressWarnings("deprecation")
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	    throws Exception {
	String content = (String) e.getMessage();
	System.out.println("�յ�С������Ϣ   ʱ�䣺" + new Date().toLocaleString()
		+ " ��Ϣ����:/n" + content);
	if (content.equalsIgnoreCase("bye")) {
	    e.getChannel().close();
	    channelGroup.remove(e.getChannel());
	    return;
	}
	System.out.println("�ظ���Ϣ(Enter������Ϣ)��");
	Scanner scanner = new Scanner(System.in);
	if (scanner.hasNext()) {
	    final String msg = scanner.next();
	    ChannelFuture future = e.getChannel().write(msg);
	    future.addListener(new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future)
			throws Exception {
		    System.out.println("��Ϣ���ͳɹ���");
		    if (msg.equalsIgnoreCase("bye")) {
			future.getChannel().close();
			channelGroup.remove(future.getChannel());
			return;
		    }
		}
	    });
	}
    }
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	    throws Exception {
	channelGroup.add(e.getChannel());
    }
    public static ChannelGroup getChannelGroup() {
	return channelGroup;
    }
}
