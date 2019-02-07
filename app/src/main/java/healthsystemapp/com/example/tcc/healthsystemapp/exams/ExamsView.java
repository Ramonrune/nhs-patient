package healthsystemapp.com.example.tcc.healthsystemapp.exams;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class ExamsView {

    private RecyclerView recyclerView;

    public ExamsView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    }

    public RecyclerView getRecyclerView(){return recyclerView;}

}
