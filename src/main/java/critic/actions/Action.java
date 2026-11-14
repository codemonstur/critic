package critic.actions;

import critic.model.CliArguments;

/**
 * The {@code Action} enum.
 */
public enum Action {
    /**
     * The {@code all} constant.
     */
    all(args -> {
        Metrics.execute(args);
        SpellingGrammar.execute(args);
    }),
    /**
     * The {@code check} constant.
     */
    check(args -> {
        Metrics.execute(args);
        SpellingGrammar.execute(args);
    }),
    /**
     * The {@code metrics} constant.
     */
    metrics(Metrics::execute),
    /**
     * The {@code spelling} constant.
     */
    spelling(SpellingGrammar::execute);

    interface CheckedConsumer<T> {
        void accept(T t) throws Exception;
    }

    private final CheckedConsumer<CliArguments> executor;
    Action(final CheckedConsumer<CliArguments> executor) {
        this.executor = executor;
    }
    /**
     * Performs the {@code execute} operation.
     *
     * @param arguments the arguments
     * @throws Exception if the operation fails
     */
    public void execute(final CliArguments arguments) throws Exception {
        executor.accept(arguments);
    }

}
