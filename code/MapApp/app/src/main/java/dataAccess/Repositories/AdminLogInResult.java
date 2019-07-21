package dataAccess.Repositories;

import java.io.Serializable;
import java.util.ArrayList;

import dataAccess.Models.Administrator;

public class AdminLogInResult implements Serializable {

    public Administrator admin;
    public ArrayList<String> countries;
}
