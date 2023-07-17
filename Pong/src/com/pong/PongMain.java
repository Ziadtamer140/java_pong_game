package com.pong;

public class PongMain {

    public static void main(String[] args) {
        boolean isServer = true; // Change to false if running as a client
        if (isServer) {
            Server.main(args);
        } else {
            Client.main(args);
        }
    }
}

