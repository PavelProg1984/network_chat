package org.java1.chatRoom.client;

import org.java1.chatRoom.common.MiscUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Asus on 08.11.2016.
 */
public class Test {
    public static void main(String[] args) throws Throwable {
        // Т.к. локальная разработка, то указываем в качестве хоста localhost
        Socket socket = new Socket(MiscUtil.SERVER_PATH,MiscUtil.SERVER_PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(reader.readLine());
        reader.close();
    }
}
