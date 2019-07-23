package dataAccess.SignalWrappers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import dataAccess.Models.User;

public class UserLogInResult implements Serializable {

    @SerializedName("limit_date")
    public String limitDate;
    @SerializedName("season_countries")
    public ArrayList<String> seasonCountries;
    public User user;
}
