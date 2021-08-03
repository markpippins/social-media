package com.angrysurfer.social.exports;

import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.TabularExport;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DirectoryExport extends TabularExport {

    static List<ColumnSpec> COLUMNS = ColumnSpec.createColumnSpecs(Arrays.asList("absolutePath", "name", "parent", "length", "canonicalPath"));

    static String NAME = "directory-export";

    public DirectoryExport() {
        super(NAME, COLUMNS);
    }

    @Component
    public static class DirectoryExportFactory implements ExportFactory {

        @Override
        public Collection<Object> getData() {

//            File file = new File(".");
//            String fontProperties = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1) + FONTS_FOLDER;
//
//            List<Object> data = Stream.of(new File(fontProperties).listFiles()).collect(Collectors.toList());
//            return data;
            return Collections.EMPTY_LIST;
        }

        @Override
        public String getExportName() {
            return NAME;
        }

        @Override
        public Class getExportClass() {
            return DirectoryExport.class;
        }
    }
}
