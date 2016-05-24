package com.baidu.ftpfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.baidu.ftpfile.utils.FtpConnectUtil;

/**
 * FTP 上传
 * 
 * @author v_tangyuanliang
 *
 */
public class UploadFile {
    /**
     * FTP 上传文件或文件夹(如果此文件或文件夹已经存在，则会更新覆盖)
     * 
     * @param file 上传的文件对象
     * @return
     */
    public static boolean uploadFile (File file) {
        boolean success = false;
        FTPClient ftp = FtpConnectUtil.ftpConnect("127.0.0.1", 21, "TYL", "TYL");
        if (ftp != null) {
            try {
                if (file.isDirectory()) {
                    ftp.makeDirectory(file.getName());
                    ftp.changeWorkingDirectory(file.getName());
                    // ftp.cwd(file.getName()); == ftp.changeWorkingDirectory(file.getName());
                    File[] files = file.listFiles();
                    for (File dirFile : files) {
                        ftp.storeFile(dirFile.getName(), new FileInputStream(dirFile));
                    }
                    success = true;
                } else if (file.isFile()) {
                    ftp.storeFile(file.getName(), new FileInputStream(file));   
                    success = true;
                } else {
                    System.out.println("要上传的文件或文件夹不存在！！！");
                }
                ftp.logout();
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
        boolean flag = UploadFile.uploadFile(new File("C:/Test"));
        System.out.println("-----------------------: " + (flag ? "上传成功！" : "上传失败"));
    }
}
