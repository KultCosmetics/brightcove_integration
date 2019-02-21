package com.kult.brightcove;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kult.R;
import com.kult.models.Product;

import java.util.List;

public class Adapter_Products extends RecyclerView.Adapter<Adapter_Products.ProductViewHolder> {
    private List<Product> productList;
    private Context mContext;
    private SeekBarListener seekBarListener;

    public void updateProductList(List<Product> p) {
        this.productList = p;
    }

    public Adapter_Products(Context context, List<Product> p) {
        mContext = context;
        productList = p;
        seekBarListener = (SeekBarListener) context;
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nyx_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(holder.getAdapterPosition());

        holder.ivProductImage.invalidate();
        String productImage = product.getImageUrl();
        Log.d("poo", "product:   " + productImage);
        Glide.with(mContext).load(productImage).centerCrop()
                .placeholder(R.drawable.ic_film)
                .error(R.drawable.ic_film)
                .into(holder.ivProductImage);

        holder.tvProductTitle.setText(product.getTitle());
        holder.tvSeekTime.setText(String.valueOf(product.getSeek_time()));

        holder.cell.setOnClickListener(v -> {
            seekBarListener.moveSeekBarTo(product.getSeek_time());
        });

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivProductImage;
        protected TextView tvProductTitle;
        protected TextView tvSeekTime;
        public View cell;


        private ProductViewHolder(View view) {
            super(view);
            this.ivProductImage = view.findViewById(R.id.iv_image);
            this.tvProductTitle = view.findViewById(R.id.tv_title);
            this.tvSeekTime = view.findViewById(R.id.tv_seek_time);
            cell = view;
        }
    }

    public interface SeekBarListener {
        void moveSeekBarTo(float msec);
    }
}