package edu.jam.telephony.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import okhttp3.Credentials;

public class AccountSaver {

    private SharedPreferences preferences;
    private Gson gson;

    public AccountSaver(Context context) {
        preferences = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void save(Subscriber subscriber){
        String json = gson.toJson(subscriber);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SUB", json);
        editor.commit();
    }

    public Subscriber get(){
        String json = preferences.getString("SUB", "");
        Subscriber sub = gson.fromJson(json, Subscriber.class);
        return sub;
    }

    public boolean hasSub (){
        return preferences.contains("SUB");
    }

    public String getCredentials(){
        return preferences.getString("CREDENTIALS", null);
    }

    public void saveCredentials(String username, String password){
        String credentials = Credentials.basic(username, password);
        preferences.edit().putString("CREDENTIALS", credentials).commit();
    }
}
