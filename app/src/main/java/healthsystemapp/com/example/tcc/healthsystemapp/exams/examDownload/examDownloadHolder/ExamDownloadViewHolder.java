package healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.examDownloadHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class ExamDownloadViewHolder extends RecyclerView.ViewHolder {

    private TextView attachmentNameTextView;
    private Button  downloadAttachmentButton;



    public ExamDownloadViewHolder(@NonNull View itemView){
        super(itemView);

        attachmentNameTextView = (TextView) itemView.findViewById(R.id.attachmentNameTextView);
        downloadAttachmentButton = (Button) itemView.findViewById(R.id.downloadAttachmentButton);
    }

    public TextView getAttachmentNameTextView() {
        return attachmentNameTextView;
    }

    public void setAttachmentNameTextView(TextView attachmentNameTextView) {
        this.attachmentNameTextView = attachmentNameTextView;
    }

    public Button getDownloadAttachmentButton() {
        return downloadAttachmentButton;
    }

    public void setDownloadAttachmentButton(Button downloadAttachmentButton) {
        this.downloadAttachmentButton = downloadAttachmentButton;
    }
}
