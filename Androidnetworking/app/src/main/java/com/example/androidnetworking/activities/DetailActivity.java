package com.example.androidnetworking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnetworking.R;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.NewsModelDetailResponse;
import com.example.androidnetworking.models.NewsModelResponse;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;
public class DetailActivity extends AppCompatActivity {
    TextView txtTieuDe,txtUser, txtTimeUp, txtContnet;
    IRetrofitRouter iRetrofitRouter;
    ImageView txtImage;
    private int newsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtTieuDe = findViewById(R.id.txtTieuDe);
        txtUser= findViewById(R.id.txtUser);
        txtTimeUp=findViewById(R.id.txtTimeUp);
        txtContnet = findViewById(R.id.txtContent);
        txtImage = findViewById(R.id.imvNews);


        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("NEWS_ID")) {
            newsId = intent.getIntExtra("NEWS_ID", 0); // 0 là giá trị mặc định nếu không có dữ liệu
        }

        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRetrofitRouter.getNewDetail(newsId).enqueue(getNewDetailCallBack);
    }

    Callback<NewsModelDetailResponse> getNewDetailCallBack = new Callback<NewsModelDetailResponse>() {
        @Override
        public void onResponse(Call<NewsModelDetailResponse> call, Response<NewsModelDetailResponse> response) {
            if (response.isSuccessful()){
                NewsModelDetailResponse model = response.body();
                // Hiển thị thông tin chi tiết tin tức trong giao diện
                displayNewsDetail(model);
            } else {
                Toast.makeText(DetailActivity.this, "Lấy dữ liệu chi tiết không thành công", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<NewsModelDetailResponse> call, Throwable t) {
            Log.d(">>> detail", "onFailure: " + t.getMessage());
        }
    };

    // Phương thức để hiển thị thông tin chi tiết tin tức trên giao diện
    private void displayNewsDetail(NewsModelDetailResponse model) {
        if (model != null) {
            txtTieuDe.setText(model.getTitle());
            txtUser.setText("User ID: " + model.getUser_id());
            txtTimeUp.setText("Created At: " + model.getCreated_at());
            txtContnet.setText(model.getContent());
            // Sử dụng Picasso để tải và hiển thị ảnh từ URL
            Picasso.get().load(model.getImage()).into(txtImage);
        }
    }
}