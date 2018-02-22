package fr.axonaut.axonaut.Models;

/**
 * Created by Kelian on 07/02/2018.
 */

public class PipeStepModel {
    private int id;
    private String name;

    public PipeStepModel() {
    }

    public PipeStepModel(int id, String name) {
        this.id = id;
        this.name = name;
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
}
