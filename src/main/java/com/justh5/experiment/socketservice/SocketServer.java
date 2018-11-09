package com.justh5.experiment.socketservice;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SocketServer {
    private Charset cs = Charset.forName("UTF-8");
    //接受数据缓冲区
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    //发送数据缓冲区
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    //选择器（叫监听器更准确些吧应该）
    private static Selector selector;
    public List<SocketChannel> socketChannelList=new ArrayList<>();
    /**
     * 启动socket服务，开启监听
     *
     * @param port
     * @throws IOException
     */
    public void startSocketServer(int port) {
        if(selector!=null&& selector.isOpen()){
            //System.out.println("socket online");
            return;
        }
        try {
            //打开通信信道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //获取套接字
            ServerSocket serverSocket = serverSocketChannel.socket();
            //绑定端口号
            serverSocket.bind(new InetSocketAddress(port));
            //打开监听器
            selector = Selector.open();
            //将通信信道注册到监听器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //监听器会一直监听，如果客户端有请求就会进入相应的事件处理
            while (true) {
                try {
                    selector.select();//select方法会一直阻塞直到有相关事件发生或超时
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();//监听到的事件
                    for (SelectionKey key : selectionKeys) {
                        handle(key);
                    }
                    selectionKeys.clear();//清除处理过的事件
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理不同的事件
     *
     * @param selectionKey
     * @throws IOException
     */
    private void handle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        String requestMsg = "";
        int count = 0;
        if (selectionKey.isAcceptable()) {
            //每有客户端连接，即注册通信信道为可读
            System.out.println("客户端连接：");
            serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            socketChannelList.add(socketChannel);
        } else if (selectionKey.isReadable()) {
            socketChannel = (SocketChannel) selectionKey.channel();
            rBuffer.clear();
            count = socketChannel.read(rBuffer);
            //读取数据
            if (count > 0) {
                rBuffer.flip();
                requestMsg = String.valueOf(cs.decode(rBuffer).array());
            }
            if(!StringUtils.isEmpty(requestMsg)) {
                String responseMsg = "已收到客户端的消息:" + requestMsg;
                System.out.println(responseMsg);
            }
            //返回数据
            //sBuffer = ByteBuffer.allocate(responseMsg.getBytes("UTF-8").length);
            //sBuffer.put(responseMsg.getBytes("UTF-8"));
            //sBuffer.flip();
            //socketChannel.write(sBuffer);
            //socketChannel.close();
        }else if(selectionKey.isConnectable()){
            System.out.println("isConnectable");
        }else if(selectionKey.isWritable()){
            System.out.println("write");
        }
    }

}
