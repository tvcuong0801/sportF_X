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

    public static void init(Context context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static  void setLogin(boolean isLogin){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean("login", isLogin);
        editor.apply();
    }

    public static boolean isLogin(){
       return sPreferences.getBoolean("login", false);
    }

    public static void setIDFB(String email){
        SharedPreferences.Editor editor= sPreferences.edit();
        editor.putString("IDFB",email);
        editor.apply();
    }

    public static String getIDFB(){
        return sPreferences.getString("IDFB","");
    }


    public static void setFirstTimeSetup(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    public static boolean isFirstTimeSetup() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }

    public static void setFirstTimeSetup2(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    public static void setIdSB_Hinh_Anh(int idSB){
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("idsb", idSB);
        editor.apply();
    }

    public static int getIdSB_Hinh_Anh(){
       return sPreferences.getInt("idsb",0);
    }

    public static boolean isFirstTimeSetup2() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }

}
