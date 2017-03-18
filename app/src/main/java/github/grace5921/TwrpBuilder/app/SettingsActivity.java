package github.grace5921.TwrpBuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import github.grace5921.TwrpBuilder.R;

/**
 * Created by sumit on 19/11/16.
 */

public class SettingsActivity extends AppCompatActivity {
    private Button DeleteUser;
    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        DeleteUser=(Button)findViewById(R.id.delete_account);

        DeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}
