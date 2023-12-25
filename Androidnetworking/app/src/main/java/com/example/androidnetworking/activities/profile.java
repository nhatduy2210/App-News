package com.example.androidnetworking.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnetworking.R;
import com.example.androidnetworking.adapters.NewsAdapter;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.NewsModelResponse;
import com.example.androidnetworking.models.UserDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    IRetrofitRouter iRetrofitRouter;
    ListView lvProNew;
    List<NewsModelResponse> list;
    NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lvProNew = findViewById(R.id.lvProNew);

        list = new ArrayList<>();
        adapter = new NewsAdapter(list);
        lvProNew.setAdapter(adapter);

        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int userId = preferences.getInt("user_id", -1);

        Log.d(".................", String.valueOf(userId));

        //kết nối
        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);
        // Gọi API để lấy thông tin người dùng với ID
        iRetrofitRouter.getUserDetail(userId).enqueue(userDetailsCallback);
        iRetrofitRouter.getNewByUser(userId).enqueue(getNewCallBackUser);

        //nhấp vào chi tiết
        lvProNew.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy tin được chọn từ danh sách
            NewsModelResponse selectedNews = list.get(position);

            // Chuyển sang Activity chi tiết và truyền dữ liệu
            Intent intent = new Intent(profile.this, DetailActivity.class);
            intent.putExtra("NEWS_ID", selectedNews.getId()); // Truyền ID của tin tức
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    Callback<UserDetailResponse> userDetailsCallback = new Callback<UserDetailResponse>() {
        @Override
        public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
            if (response.isSuccessful()) {
                // Thành công, cập nhật giao diện với thông tin người dùng
                UserDetailResponse userDetails = response.body();
                // Đây là nơi bạn có thể cập nhật giao diện với thông tin người dùng
                updateUI(userDetails);
            } else {
                // Lỗi API, xử lý tùy thuộc vào yêu cầu
                // Ví dụ: Hiển thị thông báo lỗi
                Toast.makeText(profile.this, "Failed to get user details", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<UserDetailResponse> call, Throwable t) {
            // Xử lý lỗi kết nối hoặc lỗi khác
            Log.e("ProfileActivity", "API request failed: " + t.getMessage());
        }
    };

    private void updateUI(UserDetailResponse userDetails) {
        // Đây là nơi để cập nhật giao diện người dùng với thông tin nhận được từ API
        // Ví dụ: Thiết lập TextViews, ImageView, vv.
        TextView txtUserName = findViewById(R.id.txtName);
        TextView txtUserEmail = findViewById(R.id.txtEmail);
        ImageView imageView = findViewById(R.id.imvAvatar);


        txtUserName.setText(userDetails.getName());
        txtUserEmail.setText(userDetails.getEmail());

        // Sử dụng Picasso để tải và hiển thị ảnh từ URL
        Picasso.get().load(userDetails.getAvatar()).error(R.drawable.img_null).into(imageView);

    }

    Callback<List<NewsModelResponse>> getNewCallBackUser = new Callback<List<NewsModelResponse>>() {
        @Override
        public void onResponse(Call<List<NewsModelResponse>> call, Response<List<NewsModelResponse>> response) {
            if (response.isSuccessful()){
                List<NewsModelResponse> model = response.body();
                if (model != null && model.size()>0) {
                    list.clear();
                    list.addAll(model);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(profile.this, "Danh sách trống", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<NewsModelResponse>> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };


}