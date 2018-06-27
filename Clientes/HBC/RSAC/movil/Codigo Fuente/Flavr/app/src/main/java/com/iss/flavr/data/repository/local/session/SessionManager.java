package com.iss.flavr.data.repository.local.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Luciano on 24/06/2018.
 */

public class SessionManager {

    //Variables declaration
    private static final String PREFERENCE_NAME = "FileSP";
    private int PRIVATE_MODE = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SessionManager sessionManager;


    /*
     * Constructors
     */
    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    private SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /*
     * Session
     */

    private static final String KEY_SESSION = "session";
    public static final String KEY_ID_USER = "idUser";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_P_USERNAME = "personUserName";
    private static final String KEY_P_EMAIL = "personEmail";
    private static final String KEY_P_NAME = "personName";
    private static final String KEY_P_LAST_NAME = "personLastName";
    private static final String KEY_P_BIRTHDAY = "personBirthday";
    private static final String KEY_P_GENDER = "personGender";
    private static final String KEY_L_TYPE = "loginType";

    public void setKeyIdUser(int id) {
        editor.putInt(KEY_ID_USER, id);
        editor.commit();

    }

    public int getKeyIdUser() {
        return preferences.getInt(KEY_ID_USER,0);
    }


    public void setLTypeAsNormal() {
        editor.putInt(KEY_L_TYPE, 1);
        editor.commit();

    }

    public void setLTypeAsSocial() {
        editor.putInt(KEY_L_TYPE, 2);
        editor.commit();

    }

    public void setKeyPUsername(String user) {
        editor.putString(KEY_P_USERNAME, user);
        editor.commit();
    }

    public String getKeyPUsername() {
        return preferences.getString(KEY_P_USERNAME, "");
    }

    public int getKeyLType() {
        return preferences.getInt(KEY_L_TYPE, 0);
    }

    //Token set and get
    public void setKeyToken(String token) {
        editor.putString(KEY_TOKEN, "Bearer " + token);
        editor.commit();
    }

    public String getKeyToken() {
        return preferences.getString(KEY_TOKEN, "");
    }

    /*//IdUser set and get
    public void setIdUser(int idUser) {
        editor.putInt(KEY_ID_USER, idUser);
        editor.commit();
    }

    public int getIdUser() {
        return preferences.getInt(KEY_ID_USER, 0);
    }*/

    //Token set and get
    public void setKeyPhoto(String photo) {
        editor.putString(KEY_PHOTO, photo);
        editor.commit();
    }

    public String getKeyPhoto() {
        return preferences.getString(KEY_PHOTO, "");
    }


    public void setKeyPName(String name) {
        editor.putString(KEY_P_NAME, name);
        editor.commit();
    }

    public String getKeyPName() {
        return preferences.getString(KEY_P_NAME, "");
    }

    public void setKeyPGender(String gender) {
        editor.putString(KEY_P_GENDER, gender);
        editor.commit();
    }

    public String getKeyPGender() {
        return preferences.getString(KEY_P_GENDER, "");
    }

    public void setKeyPBirthday(String birthday) {
        editor.putString(KEY_P_BIRTHDAY, birthday);
        editor.commit();
    }

    public String getKeyPBirthday() {
        return preferences.getString(KEY_P_BIRTHDAY, "");
    }


    public void setKeyPLastName(String lastname) {
        editor.putString(KEY_P_LAST_NAME, lastname);
        editor.commit();
    }

    public String getKeyPLastName() {
        return preferences.getString(KEY_P_LAST_NAME, "");
    }


    public void setKeyPEmail(String email) {
        editor.putString(KEY_P_EMAIL, email);
        editor.commit();
    }

    public String getKeyPEmail() {
        return preferences.getString(KEY_P_EMAIL, "");
    }


    //Change session status in login
    public void login() {
        editor.putBoolean(KEY_SESSION, true);
        editor.commit();
    }

    //Change session status in logout
    public void logOut() {
        //This cleans shared preferences
        editor.clear();
        editor.putBoolean(KEY_SESSION, false);
        editor.commit();
        //This cleans all favorite teams stored in the db
       /* UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                "user", null, 1);
        SQLiteDatabase db = user.getWritableDatabase();
        db.execSQL("delete from TEAMS");*/
    }

    //Ask if the user is already login
    public boolean isActive() {
        return preferences.getBoolean(KEY_SESSION, false);
    }


    /*
     * Restore Password
     */
    private static final String KEY_RESTORE_EMAIL = "restoreEmail";

    public void setKeyRestoreEmail(String email) {
        editor.putString(KEY_RESTORE_EMAIL, email);
        editor.commit();
    }

    public String getKeyRestoreEmail() {
        return preferences.getString(KEY_RESTORE_EMAIL, "");
    }

    /*
     * SingInActivity
     */

    private static final String KEY_SIGN_UP_NAME = "signUpName";
    private static final String KEY_SIGN_UP_LAST_NAME = "signUpLastName";
    private static final String KEY_SIGN_UP_BIRTHDAY = "signUpBirthday";
    private static final String KEY_SIGN_UP_GENDER = "signUpGender";
    private static final String KEY_SIGN_UP_EMAIL = "signUpEmail";
    private static final String KEY_SIGN_UP_PASSWORD = "signUpPassword";


    public void setKeySignUpName(String name) {
        editor.putString(KEY_SIGN_UP_NAME, name);
        editor.commit();
    }

    public String getKeySignUpName() {
        return preferences.getString(KEY_SIGN_UP_NAME, "");
    }

    public void setKeySignUpLastName(String lastName) {
        editor.putString(KEY_SIGN_UP_LAST_NAME, lastName);
        editor.commit();
    }

    public String getKeySignUpLastName() {
        return preferences.getString(KEY_SIGN_UP_LAST_NAME, "");
    }

    public void setKeySignUpBirthday(String birthday) {
        editor.putString(KEY_SIGN_UP_BIRTHDAY, birthday);
        editor.commit();
    }

    public String getKeySignUpBirthday() {
        return preferences.getString(KEY_SIGN_UP_BIRTHDAY, "");
    }

    public void setKeySignUpGender(String gender) {
        editor.putString(KEY_SIGN_UP_GENDER, gender);
        editor.commit();
    }

    public String getKeySignUpGender() {
        return preferences.getString(KEY_SIGN_UP_GENDER, "");
    }

    public void setKeySignUpEmail(String email) {
        editor.putString(KEY_SIGN_UP_EMAIL, email);
        editor.commit();
    }

    public String getKeySignUpEmail() {
        return preferences.getString(KEY_SIGN_UP_EMAIL, "");
    }

    public void setKeySignUpPassword (String pass) {
        editor.putString(KEY_SIGN_UP_PASSWORD, pass);
        editor.commit();
    }

    public String getKeySignUpPassword() {
        return preferences.getString(KEY_SIGN_UP_PASSWORD, "");
    }


    private String getOtherData(String key) {
        return preferences.getString(key, "");
    }

}
