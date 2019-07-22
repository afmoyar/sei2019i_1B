package businessLogic.Controllers;

import android.content.Context;
import android.widget.Toast;

import dataAccess.DataBase.Database;

public class DateController {

    private static final Database database = new Database();

    public DateController(){

    }

    public void updatedate(Context context, String date){
        database.updateadmin(context,date);
    }

    public String[] split (String date){
        String[] chain = date.split("/",3);
        return chain;
    }

    public boolean verify(Context context,String date){
        String[] chain= split(date);
        if(Integer.parseInt(chain[0]) >= 2019){
            if(Integer.parseInt(chain[1]) > 0 && Integer.parseInt(chain[1]) < 13){
                if(Integer.parseInt(chain[2]) > 0 && Integer.parseInt(chain[2]) < 31){
                    return  true;
                }else{
                    Toast.makeText(context, "wrong day", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(context, "wrong month", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(context, "wrong year", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
