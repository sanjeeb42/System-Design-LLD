package managers;

import models.Restaurant;

import java.util.ArrayList;
import java.util.List;

// Ye singleTon instance hoga
public class RestuarantManager {
    private List<Restaurant>restaurants=new ArrayList<>();

    private static volatile RestuarantManager instance = null;

    private RestuarantManager(){}

    public static RestuarantManager getInstance() {
        if (instance == null) {
            synchronized (RestuarantManager.class) {
                if (instance == null) {
                    instance = new RestuarantManager();
                }
            }
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    public List<Restaurant> searchByLocation(String location) {
        List<Restaurant> result = new ArrayList<>();
        location = location.toLowerCase();
        for (Restaurant r : restaurants) {
            String rl = r.getLocation().toLowerCase();
            if (rl.equals(location)) {
                result.add(r);
            }
        }
        return result;
    }
}
