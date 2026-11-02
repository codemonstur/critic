package critic.model;

import critic.util.IO;
import java.util.function.Function;

import static critic.util.IO.*;

public enum OutputFormat {
    json(GSON::toJson),
    xml(IO::toXml),
    yaml(YAML::dump);

    private final Function<Object, String> serializer;
    OutputFormat(final Function<Object, String> serializer) {
        this.serializer = serializer;
    }

    public String toString(final Object o) {
        return serializer.apply(o);
    }
}
