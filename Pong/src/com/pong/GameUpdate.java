package com.pong;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameUpdate implements Serializable {
    private Paddle paddle1;
    int playerId;
    private Paddle paddle2;
    private Ball ball;
    private Score score;
    private int paddle1Y;
    private int paddle2Y;
    private int ballX;
    private int ballY;
    private int player1Score;
    private int player2Score;


    // Constructor, getters, and setters here...
    public GameUpdate(Paddle paddle1, Paddle paddle2, Ball ball, Score score) {
        this.paddle1 = paddle1;
        this.paddle2 = paddle2;
        this.ball = ball;
        this.score = score;
    }

    public GameUpdate(PongPanel pongPanel, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.paddle1 = pongPanel.getPaddle1();
        this.paddle2 = pongPanel.getPaddle2();
        this.ball = pongPanel.getBall();
        this.score = pongPanel.getScore();
    }

    public Paddle getPaddle1() {
        return paddle1;
    }

    public void setPaddle1(Paddle paddle1) {
        this.paddle1 = paddle1;
    }

    public Paddle getPaddle2() {
        return paddle2;
    }

    public void setPaddle2(Paddle paddle2) {
        this.paddle2 = paddle2;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public int getPaddle1Y() {
        return paddle1Y;
    }

    public void setPaddle1Y(int paddle1Y) {
        this.paddle1Y = paddle1Y;
    }

    public int getPaddle2Y() {
        return paddle2Y;
    }

    public void setPaddle2Y(int paddle2Y) {
        this.paddle2Y = paddle2Y;
    }

    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public PlayerMovement getPlayerMovement() {
        if (playerId == 1) {
            return new PlayerMovement(paddle1.y, playerId);
        } else {
            return new PlayerMovement(paddle2.y, playerId);
        }
    }
}