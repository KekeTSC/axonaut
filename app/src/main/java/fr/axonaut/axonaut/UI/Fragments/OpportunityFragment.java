package fr.axonaut.axonaut.UI.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;

import java.util.ArrayList;
import java.util.List;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.Controllers.NavigationController;
import fr.axonaut.axonaut.Models.OpportunityModel;
import fr.axonaut.axonaut.Models.PipeModel;
import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;
import fr.axonaut.axonaut.UI.Adapters.OpportunityStepAdapter;
import fr.axonaut.axonaut.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpportunityFragment extends Fragment implements ApiCallsController.DataReadyListener {

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    OpportunityStepAdapter mAdapter;
    private ApiCallsController mApiCallsController;
        Spinner mSpinner;
        private ArrayList<PipeModel> mPipeList = new ArrayList<>();
    private NavigationController mNavigationController;

    public OpportunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);
        Toast.makeText(getActivity(), "OPPCREATEVIEW", Toast.LENGTH_SHORT).show();

        mApiCallsController = ApiCallsController.getInstance();
        mNavigationController = NavigationController.getInstance();
        mApiCallsController.setDataReadyListener(this);
        mPipeList = mApiCallsController.getPipeArray();
        mAdapter = new OpportunityStepAdapter(getContext(), mNavigationController.getPipePosition());
        RecyclerView recyclerView = view.findViewById(R.id.opportunityStateRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mSpinner = view.findViewById(R.id.spinner);

        setSpinner();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mNavigationController.setPipePosition(position);
                mAdapter.updateAdapter(mPipeList.get(mSpinner.getSelectedItemPosition()).getPipeSteps(), mSpinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void OnDataReady(ArrayList<PipeModel> pipeList) {
        this.mPipeList = pipeList;
        setSpinner();
    }

    @Override
    public void OnOpportinityPushed() {
        mApiCallsController.getOpportunities(getContext());
    }

    private void setSpinner(){
        List<String> list = new ArrayList<>();
        for (PipeModel pipeModel : mPipeList){
            list.add(pipeModel.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, list);
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setSelection(mNavigationController.getPipePosition());
        mAdapter.updateAdapter(mPipeList.get(mSpinner.getSelectedItemPosition()).getPipeSteps(), mSpinner.getSelectedItemPosition());
    }
}
