package com.example.technicalassingmentturkcell.network.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products", indices = [Index(value = ["id"], unique = true)])
data class Product(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    @ColumnInfo(name = "id")
    @SerializedName("product_id")
    val id: String,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: Int,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String
)