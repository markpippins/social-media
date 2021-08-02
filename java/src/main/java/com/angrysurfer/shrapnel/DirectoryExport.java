package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.component.ColumnSpec;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DirectoryExport extends TabularExport {

    static List<ColumnSpec> COLUMNS = ColumnSpec.createColumnSpecs(Arrays.asList("absolutePath"));

    static String NAME = "directory-export";

    public DirectoryExport() {
        super(NAME, COLUMNS);
    }

    @Component
    public static class DirectoryExportFactory implements ExportFactory {

        @Override
        public Collection<Object> getData() {
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
