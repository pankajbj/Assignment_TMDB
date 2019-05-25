package com.example.pankaj.assignment_tmdb;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<MovieResults.Result> list=new ArrayList<>();
    Context context;
    ///LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<MovieResults.Result> listt){
        this.context=context;
        this.list=listt;
       // layoutInflater=LayoutInflater.from(context);
        }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
       // MyViewHolder holder=new MyViewHolder(itemView);
        int n=list.size();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.title.setText(list.get(i).getTitle());
        String img=list.get(i).getPosterPath();

        Picasso.with(context)
                .load(img)
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(myViewHolder.iv);

        if (i==list.size()){
        myViewHolder.v.setVisibility(View.INVISIBLE);
    }
    myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_detail);
            ImageView iv_userProfile = (ImageView) dialog.findViewById(R.id.iv_user);

            TextView tv_Userusername = dialog.findViewById(R.id.tv_Userusername);
            TextView tv_Useremail = dialog.findViewById(R.id.tv_Useremail);
            TextView tv_Usertype = dialog.findViewById(R.id.tv_Usertype);
            String releasedate=list.get(i).getReleaseDate();

            tv_Userusername.setText(list.get(i).getTitle());
            tv_Useremail.setText(list.get(i).getOverview());
            tv_Usertype.setText("Release Date :"+""+releasedate);
            String imgPath=list.get(i).getPosterPath();
            Picasso.with(context)
                    .load(imgPath)
                    .fit()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(iv_userProfile);

            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        }
    });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv;
        View v;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_item);
            v=itemView.findViewById(R.id.v);
        iv=itemView.findViewById(R.id.iv);
        }
    }
}
