package com.angrysurfer.social.shrapnel.util;

import com.angrysurfer.social.shrapnel.Exporter;
import com.angrysurfer.social.shrapnel.component.writer.PdfRowWriter;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PdfUtil {

    public static Document createDocument(String filename) throws IOException {
        return createDocument(filename, PageSize.Default);
    }

    public static Document createDocument(String filename, PageSize pageSize) throws IOException {
        FileUtil.ensureSafety(filename);

        PdfWriter pdfWriter = new PdfWriter(filename, getWriterProperties());
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(pageSize);
        pdfDocument.addNewPage();

        return new Document(pdfDocument);
    }

    public static Document createDocument(ByteArrayOutputStream baos, PageSize pageSize) {
        PdfWriter pdfWriter = new PdfWriter(baos, getWriterProperties());
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(pageSize);
        pdfDocument.addNewPage();

        return new Document(pdfDocument);
    }

    public static Document createDocument(ByteArrayOutputStream baos) {
        return createDocument(baos, PageSize.Default);
    }

    public static String getFontName(Object obj) {
        String fontName = null;

        if (obj instanceof String)
            return obj.toString();

        if (obj instanceof PdfFont) {
            PdfFont pdfFont = (PdfFont) obj;
            FontProgram fontProgram = pdfFont.getFontProgram();
            fontName = fontProgram.toString();
        }

        return fontName;
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> items, PdfRowWriter pdfRowWriter, PageSize pageSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = createDocument(baos, pageSize);
        writeToTable(items, pdfRowWriter);
        document.add(pdfRowWriter.getTable());
        document.close();
        return baos;
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> items, Exporter exporter) {
        return generateByteArrayOutputStream(items, exporter.getPdfRowWriter(), exporter.getPageSize());
    }

    public static ByteArrayOutputStream generateByteArrayOutputStream(Collection<Object> items, PdfRowWriter pdfRowWriter) {
        return generateByteArrayOutputStream(items, pdfRowWriter, PageSize.Default);
    }

    static WriterProperties getWriterProperties() {
        return getWriterProperties(PdfVersion.PDF_2_0);
    }

    static WriterProperties getWriterProperties(PdfVersion pdfVersion) {
        WriterProperties properties = new WriterProperties();
        properties.setPdfVersion(pdfVersion);
        return properties;
    }

    public static String writeTabularFile(Collection<Object> items, PdfRowWriter pdfRowWriter, String filename) throws IOException {
        return writeTabularFile(items, pdfRowWriter, filename, PageSize.Default);
    }

    public static String writeTabularFile(Collection<Object> items, PdfRowWriter pdfRowWriter, String filename, PageSize pageSize) throws IOException {
        String outputFileName = FileUtil.getOutputFileName(filename, "pdf");
        FileUtil.ensureSafety(outputFileName);
        Document document = createDocument(outputFileName, pageSize);
        writeToTable(items, pdfRowWriter);
        document.add(pdfRowWriter.getTable());
        document.close();
        return outputFileName;
    }

    public static String writeTabularFile(Collection<Object> items, Exporter exporter, String filename) throws IOException {
        return writeTabularFile(items, exporter.getPdfRowWriter(), filename, exporter.getPageSize());
    }

    public static void writeToTable(Collection<Object> items, PdfRowWriter pdfRowWriter, Table table) {
        Map<String, Object> outputConfig = new HashMap<>();
        outputConfig.put(PdfRowWriter.TABLE, table);
        pdfRowWriter.writeValues(outputConfig, items);
    }

    public static void writeToTable(Collection<Object> items, PdfRowWriter pdfRowWriter) {
        Table table = pdfRowWriter.createTable();
        Map<String, Object> outputConfig = new HashMap<>();
        outputConfig.put(PdfRowWriter.TABLE, table);
        pdfRowWriter.writeValues(outputConfig, items);
    }
}
