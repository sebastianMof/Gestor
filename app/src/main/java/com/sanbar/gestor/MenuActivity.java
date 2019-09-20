package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String welcome = getString(R.string.welcome);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            String loginModelData1 = extras.getString("loginModelData1");//model.getDisplayName()
        }

    }
}
