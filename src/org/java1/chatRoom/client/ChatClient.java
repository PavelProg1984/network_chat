package org.java1.chatRoom.client;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable {
    private Socket socket;
    // Формируем 2 потока, с которыми будем постоянно взаимодействовать
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;

    private PrintStream outPrintStream;

    public ChatClient(final String host, final int port) {
        try {
            this.socket = new Socket(host, port);
            this.socketReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.socketWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e){
            throw new RuntimeException(String.format("Проблема с подключением к серверу " +
                    "по адресу = %1$s:%2$s", host, port));
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                // Чтение сообщений от сервера
                String message = this.socketReader.readLine();
                this.outPrintStream.println("from server = " + message);
            }
        } catch (Throwable t){
            System.err.println("Client ERROR = " + t.getMessage());
        }
    }

    public void sendMessage(final String message){
        try {
            this.socketWriter.write(message + "\n");
            this.socketWriter.flush();
            //System.out.println("SEND TO SERVER = " + message);

        } catch (Throwable t){
            System.err.println("Client ERROR = " + t.getMessage());
        }
    }

    public PrintStream getPrintStream() {
        return outPrintStream;
    }

    // Чтобы мы могли поменять путь, куда записывать информацию
    public void setPrintStream(PrintStream outPrintStream) {
        this.outPrintStream = outPrintStream;
    }
}
