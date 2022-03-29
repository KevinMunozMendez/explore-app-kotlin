package com.example.drawerlayout.ui.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.databinding.ItemCartBinding
import com.example.drawerlayout.ui.products.adapter.OnClickListener
import kotlin.coroutines.coroutineContext

class CartAdapter(private var listener: CartListener):
    ListAdapter<CartEntity, RecyclerView.ViewHolder>(CartDiffCallback()){

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val items = getItem(position)
        with(holder as ViewHolder) {
            setListener(items)
            binding.descriptionItem.text = items.description
            binding.titleItem.text = items.name
            binding.priceItem.text = "€" + (items.price).toString()
            binding.sum.text = (items.amount).toString()
            Glide.with(mContext)
                .load(items.coverImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgItem)
        }
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCartBinding.bind(view)

        @SuppressLint("ClickableViewAccessibility")
        fun setListener(cartEntity: CartEntity){
            binding.optionMenu.setOnClickListener { v: View ->
                listener.onClick(cartEntity, v, R.menu.menu_item_cart)
            }

//            binding.cardView.setOnTouchListener { v, event ->
//                listener.swipe(cartEntity)
//                true
//            }
        }
    }

    class CartDiffCallback: DiffUtil.ItemCallback<CartEntity>(){
        override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem == newItem
        }
    }
}