package com.xiaomahua.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AppClient {

    public void run() throws InterruptedException {
        //定义线程池 eventloopgroup
        NioEventLoopGroup group = new NioEventLoopGroup();

        //启动客户端需要辅助类
        Bootstrap bootstrap = new Bootstrap();
        try {
        bootstrap = bootstrap.group(group)
                .remoteAddress(new InetSocketAddress(8080))
                //初始化什么样的channel
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MyChannelHandler2());
                    }
                });


            //尝试连接服务器，sync用于阻塞
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //获取channel并且写出数据
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("hello".getBytes(StandardCharsets.UTF_8)));
            //阻塞等待接收消息
            channelFuture.channel().closeFuture().sync();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new AppClient().run();
    }

}
