package com.refood.tastie.presentation.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.refood.tastie.databinding.DialogCheckoutBinding

class CustomDialog(context: Context, private val listener: DialogListener) : Dialog(context) {
    interface DialogListener {
        fun onBackToHomeClicked()
    }

    private val binding: DialogCheckoutBinding by lazy {
        DialogCheckoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonBackToHome.setOnClickListener {
            listener.onBackToHomeClicked()
            dismiss()
        }
    }
}
