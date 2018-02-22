package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

/**
 * Created by Kelian on 08/02/2018.
 */

public class CompanyForListModel {
    private int companyId;
    private String name;
    private String addressStreet;
    private String addressZipCode;
    private String addressTown;
    private ArrayList<ClientModel> employees;
    private int nbOpportunities;

    public CompanyForListModel() {
    }

    public CompanyForListModel(int companyId, String name, String addressStreet, String addressZipCode, String addressTown, ArrayList<ClientModel> employees, int nbOpportunities) {
        this.companyId = companyId;
        this.name = name;
        this.addressStreet = addressStreet;
        this.addressZipCode = addressZipCode;
        this.addressTown = addressTown;
        this.employees = employees;
        this.nbOpportunities = nbOpportunities;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    public ArrayList<ClientModel> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<ClientModel> employees) {
        this.employees = employees;
    }

    public int getNbOpportunities() {
        return nbOpportunities;
    }

    public void setNbOpportunities(int nbOpportunities) {
        this.nbOpportunities = nbOpportunities;
    }

    @Override
    public String toString() {
        StringBuilder stringForAutoComplete = new StringBuilder("");
        stringForAutoComplete.append(getName());
        for (ClientModel employee: employees) {
            stringForAutoComplete.append(" ,");
            stringForAutoComplete.append(employee.toString());
        }
        return stringForAutoComplete.toString();
    }
}
