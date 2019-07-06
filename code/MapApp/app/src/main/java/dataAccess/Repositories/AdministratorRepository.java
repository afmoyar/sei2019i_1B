package dataAccess.Repositories;

import android.content.Context;
import dataAccess.DataBase.Database;


public abstract class AdministratorRepository {

    private static final Database database = new Database();

    public static void searchByIdAndPassword(final Context context,final String id, final String password){
        database.AdminloginFunction(context, id, password);
    }
}
