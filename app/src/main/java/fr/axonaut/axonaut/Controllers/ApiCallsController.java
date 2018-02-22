package fr.axonaut.axonaut.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.axonaut.axonaut.Models.ClientModel;
import fr.axonaut.axonaut.Models.CompanyCategorieModel;
import fr.axonaut.axonaut.Models.CompanyForListModel;
import fr.axonaut.axonaut.Models.CompanyModel;
import fr.axonaut.axonaut.Models.CustomFieldModel;
import fr.axonaut.axonaut.Models.EmployeeModel;
import fr.axonaut.axonaut.Models.EventModel;
import fr.axonaut.axonaut.Models.OpportunityModel;
import fr.axonaut.axonaut.Models.OpportunityStepModel;
import fr.axonaut.axonaut.Models.PipeModel;
import fr.axonaut.axonaut.Models.PipeStepModel;
import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;

/**
 * Created by Kelian on 07/02/2018.
 */

public class ApiCallsController {

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());
    private ArrayList<PipeModel> mArrayPipes = new ArrayList<>();
    private ArrayList<OpportunityModel> mArrayOpportunities = new ArrayList<>();
    private ArrayList<CompanyForListModel> mArrayCompanies = new ArrayList<>();
    private HashMap<String, ArrayList<CustomFieldModel>> mCustomFieldModelsMap = new HashMap<>();
    private ArrayList<String> mAutoCompleteList = new ArrayList<>();

    public static String API_KEY = "abd11e11b4456aabdfeb662ec917bb0a";

    private static volatile ApiCallsController sInstance = null;

    private DataReadyListener mDataReadyListener;
    private SplashDataLoadedListener mSplashDataLoadedListener;

    boolean contactLoaded = false;
    boolean customFieldsLoaded = false;
    boolean opportunitiesLoaded = false;

    private ApiCallsController(){
        // Prevent from the reflection API.
        if(sInstance != null) {
            throw new RuntimeException("Use getInstance() to get the single instance of this class.");
        }
    }

    public static ApiCallsController getInstance(){
        // Check for the first time
        if(sInstance == null) {
            // Check for the second time
            synchronized (ApiCallsController.class) {
                // If no Instance available create new One
                if(sInstance == null) {
                    sInstance = new ApiCallsController();
                }
            }
        }
        return sInstance;
    }

    //async
    public void getContactList(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://axonaut.com/api/post/company/list";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    JSONObject httpResponse = null;
                    try {
                        httpResponse = new JSONObject(response);
                        if (httpResponse.getString("status").equals("OK")) {
                            ArrayList<CompanyForListModel> arrayCompanies = new ArrayList<>();
                            JSONArray arrayCompaniesJson = httpResponse.getJSONArray("arrayCompanies");
                            for (int index = 0; index < arrayCompaniesJson.length(); index++) {
                                JSONObject companyJsonObject = arrayCompaniesJson.getJSONObject(index);
                                CompanyForListModel companyModel = new CompanyForListModel();
                                StringBuilder stringForAutoComplete = new StringBuilder("");
                                companyModel.setCompanyId(companyJsonObject.getInt("companyId"));
                                companyModel.setName(companyJsonObject.getString("name"));
                                stringForAutoComplete.append(companyModel.getName());
                                companyModel.setAddressStreet(companyJsonObject.getString("addressStreet"));
                                companyModel.setAddressZipCode(companyJsonObject.getString("addressZipCode"));
                                companyModel.setAddressTown(companyJsonObject.getString("addressTown"));

                                JSONArray employeesListJson = companyJsonObject.getJSONArray("employees");
                                ArrayList<ClientModel> employeesList = new ArrayList<>();

                                for (int i = 0; i < employeesListJson.length(); i++) {
                                    stringForAutoComplete.append(", ");
                                    JSONObject opportunityStepJsonObject = employeesListJson.getJSONObject(i);
                                    ClientModel opportunityStepModel = new ClientModel();

                                    opportunityStepModel.setFirstname(opportunityStepJsonObject.getString("firstname"));
                                    stringForAutoComplete.append(opportunityStepModel.getFirstname());
                                    opportunityStepModel.setLastname(opportunityStepJsonObject.getString("lastname"));
                                    stringForAutoComplete.append(" ");
                                    stringForAutoComplete.append(opportunityStepModel.getLastname());
                                    opportunityStepModel.setEmailAddress(opportunityStepJsonObject.getString("emailAddress"));
                                    opportunityStepModel.setCellPhoneNumber(opportunityStepJsonObject.getString("cellphoneNumber"));
                                    opportunityStepModel.setJob(opportunityStepJsonObject.getString("job"));
                                    opportunityStepModel.setId(opportunityStepJsonObject.getInt("id"));

                                    employeesList.add(opportunityStepModel);
                                }
                                companyModel.setEmployees(employeesList);
                                companyModel.setNbOpportunities(companyJsonObject.getInt("nbOpportunities"));
                                arrayCompanies.add(companyModel);
                                mAutoCompleteList.add(stringForAutoComplete.toString());
                            }
                            mArrayCompanies = arrayCompanies;
                            contactLoaded = true;
                            passSplash();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // error
                    Log.d(TAG, "error: ");
                }
        ) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("accountApiKey", API_KEY);

                return params;
            }
        };
        queue.add(postRequest);


    }

    //async
    public void getOpportunities(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://axonaut.com/api/post/opportunity/list/inProgress";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    JSONObject httpResponse = null;
                    try {
                        httpResponse = new JSONObject(response);
                        if (httpResponse.getString("status").equals("OK")) {
                            ArrayList<OpportunityModel> arrayOpportunities = new ArrayList<>();
                            JSONArray arraysOpportunities = httpResponse.getJSONArray("arrayOpportunities");
                            for (int index = 0; index < arraysOpportunities.length(); index++) {
                                JSONObject opportunityJsonObject = arraysOpportunities.getJSONObject(index);
                                OpportunityModel opportunityModel = new OpportunityModel();

                                opportunityModel.setId(opportunityJsonObject.getInt("id"));
                                opportunityModel.setName(opportunityJsonObject.getString("name"));

                                JSONArray opportunityStepHistory = opportunityJsonObject.getJSONArray("opportunityStepHistory");
                                ArrayList<OpportunityStepModel> opportunityStepList = new ArrayList<>();

                                for (int i = 0; i < opportunityStepHistory.length(); i++) {
                                    JSONObject opportunityStepJsonObject = opportunityStepHistory.getJSONObject(i);

                                    OpportunityStepModel opportunityStepModel = new OpportunityStepModel();
                                    PipeStepModel pipeStepModel = new PipeStepModel();

                                    opportunityStepModel.setDateStep(opportunityStepJsonObject.getString("dateStep"));

                                    JSONObject pipeStepJsonObject = opportunityStepJsonObject.getJSONObject("pipeStep");
                                    pipeStepModel.setId(pipeStepJsonObject.getInt("id"));
                                    pipeStepModel.setName(pipeStepJsonObject.getString("name"));

                                    opportunityStepModel.setPipeStep(pipeStepModel);
                                    opportunityStepList.add(opportunityStepModel);
                                }

                                opportunityModel.setOpportunityStepHistory(opportunityStepList);
                                opportunityModel.setComments(opportunityJsonObject.getString("comments"));
                                opportunityModel.setAmount(opportunityJsonObject.getInt("amount"));
                                opportunityModel.setProbability(opportunityJsonObject.getInt("probability"));
                                opportunityModel.setDueDate(opportunityJsonObject.getString("dueDate"));
                                opportunityModel.setDateEnd(opportunityJsonObject.getString("dateEnd"));
                                //opportunityModel.setWin(opportunityJsonObject.getBoolean("isWin"));
                                //opportunityModel.setArchived(opportunityJsonObject.getBoolean("isArchived"));
                                opportunityModel.setUserName(opportunityJsonObject.getString("userName"));
                                opportunityModel.setPipeName(opportunityJsonObject.getString("pipeName"));

                                JSONObject companyJson = opportunityJsonObject.getJSONObject("company");
                                CompanyModel companyModel = new CompanyModel();
                                companyModel.setId(companyJson.getInt("id"));
                                companyModel.setName(companyJson.getString("name"));

                                JSONArray companyNaturesJson = companyJson.getJSONArray("companyNatures");
                                ArrayList<CompanyCategorieModel> companyNaturesList = new ArrayList<>();

                                for (int i = 0; i < companyNaturesJson.length(); i++) {
                                    JSONObject companyCategorieJson = companyNaturesJson.getJSONObject(i);

                                    CompanyCategorieModel companyCategorieModel = new CompanyCategorieModel();
                                    companyCategorieModel.setId(companyCategorieJson.getInt("id"));
                                    companyCategorieModel.setName(companyCategorieJson.getString("name"));

                                    companyNaturesList.add(companyCategorieModel);
                                }
                                companyModel.setCompanyNatures(companyNaturesList);

                                opportunityModel.setCompany(companyModel);

                                JSONArray employeesJson = opportunityJsonObject.getJSONArray("employees");
                                ArrayList<EmployeeModel> employeesList = new ArrayList<>();

                                for (int i = 0; i < employeesJson.length(); i++) {
                                    JSONObject employeeJson = employeesJson.getJSONObject(i);

                                    EmployeeModel employeeModel = new EmployeeModel();
                                    employeeModel.setId(employeeJson.getInt("id"));
                                    employeeModel.setEmail(employeeJson.getString("email"));
                                    employeeModel.setFullname(employeeJson.getString("fullname"));
                                    employeeModel.setPhoneNumber(employeeJson.getString("phoneNumber"));
                                    employeeModel.setCellphoneNumber(employeeJson.getString("cellphoneNumber"));

                                    employeesList.add(employeeModel);
                                }
                                opportunityModel.setEmployees(employeesList);

                                JSONArray eventArrayJson = opportunityJsonObject.getJSONArray("events");
                                ArrayList<EventModel> eventList = new ArrayList<>();

                                for (int i = 0; i < eventArrayJson.length(); i++) {
                                    JSONObject eventJson = eventArrayJson.getJSONObject(i);
                                    EventModel eventModel = new EventModel();

                                    eventModel.setDate(eventJson.getString("date"));
                                    eventModel.setFlow(eventJson.getInt("flow"));
                                    eventModel.setDuration(eventJson.getString("duration"));
                                    eventModel.setType(eventJson.getString("type"));
                                    eventModel.setContent(eventJson.getString("content"));

                                    JSONArray userArrayJson = eventJson.getJSONArray("users");
                                    ArrayList<EmployeeModel> userList = new ArrayList<>();

                                    for (int j = 0; j < userArrayJson.length(); j++) {
                                        JSONObject userJson = userArrayJson.getJSONObject(j);
                                        EmployeeModel userModel = new EmployeeModel();
                                        userModel.setId(userJson.getInt("id"));
                                        userModel.setEmail(userJson.getString("email"));
                                        userModel.setFullname(userJson.getString("fullname"));
                                        userList.add(userModel);
                                    }

                                    eventModel.setUsers(userList);

                                    JSONArray employeeArrayJson = eventJson.getJSONArray("employees");
                                    ArrayList<EmployeeModel> employeeList = new ArrayList<>();

                                    for (int j = 0; j < employeeArrayJson.length(); j++) {
                                        JSONObject employeeJson = employeeArrayJson.getJSONObject(j);
                                        EmployeeModel employeeModel = new EmployeeModel();
                                        employeeModel.setId(employeeJson.getInt("id"));
                                        employeeModel.setEmail(employeeJson.getString("email"));
                                        employeeModel.setFullname(employeeJson.getString("fullname"));
                                        employeeList.add(employeeModel);
                                    }

                                    eventModel.setEmployees(employeeList);
                                    JSONArray unknownOtherEmailsRecipientsArrayJson = eventJson.getJSONArray("employees");
                                    ArrayList<String> unknownOtherEmailsRecipientsArray = new ArrayList<>();

                                    for (int j = 0; j < unknownOtherEmailsRecipientsArrayJson.length(); j++) {
                                        unknownOtherEmailsRecipientsArray.add(unknownOtherEmailsRecipientsArrayJson.getString(j));

                                    }
                                    eventModel.setUnknownOtherEmailsRecipients(unknownOtherEmailsRecipientsArray);

                                    eventList.add(eventModel);
                                }
                                opportunityModel.setEvents(eventList);

                                if (!opportunityJsonObject.isNull("customFields")){
                                    JSONObject customFieldsJson = opportunityJsonObject.getJSONObject("customFields");
                                    HashMap<String, String> customFieldsMap = new HashMap<>();
                                    for (CustomFieldModel customFieldModel : mCustomFieldModelsMap.get("customFieldsOpportunity")){
                                        if (!customFieldsJson.isNull(customFieldModel.getName())) {
                                            switch (customFieldModel.getType()) {
                                                case text:
                                                    customFieldsMap.put(customFieldModel.getName(), customFieldsJson.getString(customFieldModel.getName()));
                                                    break;
                                                case list:
                                                    String listResult = customFieldsJson.getString(customFieldModel.getName());
                                                    if (!listResult.equals("")) {
                                                        try {
                                                            customFieldsMap.put(customFieldModel.getName(), listResult.split("\"")[1]);
                                                        }catch (ArrayIndexOutOfBoundsException e){
                                                            customFieldsMap.put(customFieldModel.getName(), customFieldsJson.getString(customFieldModel.getName()));
                                                        }
                                                    }
                                                    break;
                                                case multilist:
                                                    JSONArray customFieldList = customFieldsJson.getJSONArray(customFieldModel.getName());
                                                    StringBuilder multiList = new StringBuilder();
                                                    for (int i = 0; i < customFieldList.length(); i++) {
                                                        multiList.append(i == 0 ? "" : ", ");
                                                        multiList.append(customFieldList.get(i));
                                                    }
                                                    customFieldsMap.put(customFieldModel.getName(), multiList.toString());
                                                    break;
                                            }
                                        }
                                    }
                                    opportunityModel.setCustomFieldsMap(customFieldsMap);
                                } else {
                                    opportunityModel.setCustomFieldsMap(new HashMap<>());
                                }
                                arrayOpportunities.add(opportunityModel);
                            }
                            mArrayOpportunities = arrayOpportunities;
                            getPipes(context);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // error
                    Log.e(TAG, "error: ");
                }
        ) {
            @Override
            public Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("accountApiKey", API_KEY);

                return params;
            }
        };
        queue.add(postRequest);


    }

    public void getPipes(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://axonaut.com/api/post/opportunity/pipe/list";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject httpResponse = null;
                    try {
                        httpResponse = new JSONObject(response);
                        if (httpResponse.getString("status").equals("OK")) {
                            ArrayList<PipeModel> arrayPipes = new ArrayList<>();
                            JSONArray arrayPipesJson = httpResponse.getJSONArray("pipes");
                            for (int index = 0; index < arrayPipesJson.length(); index++) {
                                JSONObject pipeJson = arrayPipesJson.getJSONObject(index);
                                PipeModel pipeModel = new PipeModel();

                                pipeModel.setId(pipeJson.getInt("id"));
                                pipeModel.setName(pipeJson.getString("name"));

                                JSONArray pipeStepsArrayJson = pipeJson.getJSONArray("pipeSteps");
                                ArrayList<PipeStepWithOpportunityModel> pipeStepsArray = new ArrayList<>();

                                for (int i = 0; i < pipeStepsArrayJson.length(); i++) {
                                    JSONObject pipeStepJson = pipeStepsArrayJson.getJSONObject(i);
                                    PipeStepWithOpportunityModel pipeStep = new PipeStepWithOpportunityModel();
                                    pipeStep.setId(pipeStepJson.getInt("id"));
                                    pipeStep.setName(pipeStepJson.getString("name"));
                                    pipeStep.setOrderNumber(pipeStepJson.getInt("orderNumber"));
                                    pipeStep.setColorHex(pipeStepJson.getString("color"));
                                    pipeStepsArray.add(pipeStep);
                                }
                                pipeModel.setPipeSteps(pipeStepsArray);
                                arrayPipes.add(pipeModel);
                            }
                            mArrayPipes = arrayPipes;
                            putOpportunitiesInPipes();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // error
                    Log.d(TAG, "error: ");
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("accountApiKey", API_KEY);

                return params;
            }
        };
        queue.add(postRequest);

    }

    public void addOpportunity(Context context, HashMap<String, String> map){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://axonaut.com/api/post/opportunity/save";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getString("status").equals("OK")){
                            if (mDataReadyListener != null) {
                                mDataReadyListener.OnOpportinityPushed();
                            }
                        } else {
                            Toast.makeText(context, jsonResponse.getString("status"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    // error
                    Log.d(TAG, "error: ");
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.putAll(map);

                return params;
            }
        };
        queue.add(postRequest);

    }
    
    public void putOpportunitiesInPipes(){

        for (PipeModel pipeModel : mArrayPipes) {
            for (OpportunityModel opportunityModel : mArrayOpportunities) {
                if (pipeModel.getName().equals(opportunityModel.getPipeName())){
                    for (PipeStepWithOpportunityModel pipeStep : pipeModel.getPipeSteps()){
                        if (opportunityModel.getOpportunityStepHistory().get(0).getPipeStep().getId() == pipeStep.getId()){
                            if (pipeStep.getOpportunityList()!= null) {
                                ArrayList<OpportunityModel> opportunityList = pipeStep.getOpportunityList();
                                opportunityList.add(opportunityList.size(), opportunityModel);
                                pipeStep.setOpportunityList(new ArrayList<>(opportunityList));
                            } else {
                                ArrayList<OpportunityModel> opportunityList = new ArrayList<>();
                                opportunityList.add(opportunityList.size(), opportunityModel);
                                pipeStep.setOpportunityList(new ArrayList<>(opportunityList));
                            }
                        }
                    }
                }
            }
        }
        if (mDataReadyListener != null) {
            mDataReadyListener.OnDataReady(mArrayPipes);
        }
        opportunitiesLoaded = true;
        passSplash();
    }

    //async
    public void getCustomFields(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://axonaut.com/api/post/customfield/list";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getString("status").equals("OK")){
                            HashMap<JSONArray, String> customFieldsArray = new HashMap<>();
                            customFieldsArray.put(jsonResponse.getJSONArray("customFieldsCompany"), "customFieldsCompany");
                            customFieldsArray.put(jsonResponse.getJSONArray("customFieldsEmployee"), "customFieldsEmployee");
                            customFieldsArray.put(jsonResponse.getJSONArray("customFieldsOpportunity"), "customFieldsOpportunity");
                            HashMap<String, ArrayList<CustomFieldModel>> customFieldModelsArray = new HashMap<>();
                            for (JSONArray customField : customFieldsArray.keySet()) {
                                ArrayList<CustomFieldModel> customFieldModelArrayList = new ArrayList<>();
                                for (int i = 0; i < customField.length(); i++) {
                                    JSONObject customFieldObject = customField.getJSONObject(i);
                                    CustomFieldModel customFieldModel = new CustomFieldModel();
                                    customFieldModel.setName(customFieldObject.getString("name"));
                                    switch (customFieldObject.getString("type")){
                                        case "list":
                                            customFieldModel.setType(CustomFieldModel.Type.list);
                                            JSONArray choicesJsonList = customFieldObject.getJSONArray("choice");
                                            ArrayList<String> choicesList = new ArrayList<>();
                                            for (int j = 0; j < choicesJsonList.length(); j++) {
                                                choicesList.add(choicesJsonList.getString(j));
                                            }
                                            customFieldModel.setChoice(choicesList);
                                            break;
                                        case "multilist":
                                            customFieldModel.setType(CustomFieldModel.Type.multilist);
                                            JSONArray choicesJson = customFieldObject.getJSONArray("choice");
                                            ArrayList<String> choices = new ArrayList<>();
                                            for (int j = 0; j < choicesJson.length(); j++) {
                                                choices.add(choicesJson.getString(j));
                                            }
                                            customFieldModel.setChoice(choices);
                                            break;
                                        case "text":
                                            customFieldModel.setType(CustomFieldModel.Type.text);
                                            break;
                                    }
                                    customFieldModelArrayList.add(customFieldModel);
                                }
                                customFieldModelsArray.put(customFieldsArray.get(customField), customFieldModelArrayList);
                                mCustomFieldModelsMap = customFieldModelsArray;
                                customFieldsLoaded = true;
                                passSplash();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    // error
                    Log.d(TAG, "error: ");
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("accountApiKey", API_KEY);

                return params;
            }
        };
        queue.add(postRequest);

    }

    public ArrayList<String> getAutoCompleteList() {
        return mAutoCompleteList;
    }

    public ArrayList<CompanyForListModel> getArrayCompanies() {
        return mArrayCompanies;
    }

    public ArrayList<PipeModel> getPipeArray() {
        return mArrayPipes;
    }

    public HashMap<String, ArrayList<CustomFieldModel>> getCustomFieldModelsMap() {
        return mCustomFieldModelsMap;
    }

    public void setDataReadyListener(DataReadyListener listener) {
        this.mDataReadyListener = listener;
    }

    public interface DataReadyListener{
        void OnDataReady(ArrayList<PipeModel> opportunityList);
        void OnOpportinityPushed();
    }

    public void setSplashDataLoadedListener(SplashDataLoadedListener splashDataLoadedListener) {
        this.mSplashDataLoadedListener = splashDataLoadedListener;
    }

    public interface SplashDataLoadedListener {
        void OnDataReady();
    }

    public ArrayList<OpportunityModel> getArrayOpportunities() {
        return mArrayOpportunities;
    }

    private void passSplash(){
        if (mSplashDataLoadedListener != null) {
            if (contactLoaded && customFieldsLoaded && opportunitiesLoaded)
                mSplashDataLoadedListener.OnDataReady();
        }
    }
}
