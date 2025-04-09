package solvolabs.ai.me_ai;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class PdfReaderService {

    public String readPdfFromResources(String pathInResources) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(pathInResources);
             PDDocument document = PDDocument.load(is)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du PDF : " + pathInResources, e);
        }
    }
}
