package fr.axonaut.axonaut.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.R;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    ApiCallsController mApiCallsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        mApiCallsController = ApiCallsController.getInstance();

    }

    @Override
    protected void onStart() {
        mApiCallsController.getCustomFields(this);
        mApiCallsController.getContactList(this);
        mApiCallsController.getOpportunities(this);
        mApiCallsController.setSplashDataLoadedListener(() -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        mApiCallsController.setSplashDataLoadedListener(null);
        super.onStop();
    }
}
