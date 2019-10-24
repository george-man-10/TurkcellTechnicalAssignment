package com.example.technicalassingmentturkcell.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.technicalassingmentturkcell.R
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.ui.ProductListRecyclerViewAdapter

open class BaseListFragment<T> :
    BaseProgressFragment<T>() {
    lateinit var productName: TextView
    lateinit var productPrice: TextView
    lateinit var productImage: ImageView
    lateinit var productDescription: TextView
    var adapter: ProductListRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.products_list_fragment, container, false)
        return view
    }

    fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun updateList(list: List<T>) {
        adapter?.productsList = list as MutableList<Product>
        adapter?.updateRecyclerView()
    }

    fun updateView(result: Product) {
        productName.text = result.name
        productPrice.text = result.price.toString()
//        productDescription.text = result.description
        Glide.with(productImage.context).load(result.image).into(productImage)
    }
}