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
import java.util.List;

import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
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

    public static void writeToFile(final File file, final String data) throws IOException {
        writeString(file.toPath(), data, CREATE_NEW, TRUNCATE_EXISTING);
    }

    public static File newFileWithDirectory(final String rootDir, final String subDirectory, final String filename) {
        return new File(newDirectory(rootDir, subDirectory), filename);
    }

    public static File newDirectory(final String parent, final String subdir) {
        final File file = new File(parent, subdir);
        file.mkdirs();
        return file;
    }

}
