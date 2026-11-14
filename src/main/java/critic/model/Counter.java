package critic.model;

/**
 * The {@code Counter} class.
 */
public final class Counter {
    private long count = 0;

    /**
     * Performs the {@code increment} operation.
     */
    public void increment() {
        this.count++;
    }
    /**
     * Returns the result of {@code get}.
     *
     * @return the result
     */
    public long get() {
        return count;
    }

    /**
     * Creates a new instance.
     */
    public Counter() {
    }
}
