package com.baidu.ftpfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.apache.commons.net.ftp.FTPClient;

import com.baidu.ftpfile.utils.FtpConnectUtil;

/**
 * FTP 读取指定的文件
 * 
 * @author v_tangyuanliang
 *
 */
public class ReadFile {
    public static String readFile(String fileName) {
        FTPClient ftp = FtpConnectUtil.ftpConnect("127.0.0.1", 21, "TYL", "TYL");
        InputStream ins = null;
        StringBuilder builder = null;
        try {
            // 从服务器上读取指定的文件
            ins = ftp.retrieveFileStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "GBK"));
            String line;
            builder = new StringBuilder(150);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                builder.append(line);
            }
            reader.close();
            if (ins != null) {
                ins.close();
            }
            // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
            ftp.getReply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    
    public static void main(String[] args) {
        String reader = ReadFile.readFile("Hello.txt");
        System.out.println(reader);
    }
}
