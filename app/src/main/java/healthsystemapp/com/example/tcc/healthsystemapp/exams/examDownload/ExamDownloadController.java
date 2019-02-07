package healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.examDownloadAdapter.ExamDownloadAdapter;
import util.Uri;

public class ExamDownloadController extends Fragment {

    private ExamDownloadView examDownloadView;
    private View view;
    private List<ExamDownloadModel> examDownloadList;
    private StringRequest request;
    private RequestQueue requestQueue;
    private Uri uri = new Uri();

    public ExamDownloadController(){

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_exams));
        inicializeResources(inflater, container);
        initializeRecyclerView();

        return view;
    }

    private void inicializeResources(LayoutInflater inflater, ViewGroup container){

        //String myValue =

        //System.out.println("PARAMETRO" + myValue);

        view  = inflater.inflate(R.layout.fragment_exam_download_view, container, false);

        examDownloadView = new ExamDownloadView(view);

    }

    private void initializeRecyclerView() {

        ArrayList<ExamDownloadModel> examDownloadModel = (ArrayList<ExamDownloadModel>)getArguments().getSerializable("examList");



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        examDownloadView.getRecyclerView().setLayoutManager(layoutManager);

        //

        examDownloadView.getRecyclerView().setAdapter(new ExamDownloadAdapter(examDownloadModel, getActivity()));

    }

}
