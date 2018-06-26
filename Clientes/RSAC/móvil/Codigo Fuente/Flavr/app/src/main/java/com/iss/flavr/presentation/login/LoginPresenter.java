package com.iss.flavr.presentation.login;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;
import com.iss.flavr.data.model.Author;
import com.iss.flavr.data.repository.local.session.SessionManager;
import com.iss.flavr.data.repository.remote.ServiceGenerator;
import com.iss.flavr.data.repository.remote.request.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 24/06/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private Context context;
    private LoginContract.View view;
    private SessionManager session;

    public LoginPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(LoginContract.View view) {
        this.view = view;
    }

    @Nullable
    private LoginContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void login(final String email, final String password) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);
        Call<JsonObject> call = userRequest.login(email, password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        JsonObject jsonObject = response.body();
                        Log.e("yara", response.code() + "");
                        Log.e("email", email);
                        Log.e("pass", password);
                        if (jsonObject.get("status").getAsBoolean()) {
                            //Set login type
                            getUserInfo(jsonObject.get("person_id").getAsInt());
                            session.setLTypeAsNormal();
                            session.setKeyIdUser(jsonObject.get("person_id").getAsInt());
                            session.login();
                        } else {
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showWrongCredentialsToast();
                            }

                        }

                    }
                }

            }

            //Connection error message
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }
            }
        });
    }


    public void getUserInfo(int id) {
        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);
        Call<JsonObject> call = userRequest.getUserInfo(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        JsonObject user = response.body();
                        Log.e("yara", response.code() + "");
                        if (response.code()==200) {
                            //Set login type
                            session.setKeyPhoto(user.get("person_image").getAsString());
                            session.setKeyPUsername(user.get("user").getAsJsonObject().get("username").getAsString());
                            session.setKeyPEmail(user.get("user").getAsJsonObject().get("email").getAsString());
                            session.setKeyPName(user.get("user").getAsJsonObject().get("first_name").getAsString());
                            session.setKeyPLastName(user.get("user").getAsJsonObject().get("last_name").getAsString());
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSuccessToast();
                                getView().launchHome();
                            }

                        }else {
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showWrongCredentialsToast();
                            }

                        }

                    }
                }

            }

            //Connection error message
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }
            }
        });
    }
}
