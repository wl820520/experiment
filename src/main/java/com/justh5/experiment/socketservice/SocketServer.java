package com.justh5.experiment.socketservice;

import com.justh5.experiment.domain.ChannelModel;
import com.justh5.experiment.domain.OSCRespModel;
import com.justh5.experiment.util.CommonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger logger = LogManager.getLogger(SocketServer.class);
    private Charset cs = Charset.forName("UTF-8");
    //接受数据缓冲区
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    //发送数据缓冲区
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    //选择器（叫监听器更准确些吧应该）
    private static Selector selector;
    public ChannelModel channelModel=new ChannelModel();
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
                        try {
                            handle(key);
                        }catch (Exception ex){
                            logger.error("handel异常",ex);
                        }
                    }
                    selectionKeys.clear();//清除处理过的事件
                }catch (Exception ex){
                    logger.error("while 异常：",ex);
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
        try {
            String requestMsg = "";
            int count = 0;
            if (selectionKey.isAcceptable()) {
                //每有客户端连接，即注册通信信道为可读
                logger.info("客户端连接：");
                if(channelModel!=null&&channelModel.getSocketChannel()!=null&&channelModel.getSocketChannel().isConnected()){
                    channelModel.getSocketChannel().close();
                }
                serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);

                channelModel.setSocketChannel(socketChannel);
                channelModel.setRandKey(CommonHelper.getRandomString(10));
                //channelModel.setOscRespModelList(new ArrayList<>());
                //channelModel.setResp("");
                //channelModels.add(channelModel);
            } else if (selectionKey.isReadable()) {
                socketChannel = (SocketChannel) selectionKey.channel();
                rBuffer.clear();
                count = socketChannel.read(rBuffer);
                //读取数据
                if (count > 0) {
                    rBuffer.flip();
                    requestMsg = String.valueOf(cs.decode(rBuffer).array());
                }
                if (!StringUtils.isEmpty(requestMsg)) {
                    String responseMsg = "已收到客户端的消息:" + requestMsg;
                    if(requestMsg.contains("res|")){
                        String[] resList=requestMsg.split("\\|");
                        if(resList.length>2) {
                            String deviceName=resList[1];
                            String resMsg =resList[2];
                            logger.info("收到resMsg:" + resMsg);
                            if(!StringUtils.isEmpty(resMsg)) {
                                boolean hasfind = false;
                                if (channelModel.getOscRespModelList() != null && channelModel.getOscRespModelList().size() > 0) {
                                    for (OSCRespModel oscRespModel : channelModel.getOscRespModelList()) {
                                        if (!StringUtils.isEmpty(oscRespModel.getDeviceName()) && !StringUtils.isEmpty(deviceName) && oscRespModel.getDeviceName().toLowerCase().equals(deviceName.toLowerCase())) {
                                            hasfind = true;
                                            oscRespModel.setResp(resMsg);
                                            logger.info("hasfind :" + resMsg);
                                        }
                                    }
                                }
                                if (!hasfind) {
                                    OSCRespModel oscRespModel = new OSCRespModel();
                                    oscRespModel.setDeviceName(deviceName);
                                    oscRespModel.setResp(resMsg);
                                    if(channelModel.getOscRespModelList()==null||channelModel.getOscRespModelList().size()<=0) {
                                        channelModel.setOscRespModelList(new ArrayList<>());
                                    }
                                    channelModel.getOscRespModelList().add(oscRespModel);
                                    logger.info("new channelModel " + resMsg);
                                }
                            }
                        }
                    }else{
                        //返回数据
                        responseMsg="server get message ok";
                        sBuffer = ByteBuffer.allocate(responseMsg.getBytes("UTF-8").length);
                        sBuffer.put(responseMsg.getBytes("UTF-8"));
                        sBuffer.flip();
                        socketChannel.write(sBuffer);
                    }
                }
                //socketChannel.close();
            } else if (selectionKey.isConnectable()) {
                logger.error("isConnectable");
            } else if (selectionKey.isWritable()) {
                logger.error("write");
            }
        }catch (Exception ex){
            logger.error("socket异常：",ex);
//            for(ChannelModel channelModel:channelModels) {
//                if(channelModel.getSocketChannel()==socketChannel) {
//                    System.out.println("remove chanel");
//                    channelModels.remove(channelModel);
//                }
//            }
            selectionKey.cancel();
            serverSocketChannel.socket().close();
            serverSocketChannel.close();
            socketChannel.socket().close();
            socketChannel.close();
        }
    }
}
