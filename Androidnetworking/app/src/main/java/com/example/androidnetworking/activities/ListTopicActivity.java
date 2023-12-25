package com.example.androidnetworking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.androidnetworking.R;
import com.example.androidnetworking.adapters.NewsAdapter;
import com.example.androidnetworking.helpers.IRetrofitRouter;
import com.example.androidnetworking.helpers.RetrofitHelpers;
import com.example.androidnetworking.models.NewsModelDetailResponse;
import com.example.androidnetworking.models.NewsModelResponse;
import com.example.androidnetworking.models.TopicModelResponse;
import com.example.androidnetworking.models.VideoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTopicActivity extends AppCompatActivity {

    VideoView videoView;
    List<TopicModelResponse> listT;
    HorizontalScrollView horizontalScrollView;
    LinearLayout linearLayout;
    IRetrofitRouter iRetrofitRouter;

    ListView lvNews;
    List<NewsModelResponse> list;
    NewsAdapter adapter;
    private int viId =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_topic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = findViewById(R.id.videoView);
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
      linearLayout = findViewById(R.id.linearLayout);
        lvNews = findViewById(R.id.lvNews);

        list = new ArrayList<>();
        adapter = new NewsAdapter(list);
        lvNews.setAdapter(adapter);

        //nhấp vào chi tiết
        lvNews.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy tin được chọn từ danh sách
            NewsModelResponse selectedNews = list.get(position);

            // Chuyển sang Activity chi tiết và truyền dữ liệu
            Intent intent = new Intent(ListTopicActivity.this, DetailActivity.class);
            intent.putExtra("NEWS_ID", selectedNews.getId()); // Truyền ID của tin tức
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        iRetrofitRouter = RetrofitHelpers.createService(IRetrofitRouter.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRetrofitRouter.getTopic().enqueue(getTopic);
        iRetrofitRouter.getNew().enqueue(getNewCallBack);
        iRetrofitRouter.getVideoDetail(viId).enqueue(getViDetailCallBack);

        Log.d("dfdfdfdfdfdfdf", String.valueOf(getTopic));
    }

    Callback<List<TopicModelResponse>> getTopic = new Callback<List<TopicModelResponse>>() {
        @Override
        public void onResponse(Call<List<TopicModelResponse>> call, Response<List<TopicModelResponse>> response) {
            if (response.isSuccessful()){
                List<TopicModelResponse> model = response.body();
                if (model != null && model.size()>0) {
                    // Lấy LinearLayout


                    // Xóa tất cả các view hiện tại trong linearLayout
                    linearLayout.removeAllViews();



                    // Duyệt qua danh sách và thêm TextView mới cho mỗi Topic
                    for (TopicModelResponse topic : model) {

                        View topicItemView = getLayoutInflater().inflate(R.layout.item_topic, null);
                        TextView txtNameTopic = topicItemView.findViewById(R.id.txtTopicDetail);
                        txtNameTopic.setText(topic.getName());


                        // Thêm TextView vào LinearLayout

                        linearLayout.addView(topicItemView);

                }}
                else {
                    Toast.makeText(ListTopicActivity.this, "lấy dasnh sáhc k đc", Toast.LENGTH_SHORT).show();
                }

        }}

        @Override
        public void onFailure(Call<List<TopicModelResponse>> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };


//newss
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
                Toast.makeText(ListTopicActivity.this, "lấy dasnh sáhc k đc", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<List<NewsModelResponse>> call, Throwable t) {
        Log.d(">>> login", "onFailure: " + t.getMessage());
    }
};

    Callback<VideoResponse> getViDetailCallBack = new Callback<VideoResponse>() {
        @Override
        public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
            if (response.isSuccessful()){
                VideoResponse model = response.body();
                Log.d("fffffffffff", "hiên linkkkkkkkkkk: "+model.getImage());
                if (model != null && model.getImage() != null) {
                    // Hiển thị thông tin chi tiết tin tức trong giao diện
                    videoView.setVideoURI(Uri.parse(model.getImage()));
                    videoView.start();
                } else {
                    Toast.makeText(ListTopicActivity.this, "Dữ liệu video không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ListTopicActivity.this, "Lấy dữ liệu chi tiết không thành công", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<VideoResponse> call, Throwable t) {
            Log.d(">>> detail", "onFailure: " + t.getMessage());
        }
    };

}