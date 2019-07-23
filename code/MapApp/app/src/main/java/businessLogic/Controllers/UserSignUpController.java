package businessLogic.Controllers;

import android.content.Context;

import businessLogic.ControlResult;
import dataAccess.Repositories.UserRepository;

public class UserSignUpController {

    public static ControlResult insertUsr(Context context, String id, String name, String password) {

        if(id.equals("") || name.equals("") || password.equals("")){

            return ControlResult.INPUT_ERROR;
        }

        ControlResult result = null;

        switch (UserRepository.createUser(context,id,name,password)){

            case SUCCES:

                result = ControlResult.SUCCESS;
                break;

            case DB_ERROR:

                result = ControlResult.SERVER_ERROR;
                break;

            case CONNECT_ERROR:

                result = ControlResult.CONNECT_ERROR;
                break;
        }

        return result;
    }
}
