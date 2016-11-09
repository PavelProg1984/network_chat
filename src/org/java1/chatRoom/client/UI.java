package org.java1.chatRoom.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class UI extends JFrame {
    private ChatClient client;

    public UI(final String title, final int width, final int height, final ChatClient client) throws HeadlessException {
        this.setTitle(title);
        this.setSize(width, height);
        // Передаем null - окошко нарисуется примерно посередине
        this.setLocationRelativeTo(null);
        //Чтобы приложение завершалось при нажатии на крестик, а не только закрывалось окно
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.client = client;

        // final as local variable textArea is accessed from within inner class; needs to be declared final
        final JTextArea textArea = new JTextArea(50, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        // final as local variable textField is accessed from within inner class; needs to be declared final
        final JTextField textField = new JTextField();
        JButton button = new JButton("SEND");

        // Используем компоновщик
        Box box = Box.createVerticalBox();
        box.add(scrollPane);
        box.add(textField);
        box.add(button);

        // Отправка сообщения
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                if (message == null || message.isEmpty())
                    return;
                //Стираем сообщение после нажатия на кнопку
                textField.setText("");
                client.sendMessage(message);
            }
        });
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
            }
        });
        this.client.setPrintStream(printStream);
        this.setContentPane(box);
    }
    public void showUi(){
        this.setVisible(true);
    }

    public ChatClient getClient() {
        return client;
    }

    public void setClient(ChatClient client) {
        this.client = client;
    }
}
