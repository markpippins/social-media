package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.field.FieldSpec;
import com.angrysurfer.social.shrapnel.component.field.FieldSpecs;
import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.component.writer.ExcelDataWriter;
import com.angrysurfer.social.shrapnel.component.writer.PdfDataWriter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public abstract class AbstractExport implements IExport {

    private ExcelDataWriter excelRowWriter;

    private PdfDataWriter pdfRowWriter;

    private FieldSpecs fields = new FieldSpecs();

    private String name;

    public void addField(String name, FieldTypeEnum type) {
        fields.add(new FieldSpec(name, type));
    }

    public void addField(String name, String label, FieldTypeEnum type) {
        fields.add(new FieldSpec(name, label, type));
    }
}
