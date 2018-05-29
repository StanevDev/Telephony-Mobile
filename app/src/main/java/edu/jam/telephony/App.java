package edu.jam.telephony;

import android.app.Application;

import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.network.RetrofitService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AccountSaver saver = new AccountSaver(this);
        String credentials = saver.getCredentials();

        if (credentials != null)
            RetrofitService.credentials = credentials;
    }
}
