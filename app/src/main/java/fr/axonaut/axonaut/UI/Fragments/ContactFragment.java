package fr.axonaut.axonaut.UI.Fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.R;
import fr.axonaut.axonaut.UI.Adapters.ContactAdapter;


public class ContactFragment extends Fragment {
    ApiCallsController mApiCallsController;
    ContactAdapter mAdapter;
    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        Toast.makeText(getActivity(), "CONTACTCREATEVIEW", Toast.LENGTH_SHORT).show();

        mApiCallsController = ApiCallsController.getInstance();
        RecyclerView recyclerView = view.findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(getContext(), mApiCallsController.getArrayCompanies());
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
