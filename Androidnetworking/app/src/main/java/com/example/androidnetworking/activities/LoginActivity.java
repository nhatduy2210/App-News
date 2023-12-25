package com.example.androidnetworking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnetworking.R;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.ForgotPassRequest;
import com.example.androidnetworking.models.ForgotPassResponse;
import com.example.androidnetworking.models.UserLoginRequest;
import com.example.androidnetworking.models.UserLoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView txtForgotPass;
    EditText edtEmailLogin,edtPassWordLogin;
    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtForgotPass = findViewById(R.id.txtForgotPassWord);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPassWordLogin =findViewById(R.id.edtPassWordLogin);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //kết nối
        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmai= edtEmailLogin.getText().toString();
               ForgotPass(edtEmailLogin.getText().toString());
                Toast.makeText(LoginActivity.this, "click "+textEmai, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onLoginSubmit(View view){
        String email = edtEmailLogin.getText().toString();
        String password = edtPassWordLogin.getText().toString();

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        iRetrofitRouter.login(request).enqueue(loginCallback);


    }

    public void onLoadRegisterScreen(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
//hàm để nhận kết quả đi login trả về
    Callback<UserLoginResponse> loginCallback = new Callback<UserLoginResponse>() {
        @Override
        public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
            if (response.isSuccessful()){
                UserLoginResponse loginResponseDTO = response.body();
                if (loginResponseDTO.getStatus()) {
                    // nếu thành công chuyển sang màn hình danh sách
                    Toast.makeText(LoginActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    // Lấy ID người dùng từ API đăng nhập
                    int userId = loginResponseDTO.getUser().getID();

                    // Lưu ID người dùng vào SharedPreferences
                    saveUserIdToSharedPreferences(userId);

                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(LoginActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<UserLoginResponse> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };

    private void saveUserIdToSharedPreferences(int userId) {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }



    public void ForgotPass(String email) {
        // Gọi API tìm kiếm với từ khóa
        ForgotPassRequest request = new ForgotPassRequest();
        request.setEmail(email);
        iRetrofitRouter.forgot_pass(request).enqueue(ForgotCallBack);
    }

    Callback<ForgotPassResponse> ForgotCallBack = new Callback<ForgotPassResponse>() {
        @Override
        public void onResponse(Call<ForgotPassResponse> call, Response<ForgotPassResponse> response) {
            if (response.isSuccessful()){
                ForgotPassResponse ForgotResponseDTO = response.body();
                if ("Email sent.".equals(ForgotResponseDTO.getMessage())) {
                    // nếu thành công chuyển sang màn hình danh sách
                    Toast.makeText(LoginActivity.this, "Gửi ok", Toast.LENGTH_SHORT).show();
                    // Lấy ID người dùng từ API đăng nhập


                }
                else {
                    Toast.makeText(LoginActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<ForgotPassResponse> call, Throwable t) {
            Log.d(">>> Forgot", "onFailure: " + t.getMessage());
        }
    };

}