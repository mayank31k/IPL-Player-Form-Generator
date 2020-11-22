package com;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rawggar.DetailActivity;
import com.rawggar.sample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder>  {
    private ArrayList<ParseItem> parseItems;
    private Context context;

    public ParseAdapter(ArrayList<ParseItem> parseItems,Context context)
    {
        this.parseItems=parseItems;
        this.context=context;
    }
    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parse_squad,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
           ParseItem parseItem=parseItems.get(i);
           viewHolder.textView.setText(parseItem.getTitle());
        Glide.with(viewHolder.itemView.getContext()).load("https://www.espncricinfo.com"+parseItem.getImgurl()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {

        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Image_view);
            textView=itemView.findViewById(R.id.Text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            ParseItem parseItem=parseItems.get(position);
            Intent intent=new Intent(context, DetailActivity.class);
            intent.putExtra("title",parseItem.getTitle());
            intent.putExtra("imgurl",parseItem.getImgurl());
            intent.putExtra("playerid",parseItem.getPlayerid());
            context.startActivity(intent);
        }
    }
}
