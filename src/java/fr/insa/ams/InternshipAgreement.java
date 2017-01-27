package fr.insa.ams;

import fr.insa.ams.stateMachine.InternshipAgreementState;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class InternshipAgreement implements Databasable {

    // It has the same id than the Application
    private int id;
    private Application application;
    private InternshipAgreementState state;

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

    public String generatePdf(String outFolder) throws IOException {
        String formFilename = "/home/remi/dev/NetBeansProjects-6.7.1/AMS/formTest.pdf";
        String outFilename = outFolder + "/" + this.id + ".pdf";

        File file = new File(formFilename);
        PDDocument pdfTemplate = PDDocument.load(file);
        PDDocumentCatalog docCatalog = pdfTemplate.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        Partner partner = this.getPartner();
        addField(acroForm, "partnerName", partner.getName());
        addField(acroForm, "partnerAddress", partner.getAddress());
        addField(acroForm, "partnerTelephone", partner.getTelephone());
        addField(acroForm, "partnerMail", partner.getEmail());
        addField(acroForm, "partnerLocation", partner.getLocation());

        Student student = this.getStudent();
        addField(acroForm, "studentName", student.getName());
        addField(acroForm, "studentAddress", student.getAddress());
        addField(acroForm, "studentTelephone", student.getTelephone());
        addField(acroForm, "studentMail", student.getEmail());
        String studentFormation = student.getYear() + "ème anée " + student.getPathway();
        addField(acroForm, "studentFormation", studentFormation);

        ClassCoordinator coordinator = this.getCoordinator();
        addField(acroForm, "coordinatorName", coordinator.getName());
        addField(acroForm, "coordinatorMail", coordinator.getEmail());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        addField(acroForm, "date", dateFormat.format(date));
        //TODO: Here
        addIntField(acroForm, "internshipPay", 550);
        addField(acroForm, "internshipSubject", "Quelque chose...");
        addField(acroForm, "internshipStartDate", "une date");
        addField(acroForm, "internshipFinDate", "fin date");

        pdfTemplate.save(new File(outFilename));
        pdfTemplate.close();
        return outFilename;
    }

    private void addIntField(PDAcroForm acroForm, String fieldName, int value) throws IOException {
        this.addField(acroForm, fieldName, String.valueOf(value));
    }

    private void addField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
        PDField field = acroForm.getField(fieldName);
        field.setValue(value);
        field.setReadOnly(true);
    }
}
