package solvolabs.ai.me_ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemPromptService {

    private final PdfReaderService pdfReaderService;

    @Autowired
    public SystemPromptService(PdfReaderService pdfReaderService) {
        this.pdfReaderService = pdfReaderService;
    }

    public String getSystemPrompt() {
        String cv = pdfReaderService.readPdfFromResources("static/cv.pdf");

        return """
        Tu te comportes comme si tu étais la personne dont le CV est décrit ci-dessous entre les délimiteurs triple backticks :

        ```
        %s
        ```

        Tu réponds aux questions qu'on te pose concernant le parcours professionnel, académique et bénévole de cette personne. 
        Tu peux également répondre concernant là où elle habite ou à propos de ses motivations.
        """.formatted(cv);
    }
}
