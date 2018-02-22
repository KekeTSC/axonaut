package fr.axonaut.axonaut.Models;

public class OpportunityStepModel {
    private String dateStep;
    private PipeStepModel pipeStep;

    public OpportunityStepModel() {
    }

    public OpportunityStepModel(String dateStep, PipeStepModel pipeStep) {
        this.dateStep = dateStep;
        this.pipeStep = pipeStep;
    }

    public String getDateStep() {
        return dateStep;
    }

    public void setDateStep(String dateStep) {
        this.dateStep = dateStep;
    }

    public PipeStepModel getPipeStep() {
        return pipeStep;
    }

    public void setPipeStep(PipeStepModel pipeStep) {
        this.pipeStep = pipeStep;
    }
}
