package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

/**
 * Created by Kelian on 07/02/2018.
 */

public class PipeStepWithOpportunityModel {
    private int id;
    private String name;
    private String colorHex;
    private int orderNumber;
    private ArrayList<OpportunityModel> opportunityList;

    public PipeStepWithOpportunityModel() {
    }

    public PipeStepWithOpportunityModel(int id, String name, String colorHex, int orderNumber, ArrayList<OpportunityModel> opportunityList) {
        this.id = id;
        this.name = name;
        this.colorHex = colorHex;
        this.orderNumber = orderNumber;
        this.opportunityList = opportunityList;
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public ArrayList<OpportunityModel> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(ArrayList<OpportunityModel> opportunityList) {
        this.opportunityList = opportunityList;
    }

    @Override
    public String toString() {
        return name;
    }
}
