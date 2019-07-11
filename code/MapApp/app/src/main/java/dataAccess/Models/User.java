package dataAccess.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String id;
    private String name;
    private String password;
    public ArrayList<Place> places;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.places = new ArrayList<>();
    }

    public void addPlace(Place place){
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

    public ArrayList<Place> getPlaces() { return places; }

    public void setPlaces(ArrayList<Place> places) { this.places = places; }
}
