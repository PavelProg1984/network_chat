package org.java1.chatRoom.server;

import org.java1.chatRoom.common.MiscUtil;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Запускаем сервер, регистрируем по конкретному порту
        ChatServer server = new ChatServer(MiscUtil.SERVER_PORT);
        new Thread(server).start();
        System.out.println("Server has started, press q to exit");
        Scanner scanner = new Scanner(System.in);
        String fromConsole = null;

        while (true){
            fromConsole = scanner.nextLine();
            if (fromConsole.equalsIgnoreCase("q")){
                // Завершаем приложение
                System.exit(0);
            }
        }
    }
}
