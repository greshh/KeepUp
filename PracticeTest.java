import java.awt.event.KeyEvent;

public class PracticeTest extends GameEngine {
    public static void main(String[] args) {
        createGame(new PracticeTest());
    }

    /* --- GAME --- */

    boolean gameOver;
    int counter;

    boolean left, right, spacebar;

    public void init() {
        gameOver = false;
        left = false;
        right = false;
        spacebar = false;
        counter = 0;
        initBall();
        initPaddle();
    }

    public void update(double dt) {
        if (!gameOver) {
            updateBall(dt);
            updatePaddle(dt);
            for (int i = 0; i <= 120; i++) {
                if (distance(ballPositionX + 10, ballPositionY + 20, paddlePositionX + i, paddlePositionY + 10) <= 10) {
                    if (ballVelocityY < 0) {
                        ballVelocityY *= -1;
                    }
                    ballAcceleration += 0.1;
                    counter++;
                    break;
                }
            }
        }
    }

    public void paintComponent() {
        changeBackgroundColor(white);
        clearBackground(width(), height());
        if (!gameOver) {
            drawBall();
            drawPaddle();
            changeColor(black);
            if (counter < 10) {
                drawBoldText(width()-50, 50, Integer.toString(counter));
            } else if ((counter >= 10) && (counter < 100)) {
                drawBoldText(width()-72, 50, Integer.toString(counter));
            } else if (counter >= 100) {
                drawBoldText(width()-94, 50, Integer.toString(counter));
            }
        } else {
            changeColor(red);
            drawBoldText(125, 200, "GAME OVER");
            drawBoldText(10, 255, "Press spacebar to restart");
            if (spacebar) {
                init();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacebar = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacebar = false;
        }
    }

    /* --- BALL --- */ 

    double ballPositionX, ballPositionY;
    double ballVelocityX, ballVelocityY;
    double ballAcceleration;
    final double G = 9.81; // gravity constant.

    public void initBall() {
        ballPositionX = (double)(width()/2);
        ballPositionY = (double)(height()/4);
        ballVelocityX = 0;
        ballVelocityY = 0;
        if (Math.random() <= 0.5) {
            ballVelocityX = -100;
        } else {
            ballVelocityX = 100;
        }
        ballAcceleration = 1;
    }

    public void drawBall() {
        changeColor(black);
        saveCurrentTransform();
        drawSolidCircle(ballPositionX, ballPositionY, 10);
    }

    public void updateBall(double dt) {
        ballPositionX += ballVelocityX * ballAcceleration * dt;
        ballPositionY -= ballVelocityY * ballAcceleration * dt;
        ballVelocityY -= G;

        if (ballPositionX >= width()-10) {
            if (ballVelocityX > 0) {
                ballVelocityX *= -1;
            }
        } else if (ballPositionX <= 10) {
            if (ballVelocityX < 0) {
                ballVelocityX *= -1;
            }
        }
        if (ballPositionY <= 10) {
            if (ballVelocityY > 0) {
                ballVelocityY *= -1;
            }
        }

        if (ballPositionY > height()) {
            gameOver = true;
            return;
        }
    }

    /* --- PADDLE --- */

    double paddlePositionX, paddlePositionY;
    double paddleVelocity; // only horizontal velocity.

    public void initPaddle() {
        paddlePositionX = (double)((width()/2)-60);
        paddlePositionY = height()-60;
        paddleVelocity = 0;
    }

    public void drawPaddle() {
        changeColor(black);
        saveCurrentTransform();
        drawSolidRectangle(paddlePositionX, paddlePositionY, 120, 20);
    }

    public void updatePaddle(double dt) {
        //paddlePositionX+= paddleVelocity * dt;
        if (left) {
            //paddleVelocity = -10;
            paddlePositionX-= 200 * dt;
        }
        if (right) {
            //paddleVelocity = 10;
            paddlePositionX+= 200 * dt;
        }
        if (!left && !right) {
            paddleVelocity = 0;
        }
    }
}