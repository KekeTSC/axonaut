package fr.axonaut.axonaut.Models;

/**
 * Created by Kelian on 08/02/2018.
 */

public class PagerModel {
    String id;
    String title;
    int viewLayout;

    public PagerModel(String id, String title, int viewLayout) {
        this.id = id;
        this.title = title;
        this.viewLayout = viewLayout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewLayout() {
        return viewLayout;
    }

    public void setViewLayout(int viewLayout) {
        this.viewLayout = viewLayout;
    }
}
