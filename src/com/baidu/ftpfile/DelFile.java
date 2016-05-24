package com.baidu.ftpfile;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.baidu.ftpfile.utils.FtpConnectUtil;

/**
 * FTP 删除
 * 
 * @author v_tangyuanliang
 *
 */
public class DelFile {
    /**
     * FTP 删除文件或文件夹
     * 
     * @param fileName 删除的文件名称(带后缀名)或者文件夹名称
     * @return
     * 
     * ftp.mlistDir()/ftp.listFiles()  获取FTP根目录第一个文件夹下的所有文件
     * 
     * ftp.changeWorkingDirectory()/tp.cwd() 只能切换FTP根目录下的子文件夹下
     * ftp.changeWorkingDirectory("Test")/tp.cwd("Test") 进入FTP根目录下的Test文件夹下
     * ftp.changeWorkingDirectory("Test/HEHE")/tp.cwd("Test/HEHE") 进入FTP根目录下Test文件下的HEHE文件夹下
     * 
     * ftp.changeToParentDirectory()  最多只能返回到FTP根目录，
     * 也就是说可以在进入根目录子文件夹下可以返回上一个子目录
     */
    public static boolean delFile (String fileName) {
        boolean success = false;
        FTPClient ftp = FtpConnectUtil.ftpConnect("127.0.0.1", 21, "TYL", "TYL");
        try {
            if (ftp != null) {
                ftp.changeWorkingDirectory("Test/HEHE");
                FTPFile file = ftp.mlistFile(fileName);
                // 判断是否存在  && 文件
                if (file != null && file.isFile()) {
                    ftp.deleteFile(fileName);
                    success = true;
                } else if (file != null && file.isDirectory()) {
                    // 进入该文件夹
                    ftp.cwd(file.getName());
                    // 获取该文件夹下所有的文件，并删除
                    FTPFile[] files = ftp.listFiles();
                    for (FTPFile ftpFile : files) {
                        ftp.deleteFile(ftpFile.getName());
                    }
                    // 返回上一层目录
                    ftp.changeToParentDirectory();
                    // 删除该文件夹
                    ftp.removeDirectory(fileName);
                    success = true;
                } else {
                    System.out.println("要删除的文件或文件夹不存在！！！");
                }
            }
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
        return success;
    }
    
    public static void main(String[] args) {
        boolean success =  DelFile.delFile("TT.txt");
        System.out.println("-----------------------: " + (success ? "删除成功！" : "删除失败！"));
    }
}
