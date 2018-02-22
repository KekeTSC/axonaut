package fr.axonaut.axonaut.Models;

import java.util.ArrayList;
import java.util.HashMap;


public class OpportunityModel{
    private int id;
    private String name;
    private ArrayList<OpportunityStepModel> opportunityStepHistory;
    private String comments;
    private int amount;
    private int probability;
    private String dueDate;
    private String dateEnd;
    private boolean isWin;
    private boolean isArchived;
    private String userName;
    private String pipeName;
    private CompanyModel company;
    private ArrayList<EmployeeModel> employees;
    private ArrayList<EventModel> events;
    private HashMap<String, String> customFieldsMap;

    public OpportunityModel() {
    }

    public OpportunityModel(int id, String name, ArrayList<OpportunityStepModel> opportunityStepHistory, String comments, int amount, int probability, String dueDate, String dateEnd, boolean isWin, boolean isArchived, String userName, String pipeName, CompanyModel company, ArrayList<EmployeeModel> employees, ArrayList<EventModel> events, HashMap<String, String> customFieldsMap) {
        this.id = id;
        this.name = name;
        this.opportunityStepHistory = opportunityStepHistory;
        this.comments = comments;
        this.amount = amount;
        this.probability = probability;
        this.dueDate = dueDate;
        this.dateEnd = dateEnd;
        this.isWin = isWin;
        this.isArchived = isArchived;
        this.userName = userName;
        this.pipeName = pipeName;
        this.company = company;
        this.employees = employees;
        this.events = events;
        this.customFieldsMap = customFieldsMap;
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

    public ArrayList<OpportunityStepModel> getOpportunityStepHistory() {
        return opportunityStepHistory;
    }

    public void setOpportunityStepHistory(ArrayList<OpportunityStepModel> opportunityStepHistory) {
        this.opportunityStepHistory = opportunityStepHistory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPipeName() {
        return pipeName;
    }

    public void setPipeName(String pipeName) {
        this.pipeName = pipeName;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public ArrayList<EmployeeModel> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<EmployeeModel> employees) {
        this.employees = employees;
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
    }

    public HashMap<String, String> getCustomFieldsMap() {
        return customFieldsMap;
    }

    public void setCustomFieldsMap(HashMap<String, String> customFieldsMap) {
        this.customFieldsMap = customFieldsMap;
    }
}
