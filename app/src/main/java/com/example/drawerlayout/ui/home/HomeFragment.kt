package com.example.drawerlayout.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.databinding.FragmentHomeBinding
import com.example.drawerlayout.ui.login.viewModel.LoginViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()

//        val list = mutableListOf<CarouselItem>()

//        for (images in productEntity.productsImages) list.add(CarouselItem(images.photo))
//        content.carousel.addData(list)

//        setupViewModel()
    }

    private fun setupViewModel() {
        mLoginViewModel.loginResult.observeForever { loginResult ->
            loginResult.success?.let {
                updateUiWithUser(it)
            }
            loginResult.logout?.let {
                updateUiWithUser(it)
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.user
        mBinding.textHome.text = welcome
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        setHasOptionsMenu(true)
        super.onDestroy()
    }


}