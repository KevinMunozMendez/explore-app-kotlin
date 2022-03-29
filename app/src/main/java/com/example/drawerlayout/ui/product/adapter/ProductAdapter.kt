package com.example.drawerlayout.ui.product.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.CommentEntity
import com.example.drawerlayout.common.entities.LoggedInUser
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.databinding.ItemCommentBinding
import com.example.drawerlayout.ui.products.adapter.OnClickListener

class ProductAdapter (private var comments: List<CommentEntity>, private var userLoggedInUser: LoggedInUserView? = null,
                      private var listener: OnClickListenerProduct):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        with(holder) {
            setListener(comment)
            userLoggedInUser?.let { user ->
                if (comment.userId?.email == user.email) {
                    binding.optionMenu.visibility = View.VISIBLE
                } else {
                    binding.optionMenu.visibility = View.INVISIBLE
                }
            }
            binding.titleName.text = comment.userId?.user
            binding.comment.text = comment.comment
            comment.userId?.urlImg.let {
                Glide.with(mContext)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.imgUser)
            }
        }
    }

    override fun getItemCount(): Int = comments.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateComments(comments: List<CommentEntity>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUser(user: LoggedInUserView?) {
        this.userLoggedInUser = user
        notifyDataSetChanged()
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCommentBinding.bind(view)

        private val txtIl = binding.txtIlEdit
        private val txtEdit = binding.txtEditEdit
        private val btnEdit = binding.checkEdit

        fun setListener(commentEntity: CommentEntity){

            binding.optionMenu.setOnClickListener { v: View ->
                listener.onClick(
                    commentEntity,
                    txtIl,
                    txtEdit,
                    btnEdit,
                    v, R.menu.menu_item_comment
                )
            }
            binding.checkEdit.setOnClickListener {
                txtIl.visibility = View.GONE
                btnEdit.visibility = View.GONE
                listener.send(commentEntity, txtEdit.text.toString())
            }
        }
    }
}