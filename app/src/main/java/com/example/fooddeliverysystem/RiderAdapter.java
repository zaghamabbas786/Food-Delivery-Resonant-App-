package com.example.fooddeliverysystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RiderAdapter extends RecyclerView.Adapter<RiderAdapter.ViewHolder> {
    private Context context;
    private List<OrderModel> list;

    public RiderAdapter(Context context, List<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RiderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_resource, parent, false);
        return new RiderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiderAdapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .into(holder.imageView);
        String procut_name = list.get(position).getProduct_name();
        String id = list.get(position).getSeller_id();
        String price = list.get(position).getPrice();
        String orderid = list.get(position).getOrder_id();
        String product_id = list.get(position).getProduct_id();
        String status = list.get(position).getStatus();
        String location = list.get(position).getLocation();
        String img = list.get(position).getImg();
        holder.productname.setText(procut_name);
        holder.price.setText(price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustommerOrderToDeliverd.class);
                intent.putExtra("img", img);
                intent.putExtra("procut_name", procut_name);
                intent.putExtra("seller_id", id);
                intent.putExtra("price", price);
                intent.putExtra("product_id", product_id);
                intent.putExtra("status", status);
                intent.putExtra("location", location);
                intent.putExtra("orderid", orderid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
