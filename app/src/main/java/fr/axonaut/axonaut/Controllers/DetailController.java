package fr.axonaut.axonaut.Controllers;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.ClientModel;
import fr.axonaut.axonaut.Models.CompanyForListModel;
import fr.axonaut.axonaut.Models.CompanyModel;
import fr.axonaut.axonaut.Models.EmployeeModel;
import fr.axonaut.axonaut.Models.OpportunityModel;

/**
 * Created by Kelian on 15/02/2018.
 */

public class DetailController {

    private static DetailController sInstance;
    private OpportunityModel mOpportunityModel;
    private ApiCallsController mApiController;


    public static DetailController getInstance() {
        if (sInstance == null) {
            sInstance = new DetailController();
        }
        return sInstance;
    }

    private DetailController() {
        mApiController = ApiCallsController.getInstance();
    }

    public OpportunityModel getOpportunityModel() {
        return mOpportunityModel;
    }

    public void setOpportunityModel(OpportunityModel mOpportunityModel) {
        this.mOpportunityModel = mOpportunityModel;
    }

    public CompanyForListModel getCompanyById(int id){
        CompanyForListModel returnCompany = new CompanyForListModel();
        for (CompanyForListModel companyModel : mApiController.getArrayCompanies()){
            if (companyModel.getCompanyId() == id){
                returnCompany = companyModel;
            }
        }
        return returnCompany;
    }

    public OpportunityModel getOpportunityById(int id){
        OpportunityModel returnOpportunity = new OpportunityModel();
        for (OpportunityModel opportunityModel : mApiController.getArrayOpportunities()){
            if (opportunityModel.getId() == id){
                returnOpportunity = opportunityModel;
            }
        }
        return returnOpportunity;
    }
}
