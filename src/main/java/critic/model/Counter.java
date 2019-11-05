package critic.model;

public final class Counter {
    private long count = 0;

    public void increment() {
        this.count++;
    }
    public long get() {
        return count;
    }
}
