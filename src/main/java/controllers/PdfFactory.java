package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PdfFactory {

    public static Document criaPDF() {
        Document document = new Document();
        return document;
    }

    public static void criaAtributos(Document doc) {
        try {
//            PdfWriter writer = PdfWriter.getInstance(doc, fos);
            doc.open();
            doc.add(new Paragraph("Teste"));
            doc.addAuthor("Rafael Graunke");
            doc.addCreationDate();
            doc.addTitle("Tabela de turmas");
            doc.addSubject("Um exemplo da criação de PDFs.");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}
