package ru.weblokos.madbrains.UI.Backend;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.weblokos.madbrains.Model.Product;
import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.databinding.ProductItemBinding;


public class BackendProductListAdapter extends RecyclerView.Adapter<BackendProductListAdapter.ProductViewHolder> {

    List<? extends Product> mProductList;
    @Nullable
    private final ProductClickCallback mProductClickCallback;


    public BackendProductListAdapter(@Nullable ProductClickCallback productClickCallback) {
        this.mProductClickCallback = productClickCallback;
    }

    public void setProductList(final List<? extends Product> ProductList) {
        if (mProductList == null) {
            mProductList = ProductList;
            notifyItemRangeInserted(0, ProductList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductList.size();
                }

                @Override
                public int getNewListSize() {
                    return ProductList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProductList.get(oldItemPosition).getId() ==
                            ProductList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product newProduct = ProductList.get(newItemPosition);
                    Product oldProduct = mProductList.get(oldItemPosition);
                    return newProduct.getName().equalsIgnoreCase(oldProduct.getName())
                            && newProduct.getPrice() == oldProduct.getPrice()
                            && newProduct.getCount() == oldProduct.getCount();
                }
            });
            mProductList = ProductList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item, parent,
                        false);
        binding.setCallback(mProductClickCallback);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.binding.setProduct(mProductList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        final ProductItemBinding binding;

        public ProductViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}