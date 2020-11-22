package com.rawggar.sample;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.ParseAdapter;
import com.ParseItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Squad extends AppCompatActivity {
    int pressedButtonNumber;
    private RecyclerView recyclerView;
    private ParseAdapter adapter,adap;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    public String url;
    SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squad);
        searchView=findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout=findViewById(R.id.swipeLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);
        pressedButtonNumber = getIntent().getExtras().getInt("buttonNumber");
        db= FirebaseDatabase.getInstance().getReference().child(Integer.toString(pressedButtonNumber));
        db.removeValue();
        db= FirebaseDatabase.getInstance().getReference().child(Integer.toString(pressedButtonNumber));
        switch (pressedButtonNumber) {
            case 1:
                url = "https://www.espncricinfo.com/ci/content/squad/1210602.html";
                break;
            case 2:
                url="https://www.espncricinfo.com/ci/content/squad/1210601.html";
                break;
            case 3:
                url="https://www.espncricinfo.com/ci/content/squad/1210609.html";
                break;
            case 4:
                url="https://www.espncricinfo.com/ci/content/squad/1210598.html";
                break;
            case 5:
                url="https://www.espncricinfo.com/ci/content/squad/1210611.html";
                break;
            case 6:
                url="https://www.espncricinfo.com/ci/content/squad/1210599.html";
                break;
            case 7:
                url="https://www.espncricinfo.com/ci/content/squad/1210607.html";
                break;
            case 8:
                url="https://www.espncricinfo.com/ci/content/squad/1210597.html";
                break;
        }
        loadContent();
        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
            }
        });
    }
    private void search(String s){
        ArrayList<ParseItem> items=new ArrayList<>();
        for(ParseItem item:parseItems)
        {
            if(item.getTitle().toLowerCase().contains(s.toLowerCase()))
            {
                items.add(item);
            }
        }
        adap = new ParseAdapter(items, this);
        recyclerView.setAdapter(adap);
    }

    private void loadContent() {
        Content content=new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Squad.this, android.R.anim.fade_in));
            parseItems.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Squad.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Document doc = Jsoup.connect(url).get();
                Elements data = doc.select("div.large-7.medium-7.small-7.columns");
                int size = data.size();
                for (int i = 0; i < size; i++) {
                    String imgurl = data.select("img").eq(i).attr("src");
                    String title = data.select("img").eq(i).attr("title");
                    final String playerlink = data.select("a").eq(i).attr("href");
                    String playerid = getid(playerlink);
                    db.child(title).setValue(playerid);
                    parseItems.add(new ParseItem(imgurl, title, playerid));
                    //Log.d("items", "img: " + imgurl + "title: " + title + " id: " + playerid + " link:" + playerlink);
                }
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private String getid(String playerlink) {
        int slash=0;
        String id="";
        for(int i=0;i<playerlink.length()-5;i++)
        {
            if(slash==4)
            {
                id+=playerlink.charAt(i);
            }
            if(playerlink.charAt(i)=='/')
                slash++;

        }
        return id;
    }

}
