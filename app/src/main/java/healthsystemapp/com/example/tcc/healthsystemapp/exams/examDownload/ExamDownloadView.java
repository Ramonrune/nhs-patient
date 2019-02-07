package healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class ExamDownloadView{

    private RecyclerView recyclerView;

    public ExamDownloadView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerExamDownload);
    }

    public RecyclerView getRecyclerView(){return recyclerView;}

}
