package com.pong;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectOutputStream;

public class Keys extends KeyAdapter {
    private ObjectOutputStream outputStream;
    private Paddle paddle1;
    private Paddle paddle2;

    public Keys(Paddle paddle1, Paddle paddle2) {
        this.paddle1 = paddle1;
        this.paddle2 = paddle2;
    }
    public void keyPressed(KeyEvent e) {
        paddle1.keyPressed(e);
        paddle2.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        paddle1.keyReleased(e);
        paddle2.keyReleased(e);
    }
}


