package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class ShowProductAdapter extends RecyclerView.Adapter<ShowProductAdapter.MyViewHolder> {
    String[] personNames;
    Integer[] personImages;
    Context context;
    public ShowProductAdapter(Context context, String[] personNames, Integer[] personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_gridproducts, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(personNames[position]);
        holder.image.setImageResource(personImages[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                /*Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("image", personImages.get(position)); // put image data in Intent
                context.startActivity(intent); // start Intent*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return personNames.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
