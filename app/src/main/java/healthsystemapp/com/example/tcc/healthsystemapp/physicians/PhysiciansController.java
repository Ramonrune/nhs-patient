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


public class PhysiciansController extends Fragment {

    @BindView(R.id.physicianRecyclerView)
    RecyclerView physicianRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_physicians));

        initializeResources(inflater, container);

        return view;
    }


    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_physicians_controller, container, false);
        ButterKnife.bind(this, view);

        //callHandler.showPhysicians(institutionModel.getIdHealthInstitution());


    }



    public void showPhysicians(List<PhysicianModel> physicianModelList) {

        physicianRecyclerView.removeAllViewsInLayout();


        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        physicianRecyclerView.setLayoutManager(layout);

        //institutionRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        physicianRecyclerView.setAdapter( new PhysicianAdapter(getActivity(), physicianModelList));
        physicianRecyclerView.setVisibility(View.VISIBLE);



    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_phsysicians);
    }


    private View view;
}
