package fr.axonaut.axonaut;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpportunityFragment extends Fragment {


    public OpportunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);
        Toast.makeText(getActivity(), "OPPCREATEVIEW", Toast.LENGTH_SHORT).show();

        return view;
    }

}
