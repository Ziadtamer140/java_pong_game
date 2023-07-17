package com.pong;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT_NUMBER = 8888;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started. Waiting for clients...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            PongPanel pongPanel = new PongPanel();

            // Create a separate thread to send and receive game updates
            Thread gameUpdateThread = new Thread(() -> {
                try {
                    while (true) {
                        // Get the current game state from the game panel
                        GameUpdate gameUpdate = new GameUpdate(
                                pongPanel.getPaddle1(),
                                pongPanel.getPaddle2(),
                                pongPanel.getBall(),
                                pongPanel.getScore()
                        );

                        // Send the game update to the client
                        outputStream.writeObject(gameUpdate);
                        System.out.println(gameUpdate);

                        // Receive player movement from the client
                       PlayerMovement playerMovement = (PlayerMovement) inputStream.readObject();
                        System.out.println(playerMovement);

                        // Update the game panel with the received movement
                        pongPanel.updatePlayerMovement(playerMovement);

                        // Repaint the panel to reflect the changes
                        SwingUtilities.invokeLater(() -> {
                            pongPanel.repaint();
                        });
                    }
                } catch (IOException e) {
                    System.err.println("Client disconnected.");
                } catch (ClassNotFoundException e) {
                    System.err.println("Invalid data received from the client.");
                }
            });

            // Start the game update thread
            gameUpdateThread.start();
            // sync the game update thread with the main thread
            gameUpdateThread.join();


            JFrame frame = new JFrame("Server - Pong");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setBackground(Color.black);
            frame.add(pongPanel);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
