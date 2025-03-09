
package com.mycompany.removerfirmas.pdfbox;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

public class Main {

    public static void main(String[] args) throws IOException {
        
        File file = new File("confirmas.pdf");
        PDDocument document = PDDocument.load(file);
        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = documentCatalog.getAcroForm();
        
        acroForm.flatten();
        document.save("sinfirmas.pdf");
        document.close();
    }
}
