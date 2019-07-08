package dataAccess.Models;

import java.util.ArrayList;

public class User {
    String id;
    String name;
    String password;
    ArrayList<Place> places;


    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.places = new ArrayList<Place>();
    }

    public void addPlaces(Place place){
        places.add(place);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
