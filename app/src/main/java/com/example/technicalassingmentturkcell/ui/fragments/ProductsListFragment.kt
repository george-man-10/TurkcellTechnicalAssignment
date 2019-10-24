package com.example.technicalassingmentturkcell.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technicalassingmentturkcell.R
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.ui.ProductListRecyclerViewAdapter
import com.example.technicalassingmentturkcell.utils.Resource
import com.example.technicalassingmentturkcell.viewmodels.ProductsListViewModel

const val TAG = "ProductListFragment"

class ProductListFragment : BaseListFragment<Product>(),
    ProductListRecyclerViewAdapter.OnItemClickedListener {
    lateinit var productsViewModel: ProductsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        productsViewModel = ViewModelProviders.of(this).get(ProductsListViewModel::class.java)
        return inflater.inflate(R.layout.products_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_products_list)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductListRecyclerViewAdapter(this@ProductListFragment)
        recyclerView?.adapter = adapter
        subscribeObservers()
        productsViewModel.fetchProductsList()
    }

    private fun subscribeObservers() {
        productsViewModel.getRequestResult().observe(this, Observer<Resource<List<Product>>> { it ->
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        adapter?.productsList = it as MutableList<Product>
                        Log.d(TAG, "List in adapter: ${adapter?.productsList}")
                        adapter?.updateRecyclerView()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onItemClicked(itemPosition: Int) {
        val listener = activity
        if (listener is OnFragmentInteractionListener) {
            listener.showProductsDetailsFragment(itemPosition)
        }
    }

    interface OnFragmentInteractionListener {
        fun showProductsDetailsFragment(elementId: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductListFragment()
    }

}
