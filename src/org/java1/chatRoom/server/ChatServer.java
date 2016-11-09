package org.java1.chatRoom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;

// Используем шаблон проектирования Наблюдатель
public class ChatServer implements Runnable {
    private ServerSocket serverSocket;
    // Объявляем final - поэтому виртуальная машина будет работать с этим списком быстрее
    private final Collection<ChatServerClient> clients = new ArrayList<ChatServerClient>();

    // Передавать параметр final в метод - хорошая практика, т.к. параметры не должны меняться
    public ChatServer(final int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Проблема с портом $s", port));
        }
    }
    @Override
    public void run(){
        while (true){
            try {
                ChatServerClient client = new ChatServerClient(this.serverSocket.accept(), this);
                // Новый пользователь подсоединился к чат-серверу - получаем новый сокет
                addClient(client);
                // Создаем новую нить
                new Thread(client).start();
            } catch (Throwable t) {
                System.err.println("Server ERROR while accept client = " + t.getMessage());
            }
        }
    }
    // Регистрация нового пользователя в системе; synchronized замедляет работу
    // Добавляем наблюдаемых - в рамках шаблона Наблюдатель
    public synchronized void addClient(final ChatServerClient client){
        this.clients.add(client);
        //System.out.println("new client");
    }

    // Передать всем сообщение, которое будет кто-нибдуь писать
    public synchronized void sendMessageAll(final String message){
        //System.out.println("client send = " + message);
        for (ChatServerClient client : this.clients) {
            client.sendMessage(message);
        }
    }
}
