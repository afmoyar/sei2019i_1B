package businessLogic.Controllers;

import android.content.Context;

import java.util.ArrayList;

import dataAccess.SignalWrappers.AdminUpdatePayload;
import dataAccess.Models.Administrator;
import dataAccess.SignalWrappers.AdminLogInResult;
import dataAccess.Repositories.AdministratorRepository;
import dataAccess.ResponseType;

public class AdminSetupController {

    public static ResponseType updateAdminCountries(Context context, Administrator admin, ArrayList<String> countries, ArrayList<Boolean> status){

        ArrayList<String> select = new ArrayList<>();
        ArrayList<String> drop = new ArrayList<>();

        int divide = admin.getCountries().size();
        int end = status.size();

        for(int i = 0; i < divide; i++){

            if(status.get(i)){

                drop.add(admin.getCountries().get(i));
            }
        }

        for(int i = divide, j = 0; i < end; i++, j++){

            if(status.get(i)){

                select.add(countries.get(j));
            }
        }

        AdminUpdatePayload payload = new AdminUpdatePayload(admin.getId(), select, drop);

        return AdministratorRepository.updateAdminCountries(context, payload);
    }

    public static AdminLogInResult computeNewCountryDistribution(Administrator admin, ArrayList<String> otherCountries, ArrayList<Boolean> status){

        int divide = admin.getCountries().size();
        int total = status.size();

        ArrayList<String> newAdminCountries = new ArrayList<>();
        ArrayList<String> newOtherCountries = new ArrayList<>();

        for(int i = 0; i < divide; i++){

            String country = admin.getCountries().get(i);

            if(status.get(i)){

                newOtherCountries.add(country);
            }
            else {

                newAdminCountries.add(country);
            }
        }

        for(int i = divide, j = 0; i < total; i++, j++){

            String country = otherCountries.get(j);

            if(status.get(i)){

                newAdminCountries.add(country);
            }
            else {

                newOtherCountries.add(country);
            }
        }

        AdminLogInResult result = new AdminLogInResult();
        Administrator newAdmin = new Administrator(admin, newAdminCountries);
        result.admin = newAdmin;
        result.countries = newOtherCountries;

        return result;
    }

}
