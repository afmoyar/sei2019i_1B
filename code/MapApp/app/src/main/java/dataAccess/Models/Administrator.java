package dataAccess.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Administrator implements Serializable {

    String id;
    String name;
    String password;
    String limitDate;
    ArrayList<String> places;

    public Administrator(String id, String name, String password, String limitDate) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.limitDate = limitDate;
        this.places = new ArrayList<>();
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

    public void setPassword(String Password) {
        password = Password;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }
}
