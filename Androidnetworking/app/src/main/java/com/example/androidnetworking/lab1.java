package com.example.androidnetworking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class lab1 extends AppCompatActivity {
    TextView txtView1,txtView2;
    ImageView imgView1;
    //kiểm soát luồng chạy
    ThreadPoolExecutor executor;

    //handler xử luồng
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                txtView2.setText(">>>>>>>>>>>>>>>>>>>>>>");
            } catch (Exception e) {
                Log.d("???????????", "handleMessage: " + e.getMessage());
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);

        txtView1 = findViewById(R.id.txtView1);

        imgView1 = findViewById(R.id.imgView1);

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    public void onButton1Click(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    SystemClock.sleep(1000);
                    Calendar calendar = Calendar.getInstance();
                    int h = calendar.get(Calendar.HOUR_OF_DAY);
                    int m = calendar.get(Calendar.MINUTE);
                    int s = calendar.get(Calendar.SECOND);
                    final int _h = h, _m = m, _s = s;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtView1.setText(_h + ":" + _m + ":" + _s);
                        }
                    });
                }
            }
        }).start();
    }

    public  void onButton2Click(View view){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                double value = Math.round(Math.random() * 100);
                Message message = new Message();
                message.obj = value;
                handler.handleMessage(message);
            }
        });
    }
    public  void onButton3Click(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] arrayUrl = new String[5];
                    arrayUrl[0] = "https://thuthuatphanmem.vn/uploads/2018/04/10/hinh-nen-thung-lung-nui-doi-dep_052339827.jpg";
                    arrayUrl[1] = "https://tse4.mm.bing.net/th?id=OIP.kZTZbtrISc10A3A227m1pwHaEK&pid=Api&P=0&h=180";
                    arrayUrl[2] = "https://1.bp.blogspot.com/-G6N89Ccg_lE/VRn-Etym1TI/AAAAAAAAFQQ/aYJHG3R8_xA/s1600/hinh-nen-may-tinh-full-hd-dep-me-hon.jpg";
                    arrayUrl[3] = "https://anhdepfree.com/wp-content/uploads/2020/11/anh-nen-3d-cho-desktop.jpg";
                    arrayUrl[4] = "https://tse4.mm.bing.net/th?id=OIP.2-UF7m4e4qGhLGd0DYrQmgHaEK&pid=Api&P=0&h=180  ";
                    Random random = new Random();
                    int randomNumber = random.nextInt(5);
                    URL url = new URL(arrayUrl[randomNumber]);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgView1.setImageBitmap(bitmap);
                        }

                    });
                }catch (Exception e){
                    Log.d(">>>>>>>>>>>>>>>>TAG", "onButton3Click: " + e);
                }
            }
        }).start();
            }
    }
