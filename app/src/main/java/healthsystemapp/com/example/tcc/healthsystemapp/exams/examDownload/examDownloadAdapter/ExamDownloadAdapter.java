package healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.examDownloadAdapter;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.ExamDownloadModel;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.examDownloadHolder.ExamDownloadViewHolder;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import util.Uri;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ExamDownloadAdapter extends RecyclerView.Adapter<ExamDownloadViewHolder> {

    private List<ExamDownloadModel> examsDownload;
    private Context context;
    private RequestQueue requestQueue;
    private StringRequest request;
    private Uri uri = new Uri();


    public ExamDownloadAdapter(List<ExamDownloadModel> examDownloadList, Context context) {
        this.examsDownload = examDownloadList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExamDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.exam_download_item, viewGroup, false);
        ExamDownloadViewHolder holder = new ExamDownloadViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull  ExamDownloadViewHolder examDownloadViewHolder, int position) {
        ExamDownloadViewHolder holder = (ExamDownloadViewHolder) examDownloadViewHolder;
        final ExamDownloadModel examDownloadModel = examsDownload.get(position);

        holder.getAttachmentNameTextView().setText(examDownloadModel.getAttachmentName());
        holder.getDownloadAttachmentButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadAttachment(examDownloadModel.getIdExamAttachment().toString(), examDownloadModel.getAttachmentName().toString());

            }
        });

    }


    private void downloadAttachment(String idExamAttachment, String attachmentName){

        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context.getApplicationContext());

        String url = uri.examDownload() + idExamAttachment;
        DownloadManager.Request request = new DownloadManager.Request(android.net.Uri.parse(url));
        request.addRequestHeader("Authorization", loginSharedPreferences.getToken());
        //request.setDescription(attachmentName);
        request.setTitle(attachmentName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);


    }



    @Override
    public int getItemCount() {
        return examsDownload.size();
    }
}
