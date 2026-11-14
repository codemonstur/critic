package critic.model;

import critic.util.IO;
import java.util.function.Function;

import static critic.util.IO.*;

/**
 * The {@code OutputFormat} enum.
 */
public enum OutputFormat {
    /**
     * The {@code json} constant.
     */
    json(GSON::toJson),
    /**
     * The {@code xml} constant.
     */
    xml(IO::toXml),
    /**
     * The {@code yaml} constant.
     */
    yaml(YAML::dump);

    private final Function<Object, String> serializer;
    OutputFormat(final Function<Object, String> serializer) {
        this.serializer = serializer;
    }

    /**
     * Returns a string representation of this object.
     *
     * @param o the o
     * @return the result
     */
    public String toString(final Object o) {
        return serializer.apply(o);
    }
}
