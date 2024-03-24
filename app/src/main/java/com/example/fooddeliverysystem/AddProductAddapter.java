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

public class AddProductAddapter  extends RecyclerView.Adapter<AddProductAddapter.ViewHolder> {

    private Context context;
    private List<AddProductsModel> list;

    public AddProductAddapter(Context context, List<AddProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AddProductAddapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboardresourse,parent,false);
        return new AddProductAddapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductAddapter.ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).getImg())
                .into(holder.imageView);
        String procut_name = list.get(position).getProductName();
        String img = list.get(position).getImg();
        String price = list.get(position).getPrice();
        String address = list.get(position).getRestorant_address();
        String product_id = list.get(position).getProduct_id();

        holder.productname.setText(procut_name);
        holder.price.setText(list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDeleteProductAcivity.class);

                intent.putExtra("procut_name",procut_name);
                intent.putExtra("img",img);
                intent.putExtra("price",price);
                intent.putExtra("address",address);
                intent.putExtra("product_id",product_id);
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
        private TextView price,productname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prduct_image);
            price = itemView.findViewById(R.id.product_price);
            productname = itemView.findViewById(R.id.product_name);
        }
    }
}
