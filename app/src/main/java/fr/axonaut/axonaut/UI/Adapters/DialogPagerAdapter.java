package fr.axonaut.axonaut.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.Models.CompanyForListModel;
import fr.axonaut.axonaut.Models.PagerModel;
import fr.axonaut.axonaut.Models.PipeModel;
import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;
import fr.axonaut.axonaut.R;
import fr.axonaut.axonaut.Utils.AutoResizeEditText;
import fr.axonaut.axonaut.Utils.ClearableEditText;
import fr.axonaut.axonaut.Utils.CustomFieldLayout;
import fr.axonaut.axonaut.Utils.MultiSelectionSpinner;

import static fr.axonaut.axonaut.Controllers.ApiCallsController.API_KEY;

/**
 * Created by Kelian on 08/02/2018.
 */

public class DialogPagerAdapter extends PagerAdapter {

    private Context context;
    private List<PagerModel> pagerModelList;
    private LayoutInflater inflater;
    private ClearableEditText opportunityName;
    private EditText opportunityAmount;
    private SeekBar opportunityProbability;
    private AutoResizeEditText opportunityComments;
    private MultiSelectionSpinner opportunityCompanyEmployees;
    private Spinner pipeName, pipeStepName;
    private AutoCompleteTextView opportunityCompany;
    private int mPipeStepPosition, mPipeStepNamePosition;
    private ApiCallsController mApiCallsController;
    private CompanyForListModel companyPosition;

    public DialogPagerAdapter(Context context, List<PagerModel> pagerModelList, int pipeStepNamePosition, int pipeStepPosition) {
        this.context = context;
        this.pagerModelList = pagerModelList;
        this.mPipeStepPosition = pipeStepPosition;
        this.mPipeStepNamePosition = pipeStepNamePosition;

        mApiCallsController = ApiCallsController.getInstance();
        inflater = ((Activity) context).getLayoutInflater();
    }


    @Override
    public int getCount() {
        return pagerModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PagerModel model = pagerModelList.get(position);

        View view = inflater.inflate(model.getViewLayout(), container, false);
        view.setTag(position);
        switch (model.getViewLayout()){
            case R.layout.alert_dialog_create_opportunity_2:
                Button buttonShowPipeStep = view.findViewById(R.id.buttonShowPipeStep);
                pipeName = view.findViewById(R.id.spinnerPipeName);
                pipeStepName = view.findViewById(R.id.spinnerPipeStepName);

                buttonShowPipeStep.setOnClickListener(v -> {
                    buttonShowPipeStep.setVisibility(View.GONE);
                    pipeName.setVisibility(View.VISIBLE);
                    pipeStepName.setVisibility(View.VISIBLE);
                });
                ArrayList<String> pipeList = new ArrayList<>();
                for (PipeModel pipeModel : mApiCallsController.getPipeArray()){
                    pipeList.add(pipeModel.getName());
                }
                pipeName.setAdapter(new ArrayAdapter<>(context
                        , R.layout.support_simple_spinner_dropdown_item
                        , pipeList));
                pipeName.setSelection(mPipeStepPosition);
                pipeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        ArrayList<String> pipeStepNameList = new ArrayList<>();
                        for (PipeStepWithOpportunityModel pipeStepModel : mApiCallsController.getPipeArray()
                                .get(position).getPipeSteps()){
                            pipeStepNameList.add(pipeStepModel.getName());
                        }
                        pipeStepName.setAdapter(new ArrayAdapter<>(context
                                , R.layout.support_simple_spinner_dropdown_item
                                , pipeStepNameList));
                        pipeStepName.setSelection(mPipeStepNamePosition);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                opportunityAmount = view.findViewById(R.id.editTextOpportunityAmount);
                opportunityProbability = view.findViewById(R.id.probabilitySeekBar);
                TextView progressText = view.findViewById(R.id.progressText);

                opportunityProbability.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        String placeHolder = "Progression (" + progress + " %)";
                        progressText.setText(placeHolder);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case R.layout.alert_dialog_create_opportunity_1:
                opportunityCompany = view.findViewById(R.id.spinnerOpportunityCompany);
                opportunityCompanyEmployees = view.findViewById(R.id.spinnerOpportunityEmployees);
                opportunityName = view.findViewById(R.id.editTextOpportunityName);
                opportunityComments = view.findViewById(R.id.autoEditTextOpportunityComments);
                final ArrayAdapter<CompanyForListModel> arrayAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_dropdown_item_1line,
                        mApiCallsController.getArrayCompanies());
                opportunityCompany.setAdapter(arrayAdapter);
                opportunityCompany.setOnClickListener(arg0 -> opportunityCompany.showDropDown());
                opportunityCompany.setOnItemClickListener((adapterView, view1, pos, l) -> {
                        ArrayList<String> array = new ArrayList<>();
                        Object item = adapterView.getItemAtPosition(pos);
                        if (item instanceof CompanyForListModel) {
                            companyPosition = (CompanyForListModel) item;
                            for (int i = 0; i < companyPosition.getEmployees().size(); i++) {
                                array.add(companyPosition.getEmployees().get(i).toString());
                            }
                            if (array.size() > 0) {
                                opportunityCompanyEmployees.setTitle("Contacts");
                                opportunityCompanyEmployees.setItems(array);
                                opportunityCompanyEmployees.setSelection(new int[1]);
                                opportunityCompanyEmployees.setVisibility(View.VISIBLE);
                            } else {
                                opportunityCompanyEmployees.setVisibility(View.GONE);
                            }
                            opportunityName.setText(String.format(Locale.FRANCE, "Offre #%d %s", companyPosition.getNbOpportunities() + 1, companyPosition.getName()));
                        }
                });
            break;
        }

        container.addView(view);

        return view;
    }

    public void pushOpportunity(Context context, String idOpportunity){
        HashMap<String, String> map = new HashMap<>();
        map.put("accountApiKey", API_KEY);
        map.put("idOpportunity", idOpportunity);
        map.put("name", opportunityName.getText().toString());
        map.put("amount", opportunityAmount.getText().toString());

        //TODO: SI PAS EXISTANT LUI DIRE DE CREER UN CONTACT D'ABORD
        if (companyPosition.getEmployees().size() > 0) {
        StringBuilder employeeString = new StringBuilder();
            for (int i = 0; i < opportunityCompanyEmployees.getSelectedIndices().size(); i++) {
                if (i != 0) employeeString.append(", ");
                employeeString.append(companyPosition.getEmployees().get(i).getId());
            }
        map.put("employeeIds", employeeString.toString());
        }
        map.put("probability", String.valueOf(opportunityProbability.getProgress()));
        map.put("comments", opportunityComments.getText().toString());
        map.put("companyId", String.valueOf(companyPosition.getCompanyId()));
        map.put("pipeName", mApiCallsController.getPipeArray().get(pipeName.getSelectedItemPosition()).getName());
        map.put("pipeStepName", mApiCallsController.getPipeArray().get(pipeName.getSelectedItemPosition()).getPipeSteps().get(pipeStepName.getSelectedItemPosition()).getName());
        mApiCallsController.addOpportunity(context, map);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
