package org.java1.chatRoom.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Asus on 07.11.2016.
 */
public class Test {

    public static void main(String[] args) throws Throwable {

        //Резервируем сокет за портом 9888
        // Сокет будет в спящем режиме, пока к нему никто не присоединится
        ServerSocket serverSocket = new ServerSocket(9888);
        while (true){
            // Получаем сокет, который к нам попытался подключиться
            Socket client = serverSocket.accept();
            System.out.println("Присоединились к нам");
            // Получаем сообщение от клиента
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write("Привет, юзер");
            writer.flush();
            writer.close();
        }
    }

    /**
     * условный банк
     */
    private volatile static int MONEY = 100;

    private synchronized static int getMONEY(int needed) {
        try {
            if (MONEY >= needed) {
                // Делаем тайм-аут
                TimeUnit.MICROSECONDS.sleep(100);
                MONEY -= needed;
                return needed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("getMONEY(20) = " + getMONEY(20));
        }
    }

    private static void test1() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
            thread.join();
        }
        Thread.currentThread();
        System.out.println("total.Money = " + MONEY);
    }

    private static void test0() {
        try {
            // Путь, по которому обращаемся к интернет-странице
            String path = "https://www.yandex.ru";
            URL url = new URL(path);
            // Открываем сетевое соединение
            URLConnection urlConnection = url.openConnection();
            // Превращаем наше соединение в http-соединение, т.к. работаем с протоколом http
            HttpURLConnection http = (HttpURLConnection) urlConnection;
            // Чтобы общение между клиентом и сервером произошло на уровне передачи данных
            int responseCode = http.getResponseCode();
            // Должны получить код от сервера, скорее всего, 200 - т.е. данные можем получить от удаленного сервера
            System.out.println("code = " + responseCode);
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String res = null;
                while ((res = reader.readLine()) != null) {
                    System.out.println(res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
