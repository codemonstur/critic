package critic.util;

import com.google.gson.Gson;
import org.yaml.snakeyaml.Yaml;
import xmlparser.XmlParser;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum IO {;

    public static final Gson GSON = new Gson();
    public static final XmlParser XML_PARSER = new XmlParser();
    public static final Yaml YAML = new Yaml();

    public static void writeToFile(final File file, final String data) throws IOException {
        writeString( file.toPath(), data, CREATE_NEW, TRUNCATE_EXISTING);
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
