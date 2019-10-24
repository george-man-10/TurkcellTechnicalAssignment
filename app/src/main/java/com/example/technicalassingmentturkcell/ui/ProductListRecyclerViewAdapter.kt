package com.example.technicalassingmentturkcell.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.technicalassingmentturkcell.R
import com.example.technicalassingmentturkcell.network.models.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ProductListRecyclerViewAdapter(private val listener: OnItemClickedListener) :
    RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductViewHolder>() {
    var productsList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
    )

    override fun getItemCount() = productsList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product)
        holder.id = position
    }

    fun updateRecyclerView() {
        notifyDataSetChanged()
    }

    private fun onItemClicked(itemPosition: Int) {
        listener.onItemClicked(itemPosition)
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id = -1
        private var productName = view.tv_product_name
        private var productPrice = view.tv_product_price
        private var productImage = view.iv_product_image
        private var cardView = view.card_view

        init {
            cardView.setOnClickListener {
                onItemClicked(id)
            }
        }

        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = product.price.toString()
            val circularProgressDrawable = CircularProgressDrawable(productImage.context)
            circularProgressDrawable.start()
            Glide.with(productImage.context)
                .load(product.image)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_broken_image)
                .into(productImage)
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(itemPosition: Int)
    }
}