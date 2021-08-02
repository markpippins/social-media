package com.angrysurfer.social.exports;

import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.TabularExport;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return Stream.of(new File(".").listFiles()).collect(Collectors.toList());
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
