package businessLogic.Controllers;

import android.content.Context;

import java.util.ArrayList;

import dataAccess.AdminUpdatePayload;
import dataAccess.Models.Administrator;
import dataAccess.Repositories.AdministratorRepository;
import dataAccess.Repositories.ResponseType;

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

        for(int i = divide; i < end; i++){

            if(status.get(i)){

                select.add(countries.get(i));
            }
        }

        AdminUpdatePayload payload = new AdminUpdatePayload(admin.getId(), select, drop);

        return AdministratorRepository.updateAdminCountries(context, payload);
    }
}
