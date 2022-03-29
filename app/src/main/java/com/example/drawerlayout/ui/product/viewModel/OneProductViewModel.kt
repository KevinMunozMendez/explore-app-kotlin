package com.example.drawerlayout.ui.product.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawerlayout.common.entities.CommentEntity
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.ui.product.model.OneProductInteractor

class OneProductViewModel: ViewModel() {

    private val interactor: OneProductInteractor = OneProductInteractor()

    private val productSelected = MutableLiveData<ProductEntity>()

//    private val comments = MutableLiveData<ProductEntity>()
//    private val deleteComment = MutableLiveData<ProductEntity>()
//    private val editComment = MutableLiveData<ProductEntity>()

    fun setProductSelected(productEntity: ProductEntity) {
        if (productEntity._id != null) productSelected.value = productEntity
    }

    fun getStoreSelected(): LiveData<ProductEntity> {
        return productSelected
    }

    fun setComment(comment: String, token: String, idArticle: String) {
        interactor.addComment(comment,token,idArticle) {
            productSelected.value = it
        }
    }

//    fun getComments(): LiveData<ProductEntity> {
//        return comments
//    }

    fun setDeleteComment(idComment: String, idArticle: String) {
        interactor.deleteComment(idComment,idArticle) {
            productSelected.value = it
        }
    }

//    fun getDeleteComment(): LiveData<ProductEntity> {
//        return deleteComment
//    }

    fun setEditComment(idComment: String, idArticle: String, comment: String, token: String) {
        interactor.editComment(idComment,idArticle,comment,token) {
            productSelected.value = it
        }
    }

//    fun getEditComment(): LiveData<ProductEntity> {
//        return editComment
//    }
}