package com.refood.tastie.presentation.detailmenu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.refood.tastie.R
import com.refood.tastie.data.datasource.cart.CartDataSource
import com.refood.tastie.data.datasource.cart.CartDatabaseDataSource
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.data.repository.CartRepositoryImpl
import com.refood.tastie.data.source.local.database.AppDatabase
import com.refood.tastie.databinding.ActivityDetailMenuBinding
import com.refood.tastie.presentation.main.MainActivity
import com.refood.tastie.utils.GenericViewModelFactory
import com.refood.tastie.utils.proceedWhen
import com.refood.tastie.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindProduct(viewModel.product)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivSubItemCount.setOnClickListener {
            viewModel.minus()
        }
        binding.ivAddItemCount.setOnClickListener {
            viewModel.add()
        }
        binding.btnAddToCart.setOnClickListener {
            addProductToCart()
        }
        binding.tvLocation.setOnClickListener {
            openMaps()
        }
    }

    private fun openMaps() {
        val gmmIntentUri = Uri.parse(viewModel.urlLocation)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        try {
            startActivity(mapIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "No maps found, please install maps apps", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addProductToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("navigate_to_cart", true)
                    startActivity(intent)
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindProduct(menu: Menu?) {
        menu?.let { item ->
            binding.ivCatalogImage.load(item.imagePictUrl) {
                crossfade(true)
            }
            binding.tvCatalogName.text = item.name
            binding.tvCatalogPrice.text = item.price.toIndonesianFormat()
            binding.tvDesc.text = item.description
            binding.tvLocation.text = item.location
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.isEnabled = it != 0.0
            binding.btnAddToCart.text = it.toIndonesianFormat()
        }
        viewModel.productCountLiveData.observe(this) {
            binding.tvAddCountCart.text = it.toString()
        }
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}