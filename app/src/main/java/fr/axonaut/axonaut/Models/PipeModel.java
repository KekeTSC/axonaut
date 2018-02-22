package fr.axonaut.axonaut.Models;

import java.util.ArrayList;

/**
 * Created by Kelian on 07/02/2018.
 */

public class PipeModel {
    private int id;
    private String name;
    private ArrayList<PipeStepWithOpportunityModel> pipeSteps;

    public PipeModel() {
    }

    public PipeModel(int id, String name, ArrayList<PipeStepWithOpportunityModel> pipeSteps) {
        this.id = id;
        this.name = name;
        this.pipeSteps = pipeSteps;
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

    public ArrayList<PipeStepWithOpportunityModel> getPipeSteps() {
        return pipeSteps;
    }

    public void setPipeSteps(ArrayList<PipeStepWithOpportunityModel> pipeSteps) {
        this.pipeSteps = pipeSteps;
    }
}
