package com.pong;

import java.awt.*;
import java.io.Serializable;

public class Score implements Serializable {
    public int player1;
    public int player2;
   final private int gameWidth;
   final private int gameHeight;

    public Score(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));

        // Draw player scores on the screen
        g.drawString(String.valueOf(player1), (gameWidth / 2) - 85, 50);
        g.drawString(String.valueOf(player2), (gameWidth / 2) + 20, 50);

        // Draw a line in the middle of the screen
        g.drawLine(gameWidth / 2, 0, gameWidth / 2, gameHeight);

        // if a player scores 5 points, he wins and the game ends
        if (player1 == 5) g.drawString("Player 1 wins!", (gameWidth / 2) - 200, gameHeight / 2);
        else if (player2 == 5) {
            g.drawString("Player 2 wins!", (gameWidth / 2) - 200, gameHeight / 2);
        }
    }

    public int getPaddle1Score() {
        return player1;
    }

    public int getPaddle2Score() {
        return player2;
    }
}