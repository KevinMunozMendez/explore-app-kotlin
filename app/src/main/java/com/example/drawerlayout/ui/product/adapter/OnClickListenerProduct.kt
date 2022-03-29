package com.example.drawerlayout.ui.product.adapter

import android.view.View
import android.widget.ImageButton
import androidx.annotation.MenuRes
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.common.entities.CommentEntity
import com.example.drawerlayout.common.entities.ProductEntity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface OnClickListenerProduct {
    fun onClick (
        commentEntity: CommentEntity,
        txtIl: TextInputLayout,
        txtEdit: TextInputEditText,
        btnEdit: ImageButton,
        v: View, @MenuRes menuRes: Int
    )
    fun send (commentEntity: CommentEntity, comment: String)
}