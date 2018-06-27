package com.iss.flavr.presentation.login;

/**
 * Created by Luciano on 24/06/2018.
 */

public interface LoginContract {
    interface View{
        void showSuccessToast();
        void showLoadingDialog();
        void hideLoadingDialog();
        void showWrongCredentialsToast();
        void showConnectionError();
        void launchHome();
        void launchRestorePassword();
    }
    interface Presenter{
        void onViewDettach();
        void onViewAttach(LoginContract.View view);
        void login(String email, String password);
    }
}

