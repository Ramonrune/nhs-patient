package healthsystemapp.com.example.tcc.healthsystemapp.physicians.info;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.adapter.InstitutionAdapter;

public class InstitutionController extends Fragment {

    @BindView(R.id.institutionRecyclerView)
    RecyclerView institutionRecyclerView;


    @BindView(R.id.loadingLinearLayout)
    LinearLayout loadingLinearLayout;

    @BindView(R.id.emptyLinearLayout)
    LinearLayout emptyLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        initializeResources(inflater, container);


        return view;
    }

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_institution_controller, container, false);
        ButterKnife.bind(this, view);



    }





    public void showInstitutions(List<InstitutionModel> institutionModelList) {
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        institutionRecyclerView.setLayoutManager(layout);

        //institutionRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        System.out.println(institutionModelList.size() +  "------");
        institutionRecyclerView.setAdapter(new InstitutionAdapter(getActivity(), institutionModelList));
        loadingLinearLayout.setVisibility(View.GONE);
        institutionRecyclerView.setVisibility(View.VISIBLE);
        emptyLinearLayout.setVisibility(View.GONE);

        if (institutionModelList.size() == 0) {
            emptyLinearLayout.setVisibility(View.VISIBLE);
        }


    }




    private View view;
}
