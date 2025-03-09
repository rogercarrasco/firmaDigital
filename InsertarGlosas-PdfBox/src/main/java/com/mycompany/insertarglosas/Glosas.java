
package com.mycompany.insertarglosas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.pdfbox.util.Matrix;

public class Glosas {
    
    private String hMsg;
    private String vMsg;
    private final PDFont fontMsg = PDType1Font.COURIER;
    private final PDFont fontUrl = PDType1Font.COURIER_BOLD;
    private final PDFont fontCvd = PDType1Font.COURIER_BOLD;
    private final int fontSizeMsg = 8;
    private final int fontSizeCvd = 10;
    private final int fontSizeUrl = 10;
    private final int qrSize = 65;
    private final int urlValidatorFontSize = 8;
    private final float leading = fontSizeMsg; //1.5f*fontSize;
    private final float vMargin = 25f; 
    private final float hMargin = 45f; 
    private String cvd;
    private String urlVerif;
    
    public void genCVD(File srcFile, File dstFile, String entity, String begin, String end, String url) throws Exception {
        
        hMsg = "Esta es una representación impresa cuya autenticidad puede ser "
                + "contrastada con la representación imprimible localizada en la "
                + "sede digital de la " + entity + ". La verificación "
                + "puede ser efectuada a partir del " + begin + " hasta el " + end + ". "
                + "Base Legal: Decreto Legislativo Nº 1412, Decreto Supremo "
                + "N° 029-2021-PCM y la Directiva Nº 002-2021-PCM/SGD.";
        
        vMsg = "Documento electrónico firmado digitalmente en el marco de la Ley N° 27269, "
                + "su Reglamento y modificatorias. Su autenticidad e integridad "
                + "pueden ser verificadas en ";
        
        try (PDDocument doc = PDDocument.load(srcFile)) {
            // calcular cvd
            cvd = "CVD: 0041 6818 3782 4539";
            urlVerif = url;
            for (PDPage page : doc.getPages()) {
                int width = (int)(page.getMediaBox().getWidth()-2*vMargin);
                List<String> lines = breakLongString(hMsg, width-qrSize);
                addHorizontalText(doc, page, lines);
                
                int height = (int)(page.getMediaBox().getHeight()-2*hMargin);
                List<String> lines2 = breakLongString(vMsg, height);
                addVerticalText(doc, page, lines2);
            }
            doc.save(dstFile);
        }
    }
    
    private List<String> breakLongString(String msg, int width) throws IOException {
        
        String text = msg;
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = fontSizeMsg*fontMsg.getStringWidth(subString)/1000;
            if (size > width) {
                if (lastSpace < 0) lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            }
            else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            }
            else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }

    private void addHorizontalText(PDDocument doc, PDPage page, List<String> lines) throws IOException, WriterException {
        
        float locYrect = 10f;
        PDRectangle mediabox = page.getMediaBox();
        float startXrect = mediabox.getLowerLeftX() + vMargin;
        float startYrect = mediabox.getLowerLeftY() + locYrect;
        float widthRect = mediabox.getWidth()-2*vMargin;
        float heightRect = lines.size()*leading + fontSizeUrl + fontSizeCvd;
        
        try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            cs.beginText();
            cs.setFont(fontMsg, fontSizeMsg);
            cs.newLineAtOffset(startXrect, startYrect + heightRect - fontSizeMsg);
            for (String line: lines) {
                cs.showText(line);
                cs.newLineAtOffset(0, -leading);
            }
            cs.endText(); 
            
            // the url
            cs.beginText();
            cs.setFont(fontUrl, fontSizeUrl);
            cs.newLineAtOffset(startXrect, startYrect + fontSizeUrl);
            cs.showText("URL: " + urlVerif);
            cs.endText();
            
            // the cvd
            cs.beginText();
            cs.setFont(fontCvd, fontSizeCvd);
            cs.newLineAtOffset(startXrect, startYrect);
            cs.showText(cvd);
            cs.endText();
            
            // the rectangle
            cs.setNonStrokingColor(Color.BLACK);
            cs.addRect(startXrect, startYrect-2, widthRect, heightRect);
            cs.stroke();
            
            //create the QRCode
            BufferedImage bImage = createQR(urlVerif, qrSize, qrSize);
            bImage = bImage.getSubimage(10, 10, qrSize-2*10, qrSize-2*10); // 20 aprox qrSize/4
            ImageIOUtil.writeImage(bImage, "qr.png", 300);
            
            PDImageXObject pdiImage = JPEGFactory.createFromImage(doc, bImage);
            cs.drawImage(pdiImage, 520, 10);
            
            // close
            cs.close();
        }
    }
    
    private void addVerticalText(PDDocument doc, PDPage page, List<String> lines) throws IOException {
        
        float locYrect = 10f;
        PDRectangle mediabox = page.getMediaBox();
        float startXrect = mediabox.getLowerLeftX() + vMargin;
        float startYrect = mediabox.getLowerLeftY() + locYrect;
        float widthRect = mediabox.getHeight()-2*hMargin;
        float heightRect = lines.size()*leading;
        
        try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            cs.transform(Matrix.getRotateInstance(1.5708, 30, hMargin));
            cs.beginText();
            cs.setFont(fontMsg, fontSizeMsg);
            cs.newLineAtOffset(startXrect, startYrect + heightRect - fontSizeMsg);
            for (int i=0; i<lines.size(); i++) {
                String line = lines.get(i);
                cs.showText(line);
                if (i<lines.size()-1) cs.newLineAtOffset(0, -leading);
            }
            cs.setFont(fontUrl, urlValidatorFontSize);
            cs.setNonStrokingColor(0.0f,0.0f,1.0f);
            cs.showText(" https://apps.firmaperu.gob.pe/web/validador.xhtml");
            cs.endText(); 
                  
            cs.close();
        }
    }
        
    private BufferedImage createQR(String data, int w, int h) throws WriterException {
        
        Writer writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, w, h);
        BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                bimage.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        return bimage;
    }
}
