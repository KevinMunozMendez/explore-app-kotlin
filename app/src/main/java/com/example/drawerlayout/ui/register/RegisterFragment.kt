package com.example.drawerlayout.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import androidx.core.graphics.createBitmap
import androidx.core.net.toFile
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.ErrorDetails
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.databinding.FragmentRegisterBinding
import com.example.drawerlayout.ui.register.viewModel.RegisterViewModel
import java.io.File

class RegisterFragment : Fragment() {

    lateinit var mBinding: FragmentRegisterBinding
    private val mRegisterViewModel: RegisterViewModel by activityViewModels()

    private var mPhotoSelectedUri: Uri? = null

    private val addFragmentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            mPhotoSelectedUri = it.data?.data
            mBinding.imgPhoto.setImageURI(mPhotoSelectedUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mRegisterViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = mBinding.txtEditEmail
        val passwordEditText = mBinding.txtEditPassword
        val lastNameEditText = mBinding.txtEditName
        val registerButton = mBinding.register
        val loadingProgressBar = mBinding.loading
        val imageSearch = mBinding.btnImg

//        mRegisterViewModel.registerFormState.observe(viewLifecycleOwner, { registerFormState ->
//                registerButton.isEnabled = registerFormState.isDataValid
//                registerFormState.usernameError?.let {
//                    usernameEditText.error = getString(it)
//                }
//                registerFormState.passwordError?.let {
//                    passwordEditText.error = getString(it)
//                }
//            })

        mRegisterViewModel.registerResult.observe(viewLifecycleOwner,{ registerResult ->
            loadingProgressBar.visibility = View.GONE
            registerResult.errorFields?.let {
                showRegisterFailed(it)
            }
            registerResult.success?.let {
//                Log.i("jajajajaj", it.toString())
                updateUiWithUser(it)
            }
        })

        imageSearch.setOnClickListener { openGallery() }

        registerButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val gordo = mPhotoSelectedUri?.path
//            Log.i("photo", gordo.toString())
            mRegisterViewModel.register(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                lastNameEditText.text.toString(),
                mPhotoSelectedUri.toString()
            )
        }

        with(mBinding) {
            txtEditEmail.addTextChangedListener {
                mBinding.txtIlEmail.error = null
            }
            txtEditPassword.addTextChangedListener {
                mBinding.txtIlPassword.error = null
            }
            txtEditName.addTextChangedListener {
                mBinding.txtIlName.error = null
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        addFragmentLauncher.launch(intent)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.user
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        Navigation.findNavController(requireView()).navigate(R.id.nav_home)
//        loginViewModel.login(logOut = true)
    }

    private fun showRegisterFailed(errors: ErrorDetails) {
        val usernameEditText = mBinding.txtIlEmail
        val passwordEditText = mBinding.txtIlPassword
        val lastNameEditText = mBinding.txtIlName

        for (error in errors.details) {
//            Log.i("error we", error.toString())
            with(error) {
                if (context.key == "password") passwordEditText.error = message
                if (context.key == "email") usernameEditText.error = message
                if (context.key == "user") lastNameEditText.error = message
            }
        }
    }

}