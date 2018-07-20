package ru.weblokos.madbrains.UI.Backend;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.Model.Product;
import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.ViewModel.ProductListViewModel;
import ru.weblokos.madbrains.databinding.BackendFragmentProductListBinding;

public class BackendProductListFragment extends Fragment implements ProductListNavigator {

    private BackendFragmentProductListBinding binding;
    private BackendProductListAdapter mProductAdapter;

    public BackendProductListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.backend_fragment_product_list, container, false);
        mProductAdapter = new BackendProductListAdapter(mProductClickCallback);
        binding.productList.setAdapter(mProductAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductListViewModel.Factory factory = new ProductListViewModel.Factory(
                getActivity().getApplication(), this);
        final ProductListViewModel model = ViewModelProviders.of(this, factory)
                .get(ProductListViewModel.class);
        binding.setModel(model);
        subscribeToModel(model);
    }

    private void subscribeToModel(final ProductListViewModel model) {
        model.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (productEntities != null) {
                    binding.setIsLoading(false);
                    mProductAdapter.setProductList(productEntities);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }

    private final ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(new Intent(getActivity(), BackendProductActivity.class).putExtra("product", product.getId()));
            }
        }
    };

    @Override
    public void onAddClick() {
        startActivity(new Intent(getActivity(), BackendProductActivity.class));
    }
}
