package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.CompanyCategorieModel;

/**
 * Created by Kelian on 07/02/2018.
 */

public class CompanyModel {
    private int id;
    private String name;
    private ArrayList<CompanyCategorieModel> companyNatures;

    public CompanyModel() {
    }

    public CompanyModel(int id, String name, ArrayList<CompanyCategorieModel> companyNatures) {
        this.id = id;
        this.name = name;
        this.companyNatures = companyNatures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CompanyCategorieModel> getCompanyNatures() {
        return companyNatures;
    }

    public void setCompanyNatures(ArrayList<CompanyCategorieModel> companyNatures) {
        this.companyNatures = companyNatures;
    }
}
