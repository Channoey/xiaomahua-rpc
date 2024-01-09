package com.xiaomahua;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;

public class NettyTest {

    @Test
    public void testByteBuf(){
        ByteBuf header = Unpooled.buffer();
        ByteBuf body = Unpooled.buffer();

        //逻辑组装代替物理拷贝，实现jvm中零拷贝
        CompositeByteBuf byteBuf = Unpooled.compositeBuffer();
        byteBuf.addComponents(header,body);
    }

    @Test
    public void testWrapper(){
        byte[] buf1 = new byte[1024];
        byte[] buf2 = new byte[1024];

        //共享数组内容（引用），而不拷贝
        ByteBuf byteBuf = Unpooled.wrappedBuffer(buf1,buf2);

    }

}
