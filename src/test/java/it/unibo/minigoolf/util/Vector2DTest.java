package it.unibo.minigoolf.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Vector2DTest {
    private static final double EPSILON = 0.0001;
    private static final double X_1 = 3.0;
    private static final double Y_1 = 4.0;
    private static final double X_2 = 1.0;
    private static final double Y_2 = 2.0;
    private static final double SCALAR = 2.5;

    private static final double EXPECTED_V1_ADD_V2_X = 4.0;
    private static final double EXPECTED_V1_ADD_V2_Y = 6.0;
    private static final double EXPECTED_V1_SUBTRACT_V2_X = 2.0;
    private static final double EXPECTED_V1_SUBTRACT_V2_Y = 2.0;
    private static final double EXPECTED_VECTOR_SCALAR_MULTIPLICATION_X = 5.0;
    private static final double EXPECTED_VECTOR_SCALAR_MULTIPLICATION_Y = -7.5;
    private static final double EXPECTED_V1_DOT_PRODUCT_V2 = 23.0;

    @Test
    void testBasicOperations() {
        final Vector2D v1 = new Vector2D(X_1, Y_1);
        final Vector2D v2 = new Vector2D(X_2, Y_2);

        assertAll("Vector basic arithmetic operations", 
                () -> assertEquals(EXPECTED_V1_ADD_V2_X, v1.add(v2).getX(), EPSILON),
                () -> assertEquals(EXPECTED_V1_ADD_V2_Y, v1.add(v2).getY(), EPSILON),
                () -> assertEquals(EXPECTED_V1_SUBTRACT_V2_X, v1.subtract(v2).getX(), EPSILON),
                () -> assertEquals(EXPECTED_V1_SUBTRACT_V2_Y, v1.subtract(v2).getY(), EPSILON)
        );
    }

    @Test
    void testScalarMultiply() {
        final Vector2D v = new Vector2D(2.0, -3.0);
        final Vector2D result = v.scalarMultiply(SCALAR);

        assertAll("Vector scalar multiplication",
                () -> assertEquals(EXPECTED_VECTOR_SCALAR_MULTIPLICATION_X, result.getX(), EPSILON),
                () -> assertEquals(EXPECTED_VECTOR_SCALAR_MULTIPLICATION_Y, result.getY(), EPSILON)
        );
    }

    @Test
    void testDotProductAndDistance() {
        final Vector2D v1 = new Vector2D(2.0, 3.0);
        final Vector2D v2 = new Vector2D(4.0, 5.0);

        assertAll("Vector dot product and distance calculations",
                () -> assertEquals(EXPECTED_V1_DOT_PRODUCT_V2, v1.dotProduct(v2), EPSILON),
                () -> assertEquals(Math.sqrt(8.0), v1.distance(v2), EPSILON)
        );
    }

    @Test
    void testNormalizeZeroVector() {
        final Vector2D zero = new Vector2D(0.0, 0.0);

        org.junit.jupiter.api.Assertions.assertThrows(
                ArithmeticException.class, 
                zero::normalize, 
                "Normalizing a zero vector should throw ArithmeticException"
        );
    }
}
