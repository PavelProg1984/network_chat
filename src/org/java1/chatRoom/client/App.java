package org.java1.chatRoom.client;

import org.java1.chatRoom.common.MiscUtil;
/* import java.util.Scanner;*/

public class App {
    public static void main(String[] args) {
        ChatClient client = new ChatClient(MiscUtil.SERVER_PATH, MiscUtil.SERVER_PORT);
        client.setPrintStream(System.out);
        new Thread(client).start();
        UI ui = new UI("Chat client", 600, 400, client);
        ui.showUi();
        /*
        System.out.println("Chat client, 'exit' for exit");
        Scanner scanner = new Scanner(System.in);
        String fromConsole = null;
        while (true){
            fromConsole = scanner.nextLine();
            if (fromConsole.equalsIgnoreCase("exit")){
                System.exit(0);
            }
            // Пишем сообщение серверу
            client.sendMessage(fromConsole);
        }*/
    }
}
