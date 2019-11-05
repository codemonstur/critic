package critic.actions;

import critic.model.CliArguments;

public enum Action {
    all(args -> {
        Metrics.execute(args);
        SpellingGrammar.execute(args);
    }),
    metrics(Metrics::execute),
    spelling(SpellingGrammar::execute);

    interface CheckedConsumer<T> {
        void accept(T t) throws Exception;
    }

    private final CheckedConsumer<CliArguments> executor;
    Action(final CheckedConsumer<CliArguments> executor) {
        this.executor = executor;
    }
    public void execute(final CliArguments arguments) throws Exception {
        executor.accept(arguments);
    }

}
