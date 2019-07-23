package dataAccess.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Administrator implements Serializable {

    String id;
    String name;
    String password;
    @SerializedName("limit_date") String limitDate;
    ArrayList<String> countries;

    public Administrator(String id, String name, String password, String limitDate) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.limitDate = limitDate;
        this.countries = new ArrayList<>();
    }

    public Administrator(Administrator other, ArrayList<String> otherCountries){

        this.id = other.id;
        this.name = other.name;
        this.password = other.password;
        this.limitDate = other.limitDate;
        this.countries = otherCountries;
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

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }
}
