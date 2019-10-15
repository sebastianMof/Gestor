package com.sanbar.gestor.data;

import android.os.AsyncTask;
import android.util.Log;

import com.sanbar.gestor.Sesion;
import com.sanbar.gestor.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    //For HTTP
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    //Keep track of the login task to ensure we can cancel it if requested.
    private UserLoginTask mAuthTask = null;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            boolean loginSuccess = attemptLogin(username,password);

            if (loginSuccess){
                LoggedInUser fakeUser =
                        new LoggedInUser( "userId", "displayName","token" );
                return new Result.Success<>(fakeUser);
            } else {
                return new Result.Error(new IOException("Error logging in"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private final String mPassword;
        Sesion session = new Sesion("no user","no token");

        UserLoginTask(String user, String password) {
            mUser = user;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();
            /*JSONObject postdata = new JSONObject();
            try {
                postdata.put("username",mUser);
                postdata.put("password",mPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            String postdata = "username="+mUser+"&password="+mPassword+"&grant_type=password";
            //String postdata = "username=diego.murua@koffguerrero.com&password=Diego_01&grant_type=password";

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/Token")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONObject obj = new JSONObject(jsonResponse);

                        session.setUser(mUser);
                        session.setToken(obj.getString("access_token"));

                        Log.e("TEST",obj.getString("access_token"));

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                //Intent intent = new Intent(LoginActivity.this, BodegaActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("session", session);
                //startActivity(intent);
                //finish();
                Log.e("TEST","AUTH OK");

            } else {
                //Toast.makeText(LoginActivity.this, "Revisar datos de ingreso", Toast.LENGTH_LONG).show();
                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }

    private boolean attemptLogin(String user,String password) {
        if (mAuthTask != null) {
            return false;
        }

        mAuthTask = new UserLoginTask(user, password);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mAuthTask.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
