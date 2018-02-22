package fr.axonaut.axonaut.Models;

/**
 * Created by Kelian on 07/02/2018.
 */

public class EmployeeModel {
    private int id;
    private String email;
    private String fullname;
    private String phoneNumber;
    private String cellphoneNumber;

    public EmployeeModel() {
    }

    public EmployeeModel(int id, String email, String fullname, String phoneNumber, String cellphoneNumber) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.cellphoneNumber = cellphoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    @Override
    public String toString() {
        return fullname;
    }
}
