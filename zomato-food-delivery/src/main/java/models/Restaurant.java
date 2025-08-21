package models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private int restuarantId;
    private static int nextRestaurantId = 0;
    List<MenuItems>menu=new ArrayList<>();

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
        this.restuarantId = ++nextRestaurantId;
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

    public List<MenuItems> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItems> menu) {
        this.menu = menu;
    }

    public void addMenuItem(MenuItems item){
        menu.add(item);
    }
}
