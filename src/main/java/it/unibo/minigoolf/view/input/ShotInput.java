package it.unibo.minigoolf.view.input;

/**
 * An input interface that accepts a new shot only when enabled.
 *
 * @author fede
 */
@FunctionalInterface
public interface ShotInput {

    /**
     * Enables or disables this input.
     *
     * @param enable true to accept input, false to ignore it
     */
    void setEnable(boolean enable);
}