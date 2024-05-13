package ClientServer;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerGUI {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;

    public ServerGUI() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            // Set up the GUI
            frame = new JFrame("Chat Server");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            Container c = frame.getContentPane();
            c.setLayout(new BorderLayout());

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            c.add(new JScrollPane(chatArea), BorderLayout.CENTER);

            inputField = new JTextField();
            inputField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sendMessage(inputField.getText());
                    inputField.setText("");
                }
            });
            c.add(inputField, BorderLayout.SOUTH);

            frame.setVisible(true);

            // Start accepting client connections
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = in.readLine()) != null) {
                    chatArea.append("Client: " + message + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        chatArea.append("Server: " + message + "\n");
        out.println(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}
