package com.pong;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final int PORT_NUMBER = 8888;

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", PORT_NUMBER);

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            PongPanel pongPanel = new PongPanel();

            // Create a separate thread to receive updates from the server
            Thread serverListenerThread = new Thread(() -> {
                try {
                    while (true) {
                        // Receive game update from the server
                        GameUpdate gameUpdate = (GameUpdate) inputStream.readObject();

                        // Update the game panel with the received update
                        pongPanel.updateGame(gameUpdate);

                        // Repaint the panel to reflect the changes
                        pongPanel.repaint();
                    }
                } catch (Exception e) {
                    switch (e.getClass().getSimpleName()) {
                        case "Connection reset":
                            System.err.println("Server disconnected.");
                            break;
                        default:
                            e.printStackTrace();
                    }
                }
            });

            // Start the server listener thread
            serverListenerThread.start();

            JFrame frame = new JFrame("Client-Pong");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setBackground(Color.gray);
            frame.add(pongPanel);
            frame.pack();

            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            // Run the game loop on the main thread
            while (true) {
                // Get the current game state from the game panel
                GameUpdate gameUpdate = new GameUpdate(
                        pongPanel.getPaddle1(),
                        pongPanel.getPaddle2(),
                        pongPanel.getBall(),
                        pongPanel.getScore()
                );

                // Send the game update to the server
                outputStream.writeObject(gameUpdate);

                // sync the movement of the player with the game loop
                pongPanel.updatePlayerMovement(gameUpdate.getPlayerMovement());



                // Wait for a short duration to avoid excessive network traffic
                Thread.sleep(100);

                // Repaint the panel to reflect the changes
                pongPanel.repaint();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}