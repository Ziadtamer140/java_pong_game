package com.pong;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class Paddle extends Rectangle implements Serializable {
    private int id;
    private int yVelocity;
    private int speed = 10;

    Paddle(int x, int y, int width, int height, int id, boolean isClient) {
        super(x, y, width, height);
        this.id = id;
    }

    public void handleKeyPress(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speed);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speed);
                }
                break;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                }
                break;
        }
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        y += yVelocity;
    }

    public void draw(Graphics g) {
        if (id == 1) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        handleKeyPress(e);
    }

    public void keyReleased(KeyEvent e) {
        handleKeyRelease(e);
    }
}
