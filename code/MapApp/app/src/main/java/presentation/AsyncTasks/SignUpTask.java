package presentation.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.UserSignUpController;

public class SignUpTask extends AsyncTask<Void, Void, ControlResult> {

    private Context context;
    private Activity activity;
    private String id;
    private String name;
    private String pass;
    private ProgressDialog progress;

    public SignUpTask(Activity activity,Context context, String id, String name, String pass){

        this.context = context;
        this.activity=activity;
        this.progress = new ProgressDialog(activity);
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    @Override
    protected void onPreExecute(){

        super.onPreExecute();
        progress.setMessage("Querying DB");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected ControlResult doInBackground(Void... voids) {

        return UserSignUpController.insertUsr(context, id, name, pass);
    }

    @Override
    protected void onPostExecute(ControlResult result){

        progress.dismiss();

        switch (result) {

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;

            case INPUT_ERROR:

                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                break;

            case SERVER_ERROR:

                Toast.makeText(context, "Couldn't create user. Change the id and try again", Toast.LENGTH_LONG).show();
                break;

            case SUCCESS:

                Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}