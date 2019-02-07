package healthsystemapp.com.example.tcc.healthsystemapp.physicians;

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
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.PhysicianModel;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.adapter.PhysicianAdapter;


public class PhysicianKnownController extends Fragment {

    @BindView(R.id.physicianRecyclerView)
    RecyclerView physicianRecyclerView;


    @BindView(R.id.loadingGpsLinearLayout)
    LinearLayout loadingGpsLinearLayout;


    @BindView(R.id.noGpsLinearLayout)
    LinearLayout noGpsLinearLayout;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_physicians));

        initializeResources(inflater, container);

        return view;
    }


    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_physician_known_controller, container, false);
        ButterKnife.bind(this, view);

        System.out.println("Fui chamado");

        //callHandler.showPhysicians(institutionModel.getIdHealthInstitution());


    }


    public void showPhysicians(List<PhysicianModel> physicianModelList) {


        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        physicianRecyclerView.setLayoutManager(layout);

        //institutionRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        physicianRecyclerView.setAdapter(new PhysicianAdapter(getActivity(), physicianModelList));
        loadingGpsLinearLayout.setVisibility(View.GONE);
        physicianRecyclerView.setVisibility(View.VISIBLE);
        noGpsLinearLayout.setVisibility(View.GONE);



    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_phsysicians);
    }


    private View view;
}
