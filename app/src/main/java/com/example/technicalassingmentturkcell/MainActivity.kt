package com.example.technicalassingmentturkcell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.technicalassingmentturkcell.ui.fragments.ProductDetailsFragment
import com.example.technicalassingmentturkcell.ui.fragments.ProductListFragment


const val FRAGMENT_TAG = "A"

class MainActivity : AppCompatActivity(), ProductListFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, ProductListFragment.newInstance(), FRAGMENT_TAG)
            .commit()
    }
    override fun showProductsDetailsFragment(elementId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, ProductDetailsFragment.newInstance(elementId))
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}
