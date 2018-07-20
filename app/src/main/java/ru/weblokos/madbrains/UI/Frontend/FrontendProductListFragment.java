package ru.weblokos.madbrains.UI.Frontend;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.weblokos.madbrains.DB.Entity.ProductEntity;
import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.ViewModel.ProductBuyListViewModel;
import ru.weblokos.madbrains.databinding.FrontendFragmentProductListBinding;

public class FrontendProductListFragment extends Fragment {

    private FrontendFragmentProductListBinding binding;
    private FrontendProductPagerAdapter mProductAdapter;

    public FrontendProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frontend_fragment_product_list, container, false);
        mProductAdapter = new FrontendProductPagerAdapter(getChildFragmentManager());
        binding.productList.setAdapter(mProductAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductBuyListViewModel.Factory factory = new ProductBuyListViewModel.Factory(
                getActivity().getApplication(), null);
        final ProductBuyListViewModel model = ViewModelProviders.of(this, factory)
                .get(ProductBuyListViewModel.class);
        binding.setModel(model);
        subscribeToModel(model);
    }

    private void subscribeToModel(final ProductBuyListViewModel model) {
        model.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (productEntities != null) {
                    List<ProductEntity> products = new ArrayList<>();
                    for(int i = 0; i < productEntities.size(); i++) {
                        if(productEntities.get(i).getCount() > 0)
                            products.add(productEntities.get(i));
                    }
                    binding.setIsLoading(false);
                    mProductAdapter.setProductList(products);

                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }
}
