package com.angrysurfer.social.shrapnel.util;

import com.angrysurfer.social.shrapnel.Config;
import com.angrysurfer.social.shrapnel.component.IExport;
import com.angrysurfer.social.shrapnel.component.writer.CsvDataWriter;
import com.angrysurfer.social.shrapnel.services.factory.IExportFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class FileUtil {

    public static final String FILENAME = "filename";

    public static boolean ensureSafety(String filename) throws IOException {
        boolean safe = true;

        File file = new File(filename);
        if (!file.getParentFile().exists())
            safe = file.getParentFile().mkdirs();

        if (safe && file.exists())
            try {
                safe = file.delete();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                safe = false;
            }

        if (!safe)
            throw new IOException(String.format("%s is in use by another process of there is a permissions error.", filename));

        return safe;
    }

    public static ByteArrayResource getByteArrayResource(String filename) {
        ByteArrayResource result = null;

        try {
            File file = new File(filename);
            Path path = Paths.get(file.getAbsolutePath());
            result = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    public static String makeFileName(IExportFactory factory) {
        return String.join("-", factory.getExportName(),
//                Objects.nonNull(starter) ? starter.trim().toLowerCase() : "",
                Integer.toString(LocalDateTime.now().getHour()),
                Integer.toString(LocalDateTime.now().getMinute()),
                Integer.toString(LocalDateTime.now().getSecond()));
    }

    public static void removeFileAfter(String filename, long waitSeconds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * waitSeconds);

                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    File file = new File(filename);
                    file.delete();
                }
            }
        }).start();
    }

    public static String getOutputFileName(String filename, String ext) {

        File file = new File(".");
        String path = file.getAbsolutePath();
        String tail = ".".concat(ext);
        String outputFileName = path.substring(0, path.length() - 1) + "exports" + FileSystems.getDefault().getSeparator() + filename;
        if (!outputFileName.endsWith(tail))
            outputFileName = outputFileName + tail;

        return outputFileName;
    }

    public static String getLabel(IExport export) {
        return String.format("%s - %s - %s - %s", export.getName(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    public static Map<Object, Object> getFileProperties(String propertyFile) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertyFile)) {
            properties.load(input);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

    public static String getSQL(String filename) throws IOException {
        return Files.readString(Path.of(Config.getInstance().getProperty(Config.SQL_FOLDER) + filename + ".sql"));
    }

    public static void writeCsvFile(Collection data, IExport export, String filename) {
        CsvDataWriter writer = new CsvDataWriter(export.getFields());
        writer.writeValues(data, filename);

    }
}

