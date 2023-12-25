package com.example.androidnetworking.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidnetworking.R;
import com.example.androidnetworking.models.NewsModelResponse;

import java.net.URL;
import java.util.List;

// Import thư viện
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends BaseAdapter {

    List<NewsModelResponse> list;

    public NewsAdapter(List<NewsModelResponse> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _View, ViewGroup _ViewGroup) {
        View view = _View;
        if(view ==null){
            view = View.inflate(_ViewGroup.getContext(),R.layout.item_bangtin,null);
            TextView txtTieuDe = view.findViewById(R.id.txtTieuDe);
            TextView txtUser = view.findViewById(R.id.txtUser);
            TextView txtTimeUp = view.findViewById(R.id.txtTimeUp);
            TextView txtContent = view.findViewById(R.id.txtContent);
            ImageView imvNews = view.findViewById(R.id.imvNews);

            ViewHolder holder = new ViewHolder(txtTieuDe,txtUser,txtTimeUp,txtContent,imvNews);
            view.setTag(holder);
        }
        NewsModelResponse modelResponse = (NewsModelResponse) getItem(_i) ;
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtTieuDe.setText(modelResponse.getTitle());

        holder.txtContent.setText(modelResponse.getContent());
       holder.txtUser.setText(String.valueOf(modelResponse.getUser_id()));
        holder.txtTimeUp.setText(modelResponse.getCreate_at());

//        // Sử dụng Picasso để tải ảnh từ URL và hiển thị trong ImageView
//        Picasso.get().load(modelResponse.getImage()).into(holder.imvNews, new Callback() {
//            @Override
//            public void onSuccess() {
//                Log.d("..........", "Image loaded successfully");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("..........", "Error loading image: " + e.getMessage());
//            }
//        });
        String imgUrl = modelResponse.getImage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imgUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    holder.imvNews.setImageBitmap(bitmap);
                }catch (Exception e){
                    Log.d(">>>>>>>>>>>>>>>>TAG", "load img news: " + e);
                }
            }
        }).start();
        Log.d("ssssssssss", "fffff" + imgUrl);

        return view;
    }

    private static class ViewHolder{
        final TextView txtTieuDe, txtUser, txtTimeUp, txtContent;
        final ImageView imvNews;

        private ViewHolder(TextView txtTieuDe, TextView txtUser, TextView txtTimeUp, TextView txtContent, ImageView imvNews) {
            this.txtTieuDe = txtTieuDe;
            this.txtUser = txtUser;
            this.txtTimeUp = txtTimeUp;
            this.txtContent = txtContent;
            this.imvNews = imvNews;
        }
    }
}
