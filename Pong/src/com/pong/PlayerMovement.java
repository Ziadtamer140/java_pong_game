package com.pong;

public class PlayerMovement {
    private int paddle1Y;
    private int paddle2Y;

    public PlayerMovement(int paddle1Y, int paddle2Y) {
        this.paddle1Y = paddle1Y;
        this.paddle2Y = paddle2Y;
    }

    public PlayerMovement(int keyCode) {
        switch (keyCode) {
            case 87:
                this.paddle1Y = -10;
                break;
            case 83:
                this.paddle1Y = 10;
                break;
            case 38:
                this.paddle2Y = -10;
                break;
            case 40:
                this.paddle2Y = 10;
                break;
        }
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

    public int getPlayerId() {
        return paddle1Y;
    }

    public int getPaddleY() {
        return paddle2Y;
    }
}
