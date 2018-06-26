package com.iss.flavr.presentation.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Toast;

import com.iss.flavr.R;
import com.iss.flavr.presentation.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{


    @BindView(R.id.et_user_email)
    TextInputEditText etUsername;
    @BindView(R.id.til_user_email)
    TextInputLayout tilUsername;
    @BindView(R.id.et_user_password)
    TextInputEditText etPassword;
    @BindView(R.id.til_user_password)
    TextInputLayout tilPassword;



    private LoginContract.Presenter presenter;
    private Context context;
    private ProgressDialog mProgressDialog;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context=this;
        if (presenter == null) {
            presenter = new LoginPresenter(context);
        }
        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage( getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
    }



    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(LoginActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        tilPassword.setErrorEnabled(false);
        tilUsername.setErrorEnabled(false);
        tilUsername.setError(null);
        tilPassword.setError(null);
        flag = true;


        validateEmailField();
        validatePassword();


        if (flag) {
            getPresenter().login(etUsername.getText().toString(), etPassword.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.cant_pass), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validatePassword(){
        if (etPassword.length() < 4) {
            etPassword.requestFocus();
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getResources().getString(R.string.invalid_pass));
            flag = false;
        }
    }

    private void validateEmailField(){
        if (etUsername.length()<4) {
            etUsername.requestFocus();
            tilUsername.setErrorEnabled(true);
            tilUsername.setError(getResources().getString(R.string.invalid_email));
            flag = false;
        }
    }

    private LoginContract.Presenter getPresenter() {
        return presenter;
    }


    @Override
    public void showSuccessToast() {
        Toast.makeText(context, getResources().getString(R.string.login_success),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }


    @Override
    public void showWrongCredentialsToast() {
        Toast.makeText(context, getResources().getString(R.string.email_password_ws_error),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchHome() {
        launchActivity(this, HomeActivity.class);
    }

    @Override
    public void launchRestorePassword() {

    }

    private void launchActivity(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
