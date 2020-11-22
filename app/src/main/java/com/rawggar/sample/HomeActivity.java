package com.rawggar.sample;

import android.content.Intent;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.media.SoundPool;
        import android.os.AsyncTask;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Toast;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;
        import org.w3c.dom.Element;

        import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Content content=new Content();
        //content.execute();
    }

    public void Squad1(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 1);
        startActivity(intent);
    }

    public void Squad2(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 2);
        startActivity(intent);
    }

    public void Squad3(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 3);
        startActivity(intent);
    }

    public void Squad4(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 4);
        startActivity(intent);
    }

    public void Squad5(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 5);
        startActivity(intent);
    }

    public void Squad6(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 6);
        startActivity(intent);
    }

    public void Squad7(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 7);
        startActivity(intent);
    }

    public void Squad8(View view) {
        Intent intent=new Intent(HomeActivity.this,Squad.class);
        intent.putExtra("buttonNumber", 8);
        startActivity(intent);
    }


}



