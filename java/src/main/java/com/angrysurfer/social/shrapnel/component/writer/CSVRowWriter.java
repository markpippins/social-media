package com.angrysurfer.social.shrapnel.component.writer;

import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.component.filter.DataFilterList;
import com.angrysurfer.social.shrapnel.component.property.PropertyAccessor;
import com.angrysurfer.social.shrapnel.component.property.PropertyUtilsPropertyAccessor;
import com.angrysurfer.social.shrapnel.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class CSVRowWriter extends AbstractRowWriter implements RowWriter {

    private static final String SPACE = " ";

    private boolean spaceAfterDelim = true;

    private String delimiter = ",";

    private PropertyAccessor propertyAccessor = new PropertyUtilsPropertyAccessor();

    private List<ColumnSpec> columns;

    private DataFilterList filters = new DataFilterList.DataFilterListImpl();

    public CSVRowWriter(List<ColumnSpec> columns) {
        super(columns);
    }

    @Override
    public void writeValues(Map<String, Object> outputConfig, Collection<Object> items) {
        String filename = outputConfig.get(FileUtil.FILENAME).toString();
        writeValues(items, filename);
    }

    public void writeValues(Collection<Object> items, String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            items.stream().filter(item -> filters.allow(item, this, this.getPropertyAccessor())).forEach(item -> {
                StringBuffer line = new StringBuffer();
                getColumns().forEach(col -> {
                    line.append(getValue(item, col));
                    if (getColumns().indexOf(col) < getColumns().size() - 1)
                        line.append(spaceAfterDelim ? getDelimiter() + SPACE : getDelimiter());
                });
                try {
                    fileWriter.write(line.toString().concat("\n"));
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            });
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
