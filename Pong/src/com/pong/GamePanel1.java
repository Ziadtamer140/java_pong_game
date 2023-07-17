//package com.pong;
//
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.io.*;
//import java.net.*;
//import java.util.*;
//import javax.swing.*;
//
//public class GamePanel1 extends JPanel implements Runnable {
//
//    static final int Game_Width = 1000;
//    static final int Game_Height = (int) (Game_Width * (0.5555));
//    static final Dimension Screen_Size = new Dimension(Game_Width, Game_Height);
//    static final int BAll_Diameter = 20;
//    static final int Paddle_Width = 25;
//    static final int Paddle_Height = 100;
//
//    private Thread gameThread;
//    private Image image;
//    private Graphics graphics;
//    private Random random;
//    private Paddle paddle1;
//    private Paddle paddle2;
//    private Ball ball;
//    private Score score;
//    private ServerPlayer serverPlayer;
//
//    // Networking variables
//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private ObjectOutputStream outputStream;
//    private ObjectInputStream inputStream;
//
//    public GamePanel1() {
//        initialize();
//        setupNetworking();
//        startGameThread();
//    }
//
//    private void initialize() {
//        random = new Random();
//        score = new Score(Game_Width, Game_Height);
//
//        paddle1 = new Paddle(0, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 1);
//        paddle2 = new Paddle(Game_Width - Paddle_Width, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 2);
//
//        ball = new Ball((Game_Width / 2) - (BAll_Diameter / 2), random.nextInt(Game_Height - BAll_Diameter), BAll_Diameter, BAll_Diameter);
//
//        setFocusable(true);
//
//        addKeyListener(new KeyAdapter() {
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                paddle1.handleKeyPress(e);
//                paddle2.handleKeyPress(e);
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                paddle1.handleKeyRelease(e);
//                paddle2.handleKeyRelease(e);
//            }
//        });
//        setPreferredSize(Screen_Size);
//    }
//
//    private void setupNetworking() {
//        try {
//            Socket socket = new Socket("localhost", 7777); // Connect to the server running on localhost:7777
//            System.out.println("Connected to server!");
//            outputStream = new ObjectOutputStream(socket.getOutputStream());
//            inputStream = new ObjectInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startGameThread() {
//        gameThread = new Thread(this);
//        gameThread.start();
//    }
//
//    public void paint(Graphics g) {
//        image = createImage(getWidth(), getHeight());
//        graphics = image.getGraphics();
//        draw(graphics);
//        g.drawImage(image, 0, 0, this);
//    }
//
//    private void draw(Graphics g) {
//        paddle1.draw(g);
//        paddle2.draw(g);
//        ball.draw(g);
//        score.draw(g);
//        Toolkit.getDefaultToolkit().sync();
//    }
//
//    public void move() {
//        paddle1.move();
//        paddle2.move();
//        ball.move();
//    }
//
//    public void checkCollision() {
//        // Bounce ball off top & bottom window edges
//        if (ball.y <= 0) {
//            ball.setYDirection(-ball.yVelocity);
//        }
//        if (ball.y >= Game_Height - BAll_Diameter) {
//            ball.setYDirection(-ball.yVelocity);
//        }
//
//        // Bounce ball off paddles
//        if (ball.intersects(paddle1)) {
//            ball.xVelocity = Math.abs(ball.xVelocity);
//            ball.xVelocity++; // optional for more difficulty
//
//            if (ball.yVelocity > 0) {
//                ball.yVelocity++; // optional for more difficulty
//
//            } else {
//                ball.yVelocity--;
//            }
//            ball.setXDirection(ball.xVelocity);
//            ball.setYDirection(ball.yVelocity);
//        }
//
//        if (ball.intersects(paddle2)) {
//            ball.xVelocity = Math.abs(ball.xVelocity);
//            ball.xVelocity++; // optional for more difficulty
//            if (ball.yVelocity > 0) {
//                ball.yVelocity++; // optional for more difficulty
//            } else {
//                ball.yVelocity--;
//            }
//
//            ball.setXDirection(-ball.xVelocity);
//            ball.setYDirection(ball.yVelocity);
//        }
//
//        // Stop paddles at window edges
//        if (paddle1.y <= 0) {
//            paddle1.y = 0;
//        }
//        if (paddle1.y >= (Game_Height - Paddle_Height)) {
//            paddle1.y = Game_Height - Paddle_Height;
//        }
//        if (paddle2.y <= 0) {
//            paddle2.y = 0;
//        }
//        if (paddle2.y >= (Game_Height - Paddle_Height)) {
//            paddle2.y = Game_Height - Paddle_Height;
//        }
//
//        // Give a player 1 point and create new paddles & ball
//        if (ball.x <= 0) {
//            score.player2++;
//            resetGame();
//        }
//        if (ball.x >= Game_Width - BAll_Diameter) {
//            score.player1++;
//            resetGame();
//        }
//    }
//
//    private void resetGame() {
//        newPaddles();
//        newBall();
//        sendPaddlePositions(); // Send new paddle positions to the client
//    }
//
//    private void newBall() {
//        ball = new Ball((Game_Width / 2) - (BAll_Diameter / 2), random.nextInt(Game_Height - BAll_Diameter), BAll_Diameter, BAll_Diameter);
//    }
//
//    private void newPaddles() {
//        paddle1 = new Paddle(0, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 1);
//        paddle2 = new Paddle(Game_Width - Paddle_Width, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 2);
//    }
//
//    public void run() {
//        // Game loop
//        this.requestFocus();
//        long lastTime = System.nanoTime();
//        double amountOfTicks = 60.0;
//        double ns = 1000000000 / amountOfTicks;
//        double delta = 0;
//
//        while (true) {
//            long now = System.nanoTime();
//            delta += (now - lastTime) / ns;
//            lastTime = now;
//
//            if (delta >= 1) {
//                move();
//                checkCollision();
//                repaint();
//                delta--;
//            }
//        }
//    }
//
//    public class AL extends KeyAdapter {
//
//        public void keyPressed(KeyEvent e) {
//
//            paddle1.handleKeyPress(e);
//
//            paddle2.handleKeyPress(e);
//
//        }
//
//        public void keyReleased(KeyEvent e) {
//
//            paddle1.handleKeyPress(e);
//
//            paddle2.handleKeyPress(e);
//
//        }
//
//    }
//
//    private void sendPaddlePositions() {
//        try {
//            int paddle1Y = (int) inputStream.readObject(); // Receive paddle1's y-position from the server
//            int paddle2Y = (int) inputStream.readObject(); // Receive paddle2's y-position from the server
//
//            // Update the paddle positions received from the server
//            paddle1.setYDirection(paddle1Y);
//            paddle2.setYDirection(paddle2Y);
//
//            // Perform any further processing or update the game logic using the received paddle positions.
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static   void main(String[] args) {
//        GamePanel1 panel = new GamePanel1();
//
//
//    }
//}