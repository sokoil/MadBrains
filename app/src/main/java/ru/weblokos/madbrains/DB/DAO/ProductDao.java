package ru.weblokos.madbrains.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.weblokos.madbrains.DB.Entity.ProductEntity;

@Dao
public interface ProductDao {

    @Query("select * from products")
    LiveData<List<ProductEntity>> loadAllProducts();

    /*@Query("select * from products where count > 0")
    LiveData<List<ProductEntity>> loadBuyableProducts();*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);

    @Query("select * from products where id = :product")
    LiveData<ProductEntity> loadProduct(int product);

    @Update
    void update(ProductEntity product);
}
