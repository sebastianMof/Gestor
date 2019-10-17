package com.sanbar.gestor.data;

import com.sanbar.gestor.Sesion;
import com.sanbar.gestor.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String mail, String password) {

        try {
            // TODO: handle loggedInUser authentication

            LoggedInUser fakeUser =
                    new LoggedInUser( "userId", "displayName","token" );
            return new Result.Success<>(fakeUser);

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }
}
