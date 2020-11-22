package com.rawggar;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ParseItem;
import com.bumptech.glide.Glide;
import com.rawggar.sample.R;
import com.rawggar.sample.Squad;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView  titleTextview,detailTextview;
    private String bothform;
    private Button formBtn;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.Image_view);
        titleTextview=findViewById(R.id.Text_view);
        progressBar=findViewById(R.id.progress_circular);
        detailTextview=findViewById(R.id.detailTextview);
        detailTextview.setPaintFlags(detailTextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        titleTextview.setText(getIntent().getStringExtra("title"));
        Glide.with(this).load("https://www.espncricinfo.com"+getIntent().getStringExtra("imgurl")).into(imageView);
        final Content content=new Content();
        formBtn=(Button)findViewById(R.id.formBtn);
        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                content.execute();
            }

        });


    }

    private class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detailTextview.setText(bothform);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                   String baturl = "https://stats.espncricinfo.com/ci/engine/player/"+getIntent().getStringExtra("playerid")+".html?class=3;template=results;type=batting;view=reverse_cumulative";
                    String bowlurl = "https://stats.espncricinfo.com/ci/engine/player/"+getIntent().getStringExtra("playerid")+".html?class=3;template=results;type=bowling;view=reverse_cumulative";
                    Document document = Jsoup.connect(baturl).get();
                    Elements elements = document.select("tbody");
                    String batform="",bowlform="";
                    int form=0,size=elements.size();
                    for (int j = 0; j < size; j++) {
                        String stat = elements.select("tr.data1").eq(j).text();
                      Log.d(" bat scores: ", "val: " + stat);
                        if (j ==5) {
                           form = sendbat(stat);
                           Log.d("bat form: ", "val: " + form);
                        }
                    }
                    batform=getform(form);
                    form=0;
                     document = Jsoup.connect(bowlurl).get();
                    elements = document.select("tbody");
                    size=elements.size();
                    for (int j = 0; j < size; j++) {
                        String stat = elements.select("tr.data1").eq(j).text();
                        Log.d("bowl scores: ", "val: " + stat);
                        if (j ==5) {
                          form = sendbowl(stat);
                          Log.d("bowl form: ", "val: " + form);
                        }
                    }
                    bowlform=getform(form);
                    bothform="Batting form-"+batform +"\n\n " +"Bowling form - "+bowlform;

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private String getform(int form)
    {
        String f="";
        switch (form)
        {   case 0:f="No recent records";break;
            case 1:f="very poor";break;
            case 2:f="poor";break;
            case 3:f="average";break;
            case 4:f="good";break;
            case 5:f="excellent";break;

        }
        return f;
    }

    private int sendbat(String stat) {
        int spaces=0;
        String avg="";
        for(int i=0;i<stat.length();i++)
        {
            if(stat.charAt(i)==' ')
                spaces++;
            if(spaces==5)
                avg+=stat.charAt(i);

            if(spaces==6)
                break;
        }
       int ans=0;
        float f=0;
            try {
                f=Float.parseFloat(avg);
            } catch (NumberFormatException nfe) {
                return ans;
            }
            if (f > 50)
                ans = 5;
            else if (f > 35)
                ans = 4;
            else if (f > 25)
                ans = 3;
            else if (f > 10)
                ans = 2;
            else
                ans = 1;

            return ans;

    }

    private int sendbowl(String stat) {
        int spaces=0;
        String avg="";
        for(int i=0;i<stat.length();i++)
        {
            if(stat.charAt(i)==' ')
                spaces++;

            if(spaces==7)
                avg+=stat.charAt(i);

            if(spaces==8)
                break;
        }
        int ans=0;
         float f=0;
        try {
            f=Float.parseFloat(avg);
        } catch (NumberFormatException nfe) {
            return ans;
        }

            if (f > 50)
                ans = 1;
            else if (f > 35)
                ans = 2;
            else if (f > 25)
                ans = 3;
            else if (f > 10)
                ans = 4;
            else
                ans = 5;

            return ans;
        }
    }

