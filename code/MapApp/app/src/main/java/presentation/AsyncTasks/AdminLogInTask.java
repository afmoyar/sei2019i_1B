package presentation.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import businessLogic.Controllers.AdminLoginController;
import businessLogic.Controllers.ControlResult;
import dataAccess.Repositories.AdminLogInResult;
import presentation.Activities.WelcomeAdminActivity;

public class AdminLogInTask extends AsyncTask<Void, Void, Pair<AdminLogInResult, ControlResult>> {

    String id;
    String password;
    Context context;
    Activity activity;
    String resultKey;
    private ProgressDialog progress;

    public AdminLogInTask(Activity activity, Context context, String id, String password, String resultKey){

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
    protected Pair<AdminLogInResult, ControlResult> doInBackground(Void... voids) {

        return AdminLoginController.logIn(context, id, password);
    }

    @Override
    protected void onPostExecute(Pair<AdminLogInResult, ControlResult> result){

        progress.dismiss();

        if(result.second == ControlResult.SUCCESS) {

            Intent intent = new Intent(context, WelcomeAdminActivity.class);
            intent.putExtra(resultKey, result.first);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }

        switch (result.second){

            case INPUT_ERROR:

                Toast.makeText(context, "Wrong formatting", Toast.LENGTH_LONG).show();
                break;

            case CONNECT_ERROR:

                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show();
                break;

            case SERVER_ERROR:

                Toast.makeText(context, "ID or password incorrect. Please try again", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
