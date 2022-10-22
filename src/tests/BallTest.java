package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Domain.Ball.Ball;

class BallTest {

    private final Ball ball = new Ball();

    @Test
    @DisplayName("Move 10 Times X")
    void move10TimesX() {
        int X = ball.getXLocation() + 40;

        for (int i = 0; i < 10; i++) {
            ball.move(500, 180,100);
        }

        assertEquals(X, ball.getXLocation());
    }

    @Test
    @DisplayName("Move 10 Times Y")
    void move10TimesY() {
        int Y = ball.getYLocation() + 40;

        for (int i = 0; i < 10; i++) {
            ball.move(500, 180,100);
        }

        assertEquals(Y, ball.getYLocation());
    }

    @Test
    @DisplayName("Right Wall Reflection")
    void rightWallReflection() {
        ball.setXLocation(1800);

        for (int i = 0; i < 10; i++) {
            ball.move(500, 180,100);
        }

        assertEquals(1768, ball.getXLocation());
    }

    @Test
    @DisplayName("Left Wall Reflection")
    void leftWallReflection() {
        ball.setXLocation(15);
        ball.setxVelocity(-4);

        for (int i = 0; i < 10; i++) {
            ball.move(500, 180,100);
        }

        assertEquals(39, ball.getXLocation());
    }

    @Test
    @DisplayName("Upper Wall Reflection")
    void upperWallReflection() {
        ball.setXLocation(500);
        ball.setyVelocity(-4);

        for (int i = 0; i < 30; i++) {
            ball.move(500, 180,100);
        }

        assertEquals(36, ball.getYLocation());
    }
}