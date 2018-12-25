package com.justh5.experiment.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtil {
    private  final static String  FTP_HOST="39.96.3.133";
    private  final static int     FTP_PORT=1802;
    private  final static String  USER_NAME="root";
    private  final static String  PASS_WORD="wl.820520";


    /**
     * Description:     向FTP服务器上传文件
     * @param filename  上传到FTP服务器上的文件名
     * @param input     输入流
     * @return          成功返回true，否则返回false
     */
    public static boolean uploadFile(String filename,String filepath, InputStream input) {
        boolean  result=false;
        FTPClient ftp = new FTPClient();
        //ftp.setConnectTimeout(5*1000);
        try {
            int reply;
            ftp.connect(FTP_HOST, FTP_PORT);
            ftp.login(USER_NAME, PASS_WORD);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(filepath))
                ftp.changeWorkingDirectory(filepath);
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            ftp.setBufferSize(1024);
//            //解决上传中文 txt 文件乱码
//            ftp.setControlEncoding("GBK");
//            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
//            conf.setServerLanguageCode("zh");
//            String fileName = new String(filename.getBytes("GBK"),"iso-8859-1");

            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }

}
