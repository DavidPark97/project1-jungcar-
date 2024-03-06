package com.example.jungcar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class shared_preferences {

        static String pref_user_email = "user_email";
        static String pref_user_car = "user_car";

        static public SharedPreferences get_shared_preferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void set_user_email(Context ctx, String user_email) {//유저 저장
            SharedPreferences.Editor editor = get_shared_preferences(ctx).edit();
            editor.putString(pref_user_email, user_email);
            editor.commit();
        }

        public static String get_user_email(Context ctx) {
            return get_shared_preferences(ctx).getString(pref_user_email, "");
        }

        public static void set_user_car(Context ctx, String user_car) {//차량 저장
        SharedPreferences.Editor editor = get_shared_preferences(ctx).edit();
        editor.putString(pref_user_car, user_car);
        editor.commit();
    }

      public static String get_user_car(Context ctx) {
        return get_shared_preferences(ctx).getString(pref_user_car, "");
    }


    public static void clear_user(Context ctx) {
            SharedPreferences.Editor editor = get_shared_preferences(ctx).edit();
            editor.remove(pref_user_email);
            editor.commit();
        }

    public static void clear_car(Context ctx) {
        SharedPreferences.Editor editor = get_shared_preferences(ctx).edit();
        editor.remove(pref_user_car);
        editor.commit();
    }
    }
