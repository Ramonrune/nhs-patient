package healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload;

import java.io.Serializable;

public class ExamDownloadModel implements Serializable {

    private String attachmentName;
    private String idExam;
    private String idExamAttachment;


    public ExamDownloadModel(){

    }

    public ExamDownloadModel(String attachmentName, String idExam, String idExamAttachment){
        this.attachmentName = attachmentName;
        this.idExam = idExam;
        this.idExamAttachment = idExamAttachment;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getIdExam() {
        return idExam;
    }

    public String getIdExamAttachment() {
        return idExamAttachment;
    }

}
