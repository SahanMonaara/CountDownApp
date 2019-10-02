package com.example.sahan.countdownapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahan.countdownapp.Connection.ApiUtils;
import com.example.sahan.countdownapp.Model.Model;
import com.example.sahan.countdownapp.interfaces.RestCall;

import java.util.List;

import io.github.erehmi.countdown.CountDownTask;
import io.github.erehmi.countdown.CountDownTimers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountDownActivity extends AppCompatActivity  implements CountDownTimers.OnCountDownListener {

    private TextView mCountDownTextView;
    private long mDeadlineMillis;
    private CountDownTask mCountDownTask;
    private RestCall mAPIService;
    public int time;

    private static final String TAG = CountDownActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mCountDownTextView = (TextView) findViewById(R.id.count_down);

        mAPIService = ApiUtils.getAPIService();
        mAPIService.getTimes().enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> Times = response.body();
                 time = Integer.parseInt(Times.get(0).getTime());
                 countDown(time);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(CountDownActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void countDown(int time){
        mDeadlineMillis = CountDownTask.elapsedRealtime() + 1000 * time;
        mCountDownTask = CountDownTask.create().until(mCountDownTextView, mDeadlineMillis, 1000, this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void sendNotification(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Countdown is Done!")
                        .setContentText("Countdown Started from "+ time);

        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent notificationIntent = new Intent(this, CountDownActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    @Override
    public void onFinish(View view) {
        sendNotification(view);
        mCountDownTextView.setText("DONE.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDownTask.cancel();
    }

    @Override
    public void onTick(View view, long millisUntilFinished) {
        mCountDownTextView.setText(String.valueOf(millisUntilFinished / 1000));
    }

}
