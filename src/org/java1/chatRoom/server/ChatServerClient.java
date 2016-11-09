package org.java1.chatRoom.server;

import java.io.*;
import java.net.Socket;

// Оправка сообщения всем необходимым пользователям
public class ChatServerClient implements Runnable {
    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private ChatServer server;

    public ChatServerClient(final Socket socket, final ChatServer server) throws IOException {
        this.socket = socket;
        this.socketReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.socketWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.server = server;
    }
    @Override
    public void run() {
        try{
            while (true){
                String message = this.socketReader.readLine();
                //System.out.println("from server = " + message);
                this.server.sendMessageAll(message);
            }

        } catch (Throwable t){
            System.err.println("Server ERROR = " + t.getMessage());
        }
    }

    public void sendMessage(final String message){
        try{
            //System.out.println("for client = " + message);
            this.socketWriter.write(message + "\n");
            this.socketWriter.flush();
        } catch (Throwable t){
            System.err.println("Server ERROR = " + t.getMessage());
        }
    }
}
