package com.angrysurfer.shrapnel.util;

import com.angrysurfer.shrapnel.Export;
import com.angrysurfer.shrapnel.ExportFactory;
import com.angrysurfer.social.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class FileUtil {

    public static String FILENAME = "filename";

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

    public static String makeFileName(UserDTO user, ExportFactory factory) {
        return user.getAlias() + " - " + factory.getExportName() + " - " + LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond();
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

    public static String getTabLabel(Export export) {
        return String.format("%s - %s - %s - %s", export.getName(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }
}
