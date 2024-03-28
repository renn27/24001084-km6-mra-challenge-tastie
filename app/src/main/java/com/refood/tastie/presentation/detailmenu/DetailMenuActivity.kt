package com.refood.tastie.presentation.detailmenu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import coil.load
import androidx.appcompat.app.AppCompatActivity
import com.refood.tastie.R
import com.refood.tastie.data.model.Menu
import com.refood.tastie.databinding.ActivityDetailMenuBinding
import com.refood.tastie.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}