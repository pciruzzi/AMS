package fr.insa.ams;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import fr.insa.ams.stateMachine.InternshipAgreementState;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class InternshipAgreement implements Databasable {

    // It has the same id than the Application
    private int id;
    private Application application;
    private InternshipAgreementState state;

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public InternshipAgreement() {}
    public InternshipAgreement(Application application) {
        this.application = application;
        this.state = InternshipAgreementState.WAITING_STUDENT;
    }

    public int getId() { return this.id;}
    public Application getApplication() { return this.application;}
    public InternshipAgreementState getState() { return this.state;}

    public void setId(int id) { this.id = id;}
    public void setApplication(Application application) { this.application = application;}
    public void setState(InternshipAgreementState state) { this.state = state;}

    public Student getStudent() { return this.application.getStudent();}
    public ClassCoordinator getCoordinator() { return this.application.getCoordinator();}
    public Partner getPartner() { return this.application.getPartner();}
    public int getOfferID() { return this.application.getOfferID();}

    public String generatePdf(String folder) throws DocumentException, FileNotFoundException {
        Document document = new Document(PageSize.A4);
        String filename = folder + "/" + this.id + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        writeInternshipAgreement(document);
        document.close();
        return filename;
    }

    public void generatePdf2(String folder) throws IOException {
        File file = new File("/home/pablo/Francia/projetPDL/ams/formTest.pdf");
        PDDocument pdfTemplate = PDDocument.load(file);
        PDDocumentCatalog docCatalog = pdfTemplate.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

//        List<PDField> fields = acroForm.getFields();
//        List<String> fieldNames = new ArrayList<String>();
//        for (PDField field : fields) {
//            fieldNames.add(field.getFullyQualifiedName());
//            System.out.println(field.getFullyQualifiedName());
//        }
//
//        PDField field = acroForm.getField(fieldNames.get(0));
//        field.setValue("example value");

        PDField field = acroForm.getField("studentId");
        field.setValue(String.valueOf(this.getStudent().getId()));
        field.setReadOnly(true);
        field = acroForm.getField("partnerId");
        field.setValue(String.valueOf(this.getPartner().getId()));
        field = acroForm.getField("coordinatorId");
        field.setValue(String.valueOf(this.getCoordinator().getId()));
        field = acroForm.getField("offerId");
        field.setValue(String.valueOf(this.getOfferID()));

        pdfTemplate.save(new File("/home/pablo/Francia/projetPDL/ams/out.pdf"));
        pdfTemplate.close();
    }

    private void writeInternshipAgreement(Document document) throws DocumentException {
        document.add(new Paragraph("The student is " + this.getStudent().getName()));
        document.add(new Paragraph("The class coordinator is " + this.getCoordinator().getName()));
        document.add(new Paragraph("The partner is " + this.getPartner().getName()));
        document.add(new Paragraph("The offer id is " + this.getOfferID()));
    }
}
