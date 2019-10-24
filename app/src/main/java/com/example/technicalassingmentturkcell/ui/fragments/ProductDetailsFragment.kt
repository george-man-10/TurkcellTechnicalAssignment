package com.example.technicalassingmentturkcell.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.technicalassingmentturkcell.R
import com.example.technicalassingmentturkcell.network.models.Product
import kotlinx.android.synthetic.main.product_item.*

const val ARG_ITEM_ID = "id"

class ProductDetailsFragment : BaseListFragment<Product>() {
    private var param: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.let {
            param = it.getInt(ARG_ITEM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_product_name.text = param.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(param: Int) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_ID, param)
                }
            }
    }
}