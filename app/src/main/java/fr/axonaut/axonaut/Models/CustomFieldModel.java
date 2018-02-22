package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

/**
 * Created by Kelian on 13/02/2018.
 */

public class CustomFieldModel {
    private String name;
    private Type type;
    private ArrayList<String> choice;

    public CustomFieldModel() {
    }

    public CustomFieldModel(String name, Type type, ArrayList<String> choice) {
        this.name = name;
        this.type = type;
        this.choice = choice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<String> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<String> choice) {
        this.choice = choice;
    }

    public enum Type {
        list,
        multilist,
        text;
    }
}

