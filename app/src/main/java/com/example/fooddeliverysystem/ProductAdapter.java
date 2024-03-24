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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<AddProductsModel> productModels;
    private Context context;

    public ProductAdapter(List<AddProductsModel> productModels, Context context) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboardresourse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(productModels.get(position).getImg())
                .into(holder.imageView);
        String procut_name = productModels.get(position).getProductName();
        String id = productModels.get(position).getUser_id();
        String price = productModels.get(position).getPrice();
        String product_id = productModels.get(position).getProduct_id();
        String img = productModels.get(position).getImg();
        holder.productname.setText(procut_name);
        holder.price.setText(price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OrderPage.class);
                intent.putExtra("price", price);
                intent.putExtra("name", procut_name);
                intent.putExtra("img", procut_name);
                intent.putExtra("id", id);
                intent.putExtra("product_id", product_id);
                intent.putExtra("img", img);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public void FilterSerach(List<AddProductsModel> models) {
        productModels = models;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView price, productname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prduct_image);
            price = itemView.findViewById(R.id.product_price);
            productname = itemView.findViewById(R.id.product_name);

        }

    }
}
