package fr.axonaut.axonaut.Models;

/**
 * Created by Kelian on 16/02/2018.
 */

public class ClientEditingModel {
    private ClientModel clientModel;
    private boolean isSelected;

    public ClientEditingModel() {
    }

    public ClientEditingModel(ClientModel clientModel, boolean isSelected) {
        this.clientModel = clientModel;
        this.isSelected = isSelected;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
