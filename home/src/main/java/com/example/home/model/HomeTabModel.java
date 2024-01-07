package com.example.home.model;

import androidx.compose.ui.graphics.vector.ImageVector;

public class HomeTabModel {

    public HomeTabModel(String id, String description, ImageVector selectedIcon, ImageVector unSelectedIcon) {
        this.id = id;
        this.description = description;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    private String id;
    private String description;
    private ImageVector selectedIcon;
    private ImageVector unSelectedIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageVector getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(ImageVector selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public ImageVector getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(ImageVector unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

}
