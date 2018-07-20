package ru.weblokos.madbrains.DB.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import ru.weblokos.madbrains.Model.Product;

@Entity(tableName = "products")
public class ProductEntity extends BaseObservable implements Product { // продукт

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private double price;
    private int count;

    public ProductEntity() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    @Bindable
    public String getCountString() {
        return String.valueOf(getCount());
    }

    @Override
    @Bindable
    public String getPriceString() {
        return String.format("%.2f", getPrice());
    }

    public void setCountString(String count) {
        if(count.length() > 0)
            setCount(Integer.valueOf(count)); else
            setCount(0);
    }

    public void setPriceString(String price) {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            if (price.length() > 0)
                //setPrice(Double.valueOf(price)); else
                setPrice(format.parse(price).doubleValue());
            else
                setPrice(0);
        } catch (ParseException e) {
            setPrice(0);
        }
    }

    public void buyProduct(int count) {
        this.count -= count;
    }
    //private PropertyChangeRegistry registry = new PropertyChangeRegistry();

}
