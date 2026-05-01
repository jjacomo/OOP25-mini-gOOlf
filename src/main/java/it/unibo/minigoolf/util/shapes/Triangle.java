package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Represents a triangular area in 2D space defined by three vertices.
 *
 * @param vertex1 the first vertex of the triangle
 * @param vertex2 the second vertex of the triangle
 * @param vertex3 the third vertex of the triangle
 */
public record Triangle(Vector2D vertex1, Vector2D vertex2, Vector2D vertex3) implements Shape {

    /**
     * Checks if the given position is contained within this triangle.
     *
     * @param position the position to check
     * @return true if the position is inside the triangle, false otherwise
     */
    @Override
    public boolean contains(final Vector2D position) {
        boolean sign1 = crossSign(vertex1, vertex2, position);
        boolean sign2 = crossSign(vertex2, vertex3, position);
        boolean sign3 = crossSign(vertex3, vertex1, position);

        return sign1 == sign2 && sign2 == sign3;
    }

    /**
     * Helper method to compute the sign of the cross product (b - a) x (p - a).
     *
     * @param a first vertex of the edge
     * @param b second vertex of the edge
     * @param p the point to test
     * @return true if the cross product is >= 0, false otherwise
     */
    private boolean crossSign(final Vector2D a, final Vector2D b, final Vector2D p) {
        final double cross = (b.getX() - a.getX()) * (p.getY() - a.getY())
                           - (b.getY() - a.getY()) * (p.getX() - a.getX());

        return cross >= 0;
    }
}
