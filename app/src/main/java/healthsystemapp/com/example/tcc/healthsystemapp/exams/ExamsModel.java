package healthsystemapp.com.example.tcc.healthsystemapp.exams;

public class ExamsModel {

    private String date_exam;
    private String anotation;
    private String healthInstituionName;
    private String healthPhoto;
    private String medicName;
    private String medicPhoto;
    private String examID;


    public ExamsModel(){

    }

    public ExamsModel(String date_exam, String anotation, String healthInstituionName, String healthPhoto, String medicName, String medicPhoto, String examID){
        this.date_exam = date_exam;
        this.anotation = anotation;
        this.healthInstituionName = healthInstituionName;
        this.healthPhoto = healthPhoto;
        this.medicName = medicName;
        this.medicPhoto = medicPhoto;
        this.examID = examID;
    }



    public String getDate_exam() {
        return date_exam;
    }

    public void setDate_exam(String date_exam) {
        this.date_exam = date_exam;
    }

    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    public String getHealthInstituionName() {
        return healthInstituionName;
    }

    public void setHealthInstituionName(String healthInstituionName) {
        this.healthInstituionName = healthInstituionName;
    }

    public String getHealthPhoto() {
        return healthPhoto;
    }

    public void setHealthPhoto(String healthPhoto) {
        this.healthPhoto = healthPhoto;
    }

    public String getMedicName() {
        return medicName;
    }

    public void setMedicName(String medicName) {
        this.medicName = medicName;
    }

    public String getMedicPhoto() {
        return medicPhoto;
    }

    public void setMedicPhoto(String medipPhoto) {
        this.medicPhoto = medipPhoto;
    }

    public String getExamID() {
        return examID;
    }

}
