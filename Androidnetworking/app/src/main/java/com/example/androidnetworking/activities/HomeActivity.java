package com.example.androidnetworking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidnetworking.R;
import com.example.androidnetworking.adapters.NewsAdapter;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.ForgotPassResponse;
import com.example.androidnetworking.models.NewsModelResponse;
import com.example.androidnetworking.models.UserLoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Button btnSearch ;
        EditText edtSearch;
    ListView lvNews;
    List<NewsModelResponse> list;
    NewsAdapter adapter;
    IRetrofitRouter iRetrofitRouter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangtin);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lvNews = findViewById(R.id.lvNews);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);


        list = new ArrayList<>();
        adapter = new NewsAdapter(list);
        lvNews.setAdapter(adapter);

        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);

        //nhấp vào chi tiết
        lvNews.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy tin được chọn từ danh sách
            NewsModelResponse selectedNews = list.get(position);

            // Chuyển sang Activity chi tiết và truyền dữ liệu
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra("NEWS_ID", selectedNews.getId()); // Truyền ID của tin tức
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


        ///bottom navi
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // Xử lý khi chọn Home
                        return true;
                    case R.id.action_detail:
                        // Xử lý khi chọn Search

                        Intent intent1 = new Intent(HomeActivity.this,ListTopicActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.action_prof:
                      Intent intent = new Intent(HomeActivity.this,profile.class);
                      startActivity(intent);
                        return true;
                    case R.id.action_search:
                        // Xử lý khi chọn Profile
                        Intent intent2 = new Intent(HomeActivity.this,MenuMain.class);
                        startActivity(intent2);
                        return true;
                    case R.id.action_out:
                        // Xử lý khi chọn Settings
                        Intent intent3 = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent3);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        iRetrofitRouter.getNew().enqueue(getNewCallBack);
    }

    public void performSearch(View view) {
        // Lấy từ khóa tìm kiếm từ EditText

        String keyword = edtSearch.getText().toString();

        // Kiểm tra xem từ khóa có giá trị không
        if (!TextUtils.isEmpty(keyword)) {
            // Thực hiện tìm kiếm
            performSearchRequest(keyword);
        } else {
            // Hiển thị thông báo cho người dùng rằng từ khóa trống
            Toast.makeText(this, "Bạn để trống tìm kiếm", Toast.LENGTH_SHORT).show();
        }
    }

    private void performSearchRequest(String keyword) {
        // Gọi API tìm kiếm với từ khóa
        iRetrofitRouter.searchNews(keyword).enqueue(getNewCallBack);
    }



    Callback<List<NewsModelResponse>> getNewCallBack = new Callback<List<NewsModelResponse>>() {
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
                    Toast.makeText(HomeActivity.this, "lấy dasnh sáhc k đc", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<NewsModelResponse>> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };
}
