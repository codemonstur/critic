package critic.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import xmlparser.XmlParser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;
import static xmlparser.XmlParser.newXmlParser;

public enum IO {;

    public static final Gson GSON = new Gson();
    public static final XmlParser XML_PARSER = new XmlParser();
    public static final Yaml YAML = new Yaml(newLoaderOptions(), newRepresenter(), newDumperOptions());

    private static Constructor newLoaderOptions() {
        final var options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        options.setMaxAliasesForCollections(5);
        options.setAllowRecursiveKeys(false);
        options.setNestingDepthLimit(3);
        return new Constructor(options);
    }
    private static Representer newRepresenter() {
        return new NoNullRepresenter(newDumperOptions());
    }
    private static DumperOptions newDumperOptions() {
        final var options = new DumperOptions();
        options.setIndent(4);
        options.setIndicatorIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(BLOCK);
        return options;
    }
    private static class NoNullRepresenter extends Representer {
        public NoNullRepresenter(final DumperOptions options) {
            super(options);
        }
        protected NodeTuple representJavaBeanProperty(final Object javaBean, final Property property
                , final Object propertyValue, final Tag customTag) {
            if (propertyValue == null) return null;
            if (propertyValue instanceof List) {
                final List list = (List) propertyValue;
                if (list.isEmpty()) return null;
            }

            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        }
    }

    /**
     * Writes the given data to a file, creating or overwriting it.
     *
     * @param file the target file
     * @param data the content to write
     * @throws IOException if writing fails
     */
    public static void writeToFile(final File file, final String data) throws IOException {
        writeString(file.toPath(), data, CREATE, TRUNCATE_EXISTING);
    }

    /**
     * Creates a file handle inside a (newly created) subdirectory.
     *
     * @param rootDir      the root directory
     * @param subDirectory the subdirectory name
     * @param filename     the file name
     * @return the file handle
     */
    public static File newFileWithDirectory(final String rootDir, final String subDirectory, final String filename) {
        return new File(newDirectory(rootDir, subDirectory), filename);
    }

    /**
     * Creates a directory (and parents) if it does not exist.
     *
     * @param parent the parent directory
     * @param subdir the subdirectory name
     * @return the directory file handle
     */
    public static File newDirectory(final String parent, final String subdir) {
        final File file = new File(parent, subdir);
        file.mkdirs();
        return file;
    }

    /**
     * Serializes an object to a simple XML representation.
     * Unlike the third-party XML parser, this handles {@link Map}s and beans
     * without requiring module opens on {@code java.base}.
     *
     * @param object the object to serialize
     * @return the XML string
     */
    public static String toXml(final Object object) {
        return toXml(object, 0);
    }

    private static String toXml(final Object object, final int depth) {
        final String indent = "  ".repeat(depth);
        if (object == null) return indent + "<null/>\n";

        if (object instanceof Map<?, ?> map) {
            final StringBuilder builder = new StringBuilder(indent).append("<report>\n");
            for (final Map.Entry<?, ?> entry : map.entrySet()) {
                builder.append(indent).append("  <entry>\n");
                builder.append(indent).append("    <key>")
                        .append(escapeXml(String.valueOf(entry.getKey())))
                        .append("</key>\n");
                builder.append(indent).append("    <value>\n");
                builder.append(toXml(entry.getValue(), depth + 3));
                builder.append(indent).append("    </value>\n");
                builder.append(indent).append("  </entry>\n");
            }
            builder.append(indent).append("</report>\n");
            return builder.toString();
        }

        if (object instanceof Collection<?> collection) {
            final StringBuilder builder = new StringBuilder(indent).append("<list>\n");
            for (final Object item : collection) {
                builder.append(indent).append("  <item>\n");
                builder.append(toXml(item, depth + 2));
                builder.append(indent).append("  </item>\n");
            }
            builder.append(indent).append("</list>\n");
            return builder.toString();
        }

        final Class<?> clazz = object.getClass();
        if (isSimple(clazz)) {
            return indent + escapeXml(String.valueOf(object)) + "\n";
        }

        final StringBuilder builder = new StringBuilder(indent)
                .append("<").append(simpleName(clazz)).append(">\n");
        for (final Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                continue;
            }
            builder.append(indent).append("  <").append(field.getName()).append(">");
            if (value == null || isSimple(value.getClass())) {
                builder.append(value == null ? "" : escapeXml(String.valueOf(value)));
                builder.append("</").append(field.getName()).append(">\n");
            } else {
                builder.append("\n");
                builder.append(toXml(value, depth + 2));
                builder.append(indent).append("  </").append(field.getName()).append(">\n");
            }
        }
        builder.append(indent).append("</").append(simpleName(clazz)).append(">\n");
        return builder.toString();
    }

    private static boolean isSimple(final Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == String.class
                || Number.class.isAssignableFrom(clazz)
                || clazz == Boolean.class
                || clazz == Character.class;
    }

    private static String simpleName(final Class<?> clazz) {
        final String name = clazz.getSimpleName();
        return name.isEmpty() ? clazz.getName() : name;
    }

    private static String escapeXml(final String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

}
