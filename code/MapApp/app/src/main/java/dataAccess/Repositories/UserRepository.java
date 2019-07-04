package dataAccess.Repositories;

import android.content.Context;

import dataAccess.DataBase.Database;

public abstract class UserRepository {
    private static final Database database = new Database();

    public static void searchByIdAndPassword(final Context context,final String id, final String password){
        database.loginFunction(context, id, password);
    }

    public static void createUser(Context context, String id,String name, String password){
        database.insertUser(context,id,name,password);
    }
}
