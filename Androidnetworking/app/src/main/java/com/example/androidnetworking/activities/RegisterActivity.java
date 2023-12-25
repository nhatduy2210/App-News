package com.example.androidnetworking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidnetworking.R;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.UserLoginRequest;
import com.example.androidnetworking.models.UserLoginResponse;
import com.example.androidnetworking.models.UserRegisterRequest;
import com.example.androidnetworking.models.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmailRegister,edtNameRegister,edtPassWordRes,edtRePassWordRes;
    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtNameRegister = findViewById(R.id.edtNameRegister);
        edtPassWordRes = findViewById(R.id.edtPassWordRes);
        edtRePassWordRes = findViewById(R.id.edtRePassWordRes);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //kết nối
        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);
    }

    public void onRegisterSubmit(View view){
        String email = edtEmailRegister.getText().toString();
        String password = edtPassWordRes.getText().toString();
        String name = edtNameRegister.getText().toString();

        UserRegisterRequest request = new UserRegisterRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setName(name);

        iRetrofitRouter.register(request).enqueue(RegisterCallback);

    }

    //hàm để nhận kết quả đi login trả về
    Callback<UserRegisterResponse> RegisterCallback = new Callback<UserRegisterResponse>() {
        @Override
        public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
            if (response.isSuccessful()){
                UserRegisterResponse resResponseDTO = response.body();
                if ("User registered successfully.".equals(resResponseDTO.getMessage())) {
                    // nếu thành công chuyển sang màn hình danh sách
                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d("Response Data", response.toString());
                    Toast.makeText(RegisterActivity.this, "Failed!!!", Toast.LENGTH_LONG).show();
                }
            }
        }


        @Override
        public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };


    public void onLoadLoginScreen(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

}