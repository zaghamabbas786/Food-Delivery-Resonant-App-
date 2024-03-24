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

public class CustomerOrderAdapter extends  RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder>{
    private Context context;
    private List<OrderModel> list;
    private String user;

    public CustomerOrderAdapter(Context context, List<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    public CustomerOrderAdapter(Context context, List<OrderModel> list, String  user) {
        this.context = context;
        this.list = list;
        this.user = user;
    }

    @NonNull
    @Override
    public CustomerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_resource, parent, false);
        return new CustomerOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderAdapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .into(holder.imageView);
        String procut_name = list.get(position).getProduct_name();
        String seller_id = list.get(position).getSeller_id();
        String pricee = list.get(position).getPrice();
        String orderid = list.get(position).getOrder_id();
        String product_id = list.get(position).getProduct_id();
        String status = list.get(position).getStatus();
        String location = list.get(position).getLocation();
        String img = list.get(position).getImg();
        holder.productname.setText(procut_name);
        holder.price.setText(pricee);

        if (user.equals("customer_id")){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                Toast.makeText(context, pricee, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(holder.itemView.getContext(),OrderDetailAcitivity.class);
                    intent.putExtra("img",img);
                intent.putExtra("procut_name",procut_name);
//                intent.putExtra("product_id",product_id);
//                intent.putExtra("seller_id",seller_id);
                    intent.putExtra("orderid",orderid);
                    intent.putExtra("pricee",pricee);
//                intent.putExtra("status",status);
//                intent.putExtra("location",location);
                    context.startActivity(intent);
                }
            });
        }
        else {

        }

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
