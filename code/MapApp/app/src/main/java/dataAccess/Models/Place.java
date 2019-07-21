package dataAccess.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Place implements Serializable {
    double latitude;
    double longitude;
    String name;
    String description;
    int rating;
    String comment;

    @SerializedName("country_name")
    String countryName;

    public Place(double latitude, double longitude, String name, String description, String countryName) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.countryName = countryName;
        this.comment = null;
        this.rating = 0;
    }

    public Place(LatLng location, String name, String description, String countryName) {

        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.name = name;
        this.description = description;
        this.countryName = countryName;
        this.comment = null;
        this.rating = 0;
}

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public boolean equals(Object o) {

        Place place=(Place) o;
        if (this.latitude == place.latitude&&this.longitude== place.longitude) return true;
        else return false;

    }

}
