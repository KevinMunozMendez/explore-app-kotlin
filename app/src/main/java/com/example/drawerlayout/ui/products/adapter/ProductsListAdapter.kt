package com.example.drawerlayout.ui.products.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.databinding.ItemProductBinding

class ProductsListAdapter(private var cities: MutableList<ProductEntity>, private var listener: OnClickListener):
    RecyclerView.Adapter<ProductsListAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        with(holder) {
            setListener(city)
            binding.brandItem.text = city.brand
            binding.priceItem.text = "€"+city.price.toString()
            binding.titleItem.text = city.name
            Glide.with(mContext)
                .load(city.coverImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imageCity)
        }
    }

    override fun getItemCount(): Int = cities.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStores(cities: MutableList<ProductEntity>) {
        this.cities = cities
        notifyDataSetChanged()
    }


    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemProductBinding.bind(view)

        fun setListener(productEntity: ProductEntity){
            binding.seeMore.setOnClickListener { listener.onClick(productEntity) }
        }
    }

}