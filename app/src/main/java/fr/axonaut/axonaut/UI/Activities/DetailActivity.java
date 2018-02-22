package fr.axonaut.axonaut.UI.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.Controllers.ApiCallsController.DataReadyListener;
import fr.axonaut.axonaut.Controllers.DetailController;
import fr.axonaut.axonaut.Controllers.NavigationController;
import fr.axonaut.axonaut.Models.ClientEditingModel;
import fr.axonaut.axonaut.Models.ClientModel;
import fr.axonaut.axonaut.Models.CompanyForListModel;
import fr.axonaut.axonaut.Models.CustomFieldModel;
import fr.axonaut.axonaut.Models.EmployeeModel;
import fr.axonaut.axonaut.Models.OpportunityModel;
import fr.axonaut.axonaut.Models.PipeModel;
import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;
import fr.axonaut.axonaut.R;
import fr.axonaut.axonaut.UI.Adapters.EmployeeDetailAdapter;
import fr.axonaut.axonaut.UI.Adapters.EmployeeEditingAdapter;
import fr.axonaut.axonaut.Utils.CustomFieldLayout;
import fr.axonaut.axonaut.Utils.InputFilterMinMax;
import fr.axonaut.axonaut.Utils.MultiSelectionSpinner;

public class DetailActivity extends AppCompatActivity implements DataReadyListener {
    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());
    DetailController mDetailController;
    OpportunityModel mOpportunityModel;
    ApiCallsController mApiCallsController;
        Toolbar toolbar;
        CompanyForListModel mCompanyModel;
        TextView textViewCompanyName;
        TextView textViewCompanyAddress;
        EditText textViewDueDate;
        EditText textViewOpportunityAmount;
        EditText textViewOpportunityProbability;
        EditText editTextOpportunityName;
        RecyclerView employeeRecycler;
        DonutProgress probabilityDonut;
        FloatingActionButton fabEditing, fabCancel;
        DatePickerDialog dueDatePicker;
        long dueDate;
    EmployeeEditingAdapter mEditingAdapter;
    NavigationController mNavigationController;
    ImageButton buttonShowComment;
    LinearLayout customFieldLinearLayout, customFieldEditingLinearLayout;

        boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindField();
        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame));

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        textViewOpportunityAmount.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus&& !isEditing){
                pushAmount(textViewOpportunityAmount.getText().toString());
            }
        });

        textViewOpportunityProbability.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus && !isEditing){
                pushProbability(textViewOpportunityProbability.getText().toString());
                probabilityDonut.setProgress(Integer.parseInt(textViewOpportunityProbability.getText().toString()));
            }
        });

        textViewOpportunityProbability.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && !isEditing) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        pushProbability(textViewOpportunityProbability.getText().toString());
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        textViewOpportunityAmount.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && !isEditing) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        pushProbability(textViewOpportunityAmount.getText().toString());
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        fabEditing.setOnClickListener(view -> {
            isEditing = !isEditing;
            if (isEditing) {
                }
            if (isEditing) {
                fabCancel.setVisibility(View.VISIBLE);
                fabEditing.setImageResource(android.R.drawable.ic_menu_search);
                Snackbar.make(view, "Vous êtes en mode édition", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                setEditingMode();
            } else {
                saveOpportunity();
            }
        });

        fabCancel.setOnClickListener(view -> {
            fabCancel.setVisibility(View.GONE);
            mOpportunityModel = mDetailController.getOpportunityById(mOpportunityModel.getId());
            isEditing = false;
            setFields();
        });


        int d,m,y;
        Calendar calendarNow = Calendar.getInstance();
        Calendar dueDateCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        if(!mOpportunityModel.getDueDate().equals("")) {
            try {
                Date dueDate = sdf.parse(mOpportunityModel.getDueDate());
                dueDateCalendar.setTime(dueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        d = dueDateCalendar.get(Calendar.DAY_OF_MONTH);
        m = dueDateCalendar.get(Calendar.MONTH);
        y = dueDateCalendar.get(Calendar.YEAR);

        dueDatePicker = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            cal.set(Calendar.MONTH, datePicker.getMonth());
            cal.set(Calendar.YEAR, datePicker.getYear());
            dueDate = cal.getTimeInMillis();
            textViewDueDate.setText(sdf.format(cal.getTime()));
            if (!isEditing){
                pushDueDate(cal.getTimeInMillis()/1000);
            }
        }, y, m, d);

        textViewDueDate.setOnClickListener(view -> {
            dueDatePicker.getDatePicker().setMinDate(calendarNow.getTimeInMillis());
            dueDatePicker.show();
        });

        buttonShowComment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Commentaires");

            final EditText input = new EditText(this);
            input.setText(Html.fromHtml(mOpportunityModel.getComments()));
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setLines(5);
            input.setMaxLines(5);
            input.setGravity(Gravity.START | Gravity.TOP);
            builder.setView(input);

            builder.setPositiveButton("Valider", (dialog, whichButton) -> {
                pushComments(input.getText().toString());
            });

            builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    private void bindField() {
        toolbar = findViewById(R.id.toolbar);
        fabEditing = findViewById(R.id.fabEditing);
        fabCancel = findViewById(R.id.fabCancel);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDetailController = DetailController.getInstance();
        mApiCallsController = ApiCallsController.getInstance();
        mNavigationController = NavigationController.getInstance();

        mApiCallsController.setDataReadyListener(this);
        mOpportunityModel = mDetailController.getOpportunityModel();
        mCompanyModel = mDetailController.getCompanyById(mOpportunityModel.getCompany().getId());

        editTextOpportunityName = findViewById(R.id.editTextOpportunityName);

        textViewOpportunityAmount = findViewById(R.id.textViewOpportunityAmount);
        textViewOpportunityProbability = findViewById(R.id.textViewOpportunityProgress);
        textViewOpportunityProbability.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        probabilityDonut = findViewById(R.id.probabilityDonut);

        textViewDueDate = findViewById(R.id.textViewDueDate);

        textViewCompanyName = findViewById(R.id.textViewCompanyName);
        textViewCompanyAddress = findViewById(R.id.textViewCompanyAddress);

        employeeRecycler = findViewById(R.id.employeeRecyclerView);
        employeeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        buttonShowComment = findViewById(R.id.buttonShowComment);

        customFieldLinearLayout = findViewById(R.id.customFieldLinearLayout);
        customFieldEditingLinearLayout = findViewById(R.id.customFieldEditingLinearLayout);
        setFields();
    }

    private void setFields(){
        setTitle(mOpportunityModel.getName());

        editTextOpportunityName.setText(mOpportunityModel.getName());
        editTextOpportunityName.setVisibility(View.GONE);

        textViewOpportunityAmount.setText(String.valueOf(mOpportunityModel.getAmount()));
        textViewOpportunityAmount.getBackground().setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.CLEAR);
        textViewOpportunityProbability.setText(String.valueOf(mOpportunityModel.getProbability()));
        textViewOpportunityProbability.getBackground().setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN);

        probabilityDonut.setProgress(mOpportunityModel.getProbability());

        textViewDueDate.setText(mOpportunityModel.getDueDate().equals("") ? "__/__/____" : mOpportunityModel.getDueDate());
        textViewDueDate.getBackground().setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN);
        textViewCompanyName.setText(mCompanyModel.getName());
        textViewCompanyAddress.setText(String.format("%s, %s %s",
                mCompanyModel.getAddressStreet(),
                mCompanyModel.getAddressZipCode(),
                mCompanyModel.getAddressTown()));
        textViewCompanyAddress.setOnClickListener(view -> {
            String map = "http://maps.google.co.in/maps?q=" + textViewCompanyAddress.getText().toString();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        });
        employeeRecycler.setAdapter(new EmployeeDetailAdapter(this, mOpportunityModel.getEmployees()));

        customFieldLinearLayout.setVisibility(View.VISIBLE);
        customFieldEditingLinearLayout.setVisibility(View.GONE);
        customFieldLinearLayout.removeAllViews();
        for (String key : mOpportunityModel.getCustomFieldsMap().keySet()) {
            CustomFieldLayout customFieldLayout = new CustomFieldLayout(this);
            customFieldLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            customFieldLayout.setCustomFieldName(key);
            customFieldLayout.setCustomFieldResult(mOpportunityModel.getCustomFieldsMap().get(key));
            customFieldLinearLayout.addView(customFieldLayout);
        }
    }


    private void setEditingMode(){
        setTitle("Mode édition");
        editTextOpportunityName.setVisibility(View.VISIBLE);

        textViewOpportunityAmount.getBackground().clearColorFilter();
        textViewOpportunityProbability.getBackground().clearColorFilter();
        textViewDueDate.getBackground().clearColorFilter();
        probabilityDonut.setProgress(0);

        ArrayList<ClientEditingModel> clientList = new ArrayList<>();
        for (ClientModel clientModel : mCompanyModel.getEmployees()){
            boolean isSelected = false;
            for (EmployeeModel employeeModel : mOpportunityModel.getEmployees()) {
                if (clientModel.getId()==employeeModel.getId()) {
                    isSelected = true;
                }
            }
            clientList.add(new ClientEditingModel(clientModel, isSelected));
        }
        mEditingAdapter = new EmployeeEditingAdapter(this, clientList);
        employeeRecycler.setAdapter(mEditingAdapter);
        customFieldLinearLayout.setVisibility(View.GONE);
        customFieldEditingLinearLayout.setVisibility(View.VISIBLE);
        customFieldEditingLinearLayout.removeAllViews();
        LinearLayout.LayoutParams overTextParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        overTextParam.setMargins(8, 8, 8, 0);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        param.setMargins(16, 0, 16, 0);
        for (CustomFieldModel customFieldModel : mApiCallsController.getCustomFieldModelsMap().get("customFieldsOpportunity")){
            TextView tvCustomFieldName = new TextView(this);
            tvCustomFieldName.setLayoutParams(overTextParam);
            tvCustomFieldName.setGravity(Gravity.START);
            tvCustomFieldName.setText(customFieldModel.getName());
            customFieldEditingLinearLayout.addView(tvCustomFieldName);
            switch (customFieldModel.getType()){
                case multilist:
                    MultiSelectionSpinner multiSelectionSpinner = new MultiSelectionSpinner(this);
                    multiSelectionSpinner.setLayoutParams(param);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        multiSelectionSpinner.setElevation(4);
                    }
                    multiSelectionSpinner.setTitle(customFieldModel.getName());
                    multiSelectionSpinner.setItems(customFieldModel.getChoice());

                    ArrayList<Integer> selection = new ArrayList<>();
                    for (int i = 0; i < customFieldModel.getChoice().size(); i++){
                        String multiList = mOpportunityModel.getCustomFieldsMap().get(customFieldModel.getName());
                        if (multiList != null) {
                            String[] customFieldMulti = multiList.split(", ");
                            for (String aCustomFieldMulti : customFieldMulti) {
                                if (customFieldModel.getChoice().get(i).equals(aCustomFieldMulti)) {
                                    selection.add(i);
                                }
                            }
                        }
                    }
                    int[] sel = new int[selection.size()];
                    for (int i = 0; i < selection.size(); i++) {
                        sel[i] = selection.get(i);
                    }
                    multiSelectionSpinner.setSelection(sel);
                    multiSelectionSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
                        @Override
                        public void selectedIndices(List<Integer> indices) {

                        }

                        @Override
                        public void selectedStrings(List<String> strings) {
                            StringBuilder choices = new StringBuilder();
                            for (int i = 0; i < strings.size(); i++) {
                                choices.append(i == 0 ? "" : ", ");
                                choices.append(strings.get(i));
                            }
                            HashMap<String, String> newMap = mOpportunityModel.getCustomFieldsMap();
                            newMap.put(customFieldModel.getName(), choices.toString());
                            mOpportunityModel.setCustomFieldsMap(newMap);
                        }
                    });
                    customFieldEditingLinearLayout.addView(multiSelectionSpinner);
                    break;
                case list:
                    Spinner spinner = new Spinner(this);
                    spinner.setLayoutParams(param);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        spinner.setElevation(4);
                    }
                    ArrayList<String> array = new ArrayList<>();
                    array.add("Sélectionner");
                    array.addAll(customFieldModel.getChoice());
                    spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                            array));
                    for (int i = 0; i < customFieldModel.getChoice().size(); i++) {
                        if (mOpportunityModel.getCustomFieldsMap().get(customFieldModel.getName()) != null) {
                            spinner.setSelection(i + 1);
                        }
                    }
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if (position == 0){
                                HashMap<String, String> newMap = mOpportunityModel.getCustomFieldsMap();
                                newMap.remove(customFieldModel.getName());
                                mOpportunityModel.setCustomFieldsMap(newMap);
                            }else {
                                HashMap<String, String> newMap = mOpportunityModel.getCustomFieldsMap();
                                newMap.put(customFieldModel.getName(), customFieldModel.getChoice().get(position - 1));
                                mOpportunityModel.setCustomFieldsMap(newMap);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    customFieldEditingLinearLayout.addView(spinner);
                    break;
                case text:
                    EditText editText = new EditText(this);
                    editText.setLayoutParams(param);
                    editText.setHint(customFieldModel.getName());
                    editText.setText(mOpportunityModel.getCustomFieldsMap().get(customFieldModel.getName()));
                    HashMap<String, String> newMap = mOpportunityModel.getCustomFieldsMap();

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.toString().isEmpty()){
                                newMap.remove(customFieldModel.getName());
                            } else {
                                newMap.put(customFieldModel.getName(), editable.toString());
                            }
                        }
                    });
                    customFieldEditingLinearLayout.addView(editText);
                    mOpportunityModel.setCustomFieldsMap(newMap);
                    break;
            }
        }
    }
    private void pushAmount(String amount){
        HashMap<String, String> params = new HashMap<>();
        params.put("accountApiKey", ApiCallsController.API_KEY);
        params.put("idOpportunity", String.valueOf(mOpportunityModel.getId()));
        params.put("amount", amount);
        mApiCallsController.addOpportunity(this, params);
    }

    private void pushProbability(String probability){
        HashMap<String, String> params = new HashMap<>();
        params.put("accountApiKey", ApiCallsController.API_KEY);
        params.put("idOpportunity", String.valueOf(mOpportunityModel.getId()));
        params.put("probability", probability);
        mApiCallsController.addOpportunity(this, params);
    }

    private void pushDueDate(long date){
        HashMap<String, String> params = new HashMap<>();
        params.put("accountApiKey", ApiCallsController.API_KEY);
        params.put("idOpportunity", String.valueOf(mOpportunityModel.getId()));
        params.put("dueDateTS", String.valueOf(date));
        mApiCallsController.addOpportunity(this, params);
    }

    private void pushComments(String comment){
        HashMap<String, String> params = new HashMap<>();
        params.put("accountApiKey", ApiCallsController.API_KEY);
        params.put("idOpportunity", String.valueOf(mOpportunityModel.getId()));
        params.put("comments", comment);
        mApiCallsController.addOpportunity(this, params);
    }

    private void saveOpportunity(){
        HashMap<String, String> params = new HashMap<>();
        params.put("accountApiKey", ApiCallsController.API_KEY);
        params.put("idOpportunity", String.valueOf(mOpportunityModel.getId()));
        params.put("probability", textViewOpportunityProbability.getText().toString());
        params.put("amount", textViewOpportunityAmount.getText().toString());
        params.put("employeeIds", mEditingAdapter.getSelectedEmployeeIds());
        params.put("dueDateTS", String.valueOf(dueDate/1000));
        params.put("name", editTextOpportunityName.getText().toString());
        JSONObject customFieldsJson = new JSONObject();
        try {
        for (CustomFieldModel customFielModel : mApiCallsController.getCustomFieldModelsMap().get("customFieldsOpportunity")) {
            for (String customFieldName : mOpportunityModel.getCustomFieldsMap().keySet()){
                if (customFielModel.getName().equals(customFieldName)){
                    switch (customFielModel.getType()){
                        case text:
                            customFieldsJson.put(customFieldName, mOpportunityModel.getCustomFieldsMap().get(customFieldName));
                            break;
                        case list:
                            customFieldsJson.put(customFieldName, mOpportunityModel.getCustomFieldsMap().get(customFieldName));
                            break;
                        case multilist:
                            JSONArray multiCustomField = new JSONArray();
                            for (String splitList : mOpportunityModel.getCustomFieldsMap().get(customFieldName).split(", ")){
                                multiCustomField.put(splitList);
                            }
                            customFieldsJson.put(customFieldName, multiCustomField);
                            break;
                    }
                }
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSONJSON", customFieldsJson.toString());
        params.put("customFields", customFieldsJson.toString());
        mApiCallsController.addOpportunity(this, params);
    }

    @Override
    protected void onStop() {
        mApiCallsController.setDataReadyListener(null);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back(){
        mDetailController.setOpportunityModel(null);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            toolbar.setTitle(title);
        } else {
            toolbar.setTitle(title);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void OnDataReady(ArrayList<PipeModel> opportunityList) {
        fabCancel.setVisibility(View.GONE);
        fabEditing.setImageResource(android.R.drawable.ic_menu_edit);
        mOpportunityModel = mDetailController.getOpportunityById(mOpportunityModel.getId());
        isEditing = false;
        setFields();
    }

    @Override
    public void OnOpportinityPushed() {
        mApiCallsController.getOpportunities(DetailActivity.this);
    }
}
