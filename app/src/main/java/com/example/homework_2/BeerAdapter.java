package com.example.homework_2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;

    private List<Beer> selectedBeers;

    public BeerAdapter(List<Beer> beers){
        this.beers = beers;
        this.selectedBeers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        ViewHolder viewHolder = new ViewHolder(beerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Beer beer = beers.get(position);
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());
        Picasso.get().load(beer.getImage()).into(holder.imageView_beer);

        if(selectedBeers.contains(beer)){
            holder.imageView_icon.setImageDrawable(beer.getGreenSaber());

        }else{
            holder.imageView_icon.setImageDrawable(beer.getRedSaber());
        }

    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ImageView imageView_icon;
        Context context; // not sure

        public ViewHolder(View itemView) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);
            imageView_icon = itemView.findViewById(R.id.imageView_icon);
            context = textView_name.getContext(); // not sure

            imageView_beer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FourthActivity.class);
                    intent.putExtra("name", textView_name.getText().toString());
                    context.startActivity(intent);
                }
            });
            imageView_icon.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int selected = getAdapterPosition();
            Beer selectedV = beers.get(selected);
            if(selectedBeers.contains(selectedV)){
                selectedBeers.remove(selectedV);
            }else{
                selectedBeers.add(selectedV);
            }
            notifyDataSetChanged();
        }






    }

}

