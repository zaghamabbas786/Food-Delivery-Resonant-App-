package com.example.fooddeliverysystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAddapter extends  RecyclerView.Adapter<CartAddapter.ViewHolder>{
    private Context context;
    private List<CartModel> list;

    public CartAddapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartAddapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_resource, parent, false);
        return new CartAddapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAddapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .into(holder.imageView);
        String procut_name = list.get(position).getProductname();
        String sellerid = list.get(position).getSellerid();
        String pricee = list.get(position).getProductprice();
        String id = list.get(position).getId();
        String img = list.get(position).getImg();
        String product_id = list.get(position).getProdct_id();
        holder.productname.setText(procut_name);
        holder.price.setText(pricee);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, product_id, Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(context, OrderCartItem.class);
                intent.putExtra("procut_name",procut_name);
                intent.putExtra("sellerid",sellerid);
                intent.putExtra("pricee",pricee);
                intent.putExtra("id",id);
                intent.putExtra("img",img);
                intent.putExtra("product_id",product_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView price, productname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.order_prduct_image);
            price = itemView.findViewById(R.id.order_product_price);
            productname = itemView.findViewById(R.id.order_product_name);
        }
    }
}
