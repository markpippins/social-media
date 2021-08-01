package com.angrysurfer.shrapnel.component.style.preset;

import com.angrysurfer.shrapnel.component.style.PDFFontSource;
import com.angrysurfer.shrapnel.component.style.StyleAdapter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Setter
@Getter
@Slf4j
public class CellStyleAdapter extends StyleAdapter {

    public CellStyleAdapter() {

        PdfFont pdfFont = null;
        try {
            pdfFont = PDFFontSource.getPdfFont("calibri");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            try {
                pdfFont = PdfFontFactory.createFont(FontConstants.COURIER);
            } catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
        }

        if (Objects.nonNull(pdfFont))
            setFont(pdfFont);

        setFontSize(7);
        setMargin(1);
        setPadding(1);
    }
}
