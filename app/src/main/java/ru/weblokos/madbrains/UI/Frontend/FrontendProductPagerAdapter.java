package ru.weblokos.madbrains.UI.Frontend;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.weblokos.madbrains.Model.Product;

public class FrontendProductPagerAdapter extends FragmentStatePagerAdapter {

    private List<? extends Product> mProductList;

    public void setProductList(final List<? extends Product> ProductList) {
        mProductList = ProductList;
        notifyDataSetChanged();
    }

    public FrontendProductPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        return FrontendProductFragment.newInstance(mProductList.get(index).getId());
        /*switch (index) {
            case 0:
                return new PrearrivalPlan();
            case 1:
                return new PrimarySurvey();
            case 2:
                return new Vitals();
            case 3:
                return new SecondarySurvey();
            case 4:
                return new PrepareForTravel();
        }

        return null;*/
    }

    @Override
    public int getCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}