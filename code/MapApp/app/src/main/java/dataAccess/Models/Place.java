package dataAccess.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Place implements Serializable {
    double latitude;
    double longitude;
    String name;
    String description;

    @SerializedName("country_name")
    String countryName;

    public Place(double latitude, double longitude, String name, String description, String countryName) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.countryName = countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
