package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.EmployeeModel;

/**
 * Created by Kelian on 07/02/2018.
 */

public class EventModel {
    private String date;
    private int flow;
    private String  duration;
    private String type;
    private String content;
    private ArrayList<EmployeeModel> users;
    private ArrayList<EmployeeModel> employees;
    private ArrayList<String> unknownOtherEmailsRecipients;

    public EventModel() {
    }

    public EventModel(String date, int flow, String duration, String type, String content, ArrayList<EmployeeModel> users, ArrayList<EmployeeModel> employees, ArrayList<String> unknownOtherEmailsRecipients) {
        this.date = date;
        this.flow = flow;
        this.duration = duration;
        this.type = type;
        this.content = content;
        this.users = users;
        this.employees = employees;
        this.unknownOtherEmailsRecipients = unknownOtherEmailsRecipients;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<EmployeeModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<EmployeeModel> users) {
        this.users = users;
    }

    public ArrayList<EmployeeModel> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<EmployeeModel> employees) {
        this.employees = employees;
    }

    public ArrayList<String> getUnknownOtherEmailsRecipients() {
        return unknownOtherEmailsRecipients;
    }

    public void setUnknownOtherEmailsRecipients(ArrayList<String> unknownOtherEmailsRecipients) {
        this.unknownOtherEmailsRecipients = unknownOtherEmailsRecipients;
    }
}
