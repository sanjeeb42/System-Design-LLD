package models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private int restuarantId;
    List<MenuItems>menu=new ArrayList<>();

    public Restaurant(String name, String location, int restuarantId, List<MenuItems> menu) {
        this.name = name;
        this.location = location;
        this.restuarantId = restuarantId;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRestuarantId() {
        return restuarantId;
    }

    public void setRestuarantId(int restuarantId) {
        this.restuarantId = restuarantId;
    }

    public List<MenuItems> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItems> menu) {
        this.menu = menu;
    }
}
