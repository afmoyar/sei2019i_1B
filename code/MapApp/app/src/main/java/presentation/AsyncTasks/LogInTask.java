package presentation.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.UserLoginController;
import dataAccess.Models.User;
import presentation.Activities.WelcomeUserActivity;

public class LogInTask extends AsyncTask<Void, Void, Pair<User, ControlResult>> {

    String id;
    String password;
    Context context;
    Activity activity;
    String resultKey;
    private ProgressDialog progress;

    public LogInTask(Activity activity,Context context, String id, String password, String resultKey){

        this.context = context;
        this.id = id;
        this.password = password;
        this.progress = new ProgressDialog(activity);
        this.resultKey=resultKey;
        this.activity=activity;

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
    protected Pair<User, ControlResult> doInBackground(Void... voids) {

        return UserLoginController.logIn(context, id, password);
    }

    @Override
    protected void onPostExecute(Pair<User, ControlResult> result){

        progress.dismiss();

        if(result.second == ControlResult.SUCCESS) {

            Intent intent = new Intent(context, WelcomeUserActivity.class);
            intent.putExtra(resultKey, result.first);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }

        switch (result.second){

            case INPUT_ERROR:

                Toast.makeText(context, "Wrong formating", Toast.LENGTH_LONG).show();
                break;

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;

            case SERVER_ERROR:

                Toast.makeText(context, "User or password incorrect. Please try again", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
