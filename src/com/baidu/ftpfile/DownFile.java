package com.baidu.ftpfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.baidu.ftpfile.utils.FtpConnectUtil;

/**
 * FTP 下载
 * 
 * @author v_tangyuanliang
 *
 */
public class DownFile {
    /**
     * 下载文件
     * 
     * @param fileName 要下载的文件名
     * @param path  下载到本地的路径
     * @return
     */
    public static boolean downFile (String fileName, String path) {
        boolean success = false;
        FTPClient ftp = FtpConnectUtil.ftpConnect("127.0.0.1", 21, "TYL", "TYL");
        if (ftp != null) {
            try {
                FTPFile[] files = ftp.listFiles(); 
                for(FTPFile file : files){
                    if (file.isFile()) {
                        File localFile = new File(path + "/" + file.getName());
                        OutputStream is = new FileOutputStream(localFile);  
                        ftp.retrieveFile(file.getName(), is);
                        is.close(); 
                    } else if (file.isDirectory()) {
                        ftp.makeDirectory(file.getName());
                        // ftp.changeWorkingDirectory(file.getName());
                        // FTPFile[] files2 = ftp.listFiles();
                        
                    }
                }
                ftp.logout();
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return success;
    }
    
    public static void main(String[] args) {
        boolean flag = DownFile.downFile("Hello" , "C:/Test");
        System.out.println("-----------------------: " + (flag ? "下载成功！" : "下载失败"));
    }
}
