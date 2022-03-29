package com.example.drawerlayout.ui.product

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.common.entities.CommentEntity
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.databinding.FragmentProductBinding
import com.example.drawerlayout.ui.cart.viewModel.CartViewModel
import com.example.drawerlayout.ui.login.viewModel.LoginViewModel
import com.example.drawerlayout.ui.product.adapter.OnClickListenerProduct
import com.example.drawerlayout.ui.product.adapter.ProductAdapter
import com.example.drawerlayout.ui.product.viewModel.OneProductViewModel
import com.example.drawerlayout.ui.products.adapter.OnClickListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType

class ProductFragment : Fragment(), OnClickListenerProduct {

    private lateinit var mBinding: FragmentProductBinding
    private lateinit var mOneProductViewModel: OneProductViewModel
    private lateinit var mCartViewModel: CartViewModel
    private lateinit var mLoginViewModel: LoginViewModel
    private lateinit var mAdapter: ProductAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var mProductEntity: ProductEntity

    private var token:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOneProductViewModel = ViewModelProvider(requireActivity())[OneProductViewModel::class.java]
        mCartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        mLoginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProductBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnSend.isEnabled = false
        mBinding.txtIlComments.isEnabled = false
        setupViewModel()
        setupActionBar()
        setCommentsRecyclerView()
    }

    private fun sendComment(productEntity: ProductEntity) {
        val comment = mBinding.txtEditComments.text.toString()
        productEntity._id?.let { id ->
            token?.let { token ->
            mOneProductViewModel.setComment(comment, token, id) }
        }
    }

    private fun buy (cartEntity: CartEntity) {
        mCartViewModel.addItem(cartEntity)
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_like, menu)
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.shopCart -> {
                Navigation.findNavController(mBinding.root).navigate(R.id.nav_cart)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        mLoginViewModel.loginResult.observe(viewLifecycleOwner, { loginResult ->
            loginResult?.success?.let { userLogged ->
                mAdapter.updateUser(userLogged)
                token = userLogged.token
                mBinding.btnSend.isEnabled = true
                mBinding.txtIlComments.isEnabled = true
            }
            loginResult.logout?.let {
                mBinding.btnSend.isEnabled = false
                mBinding.txtIlComments.isEnabled = false
            }
        })
        mOneProductViewModel.getStoreSelected().observe(viewLifecycleOwner ,{
            mProductEntity = it
            it.comments?.let { comments -> mAdapter.updateComments(comments) }
            if (it != null) setUiProduct(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUiProduct(productEntity: ProductEntity) {
        val discount = (productEntity.discount * productEntity.price) / 100
//        val list = mutableListOf<CarouselItem>()
//        for (images in productEntity.productsImages) list.add(CarouselItem(images.photo))
        listener(productEntity)
        with(mBinding) {
//            productEntity.comments?.let {
//                setCommentsRecyclerView(it)
//            }
//            content.carousel.addData(list)
            titleItem.text = productEntity.name
            brandItem.text = productEntity.brand
            descriptionItem.text = productEntity.description
            priceItem.text = "€" + productEntity.price.toString()
            priceItem.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            discountItem.text = "€" + (productEntity.price - discount).toString()
            txtDiscountItem.text = productEntity.discount.toString() + "% Discount"
            txtDiscountItem.setTextColor(resources.getColor(R.color.red))
            Glide.with(this@ProductFragment)
                .load(productEntity.coverImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageItem)
        }
    }

    private fun listener(productEntity: ProductEntity) {
        val discount = (productEntity.discount * productEntity.price) / 100
        mBinding.buy.setOnClickListener {
            buy(CartEntity(
                coverImage = productEntity.coverImage,
                price = (productEntity.price - discount),
                name = productEntity.name,
                description = productEntity.description)
            )
        }
        mBinding.btnSend.setOnClickListener {
            sendComment(productEntity)
        }
    }

    private fun setCommentsRecyclerView() {
        mAdapter = ProductAdapter(listOf(),null,this)
        linearLayoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onClick(
        commentEntity: CommentEntity,
        txtIl: TextInputLayout,
        txtEdit: TextInputEditText,
        btnEdit: ImageButton,
        v: View, menuRes: Int
    ) {
        val popup = PopupMenu(requireContext(),v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.delete -> {
                    mProductEntity._id?.let { idArticle ->
                        mOneProductViewModel.setDeleteComment(commentEntity._id, idArticle)
                    }
                }
                R.id.edit -> {
                    txtIl.visibility = View.VISIBLE
                    btnEdit.visibility = View.VISIBLE
                    val comment: String = commentEntity.comment
                    txtEdit.text = comment.editable()
                }
            }
            true
        }
        popup.show()
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun send(commentEntity: CommentEntity, comment: String) {
        mProductEntity._id?.let { idArticle ->
            token?.let { token ->
                mOneProductViewModel.setEditComment(commentEntity._id, idArticle, comment, token)
            }
        }
    }

    override fun onDestroyView() {
        setupViewModel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        setHasOptionsMenu(true)
        super.onDestroy()
    }

}