package com.example.drawerlayout.ui.cart

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.databinding.FragmentCartBinding
import com.example.drawerlayout.ui.cart.adapter.CartAdapter
import com.example.drawerlayout.ui.cart.adapter.CartListener
import com.example.drawerlayout.ui.cart.viewModel.CartViewModel

class CartFragment : Fragment(), CartListener {

    private lateinit var mBinding: FragmentCartBinding
    private lateinit var mAdapter: CartAdapter
    private lateinit var mCartViewModel: CartViewModel
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCartBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setRefreshFragment()
    }

    private fun setRefreshFragment() {
        mBinding.swipeRefresh.setOnRefreshListener {
            setupViewModel() // refresh your list contents somehow
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModel() {
        mCartViewModel.getItemsCart().observe(viewLifecycleOwner) { items ->
            mAdapter.submitList(items)
            var total = 0
            for (item in items) total += item.price
            mBinding.articleCount.text = "Articles X${items.size}"
            mBinding.total.text = "Total: €$total"
            mBinding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = mBinding.recyclerView
        mAdapter = CartAdapter(this)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = linearLayoutManager
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onClick(cartEntity: CartEntity, v: View, menuRes: Int) {
        val popup = PopupMenu(requireContext(),v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.delete -> {
                    mCartViewModel.deleteItem(cartEntity)
                }
            }
            true
        }
        popup.show()
    }

    override fun swipe(cartEntity: CartEntity) {
        Toast.makeText(context,cartEntity.name,Toast.LENGTH_SHORT).show()
        val recyclerView = mBinding.recyclerView
        val swipeToDeleteCallback = object :SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mCartViewModel.deleteItem(cartEntity)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}