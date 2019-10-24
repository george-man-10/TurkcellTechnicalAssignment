package com.example.technicalassingmentturkcell.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.technicalassingmentturkcell.network.models.Product

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<Product>>
}