package com.baidu.ftpfile.utils;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpConnectUtil {
    /**
     * 向FTP服务器上传文件
     * 
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path FTP服务器保存目录或者下载目录
     * @return 成功返回FTPClient，否则返回null
     */
    public static FTPClient ftpConnect(String url, int port, String userName, String passWord){
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(url, port);//连接FTP服务器 
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
            ftp.login(userName, passWord);//登录 
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) { 
                ftp.disconnect(); 
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            ftp = null;
        }
        return ftp;
    }
}
