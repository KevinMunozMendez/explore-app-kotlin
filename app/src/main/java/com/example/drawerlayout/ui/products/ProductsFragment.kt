package com.example.drawerlayout.ui.products

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.ui.products.adapter.ProductsListAdapter
import com.example.drawerlayout.databinding.FragmentProductsBinding
import com.example.drawerlayout.ui.product.ProductFragment
import com.example.drawerlayout.ui.product.viewModel.OneProductViewModel
import com.example.drawerlayout.ui.products.adapter.OnClickListener
import com.example.drawerlayout.ui.products.viewModel.ProductViewModel
import java.nio.file.WatchEvent

class ProductsFragment: Fragment(), OnClickListener {

    private lateinit var mGridLayout: GridLayoutManager
    private lateinit var mBinding: FragmentProductsBinding
    private lateinit var mAdapter: ProductsListAdapter

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mOneProductViewModel: OneProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProductViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        mOneProductViewModel = ViewModelProvider(requireActivity())[OneProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentProductsBinding.inflate(inflater, container, false)
        return mBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mProductViewModel.setProducts()
        mProductViewModel.getStores().observe(viewLifecycleOwner) { products ->
            mBinding.progressBar.visibility = View.GONE
            mAdapter.setStores(products)
            search(products)
        }
        setupActionBar()
    }

    private fun search (products: MutableList<ProductEntity>) {
        mBinding.txtEditSearch.addTextChangedListener {
            val value = mBinding.txtEditSearch.text.toString().lowercase().trim()
            val newProduct = products.filter { i ->
                i.name.lowercase().trim().contains(value)
            }
            mAdapter.setStores(newProduct as MutableList<ProductEntity>)
        }
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.shopCart -> {
                Navigation.findNavController(mBinding.root).navigate(R.id.nav_cart)
                true
            }
            R.id.search -> {
                if (mBinding.txtIlSearch.visibility == View.GONE) {
                    mBinding.txtIlSearch.visibility = View.VISIBLE
                } else {
                    mBinding.txtIlSearch.visibility = View.GONE
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        mAdapter = ProductsListAdapter(mutableListOf(),this)
        mGridLayout = GridLayoutManager(context,2)
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mGridLayout
        }
    }

    override fun onClick(productEntity: ProductEntity) {
        launchProductFragment(productEntity)
        Navigation.findNavController(requireView()).navigate(R.id.nav_product)
    }

    private fun launchProductFragment(productEntity: ProductEntity) {
        mOneProductViewModel.setProductSelected(productEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        setHasOptionsMenu(true)
        super.onDestroy()
    }

}