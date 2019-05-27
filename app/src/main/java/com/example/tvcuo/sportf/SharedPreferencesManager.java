package com.example.tvcuo.sportf;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
    private static final String PREF_FIRST_TIME_SETUP = BuildConfig.APPLICATION_ID + ".pref_first_time_setup";
    private static final String PREF_FIRST_TIME_SETUP2 = BuildConfig.APPLICATION_ID + ".pref_first_time_setup2";


    private static SharedPreferences sPreferences;

    private SharedPreferencesManager() {
    }

    static void init(Context context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    static  void setLogin(boolean isLogin){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean("login", isLogin);
        editor.apply();
    }

    static boolean isLogin(){
       return sPreferences.getBoolean("login", false);
    }

    static void setEmail(String email){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("email",email);
        editor.apply();
    }

    static String getEmail(){
        return sPreferences.getString("email","");
    }


    static void setTenFB(String tenFB)
    {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("nameFB",tenFB);
        editor.apply();
    }
    static String getTenFB() { return sPreferences.getString("nameFB","");}


    static void setFirstTimeSetup(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    static boolean isFirstTimeSetup() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }

    public static void setFirstTimeSetup2(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    static void setIdSB_Hinh_Anh(int idSB){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("idsb", idSB);
        editor.apply();
    }

    static int getIdSB_Hinh_Anh(){
       return sPreferences.getInt("idsb",0);
    }

    public static boolean isFirstTimeSetup2() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }

}
