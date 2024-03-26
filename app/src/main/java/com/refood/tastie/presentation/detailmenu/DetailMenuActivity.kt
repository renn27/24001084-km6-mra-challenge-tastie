package com.refood.tastie.presentation.detailmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.refood.tastie.databinding.ActivityDetailMenuBinding

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}