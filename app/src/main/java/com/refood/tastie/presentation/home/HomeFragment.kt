package com.refood.tastie.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.refood.tastie.R
import com.refood.tastie.data.model.Category
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.source.local.pref.UserPreference
import com.refood.tastie.data.source.local.pref.UserPreferenceImpl
import com.refood.tastie.databinding.FragmentHomeBinding
import com.refood.tastie.presentation.detailmenu.DetailMenuActivity
import com.refood.tastie.presentation.home.adapter.CategoryListAdapter
import com.refood.tastie.utils.proceedWhen
import com.rendi.foodorderapp.presentation.home.adapter.MenuListAdapter
import com.rendi.foodorderapp.presentation.home.adapter.OnItemClickedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()

    private var currentCategory: String? = null

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            // when category clicked
            currentCategory = it.name
            getMenuData(currentCategory)
        }
    }
    private var menuAdapter: MenuListAdapter? = null
    private var isUsingListMode: Boolean = false
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserPreference()
        setupListCategory()
        getCategoryData()
        setupListMenu(isUsingListMode)
        getMenuData(currentCategory)
        setupUserData()
        setClickAction()
    }

    private fun setupUserData() {
        viewModel.getCurrentUser()?.let {
            binding.layoutHeader.tvName.setText(it.fullName)
            binding.layoutHeader.ivUser.load(it.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.iv_profile_placeholder)
                error(R.drawable.iv_profile_placeholder)
                transformations(CircleCropTransformation())
            }
        }
    }


    private fun setUserPreference() {
        userPreference = UserPreferenceImpl(requireContext())
        isUsingListMode = userPreference.getListMode()
        setIcon(isUsingListMode)
    }

    private fun setupListCategory() {
        binding.rvCategoryItem.apply {
            adapter = categoryAdapter
        }
    }

    private fun setupListMenu(isUsingListMode: Boolean) {
        val listMode = if (isUsingListMode) MenuListAdapter.MODE_LIST else MenuListAdapter.MODE_GRID
        menuAdapter = MenuListAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategoryItem.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateCategory.root.isVisible = false
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategoryItem.isVisible = true
                    it.payload?.let { data -> bindCategoryList(data) }
                },
                doOnError = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.rvCategoryItem.isVisible = false
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                }
            )
        }
    }

    private fun getMenuData(category: String? = null) {
        viewModel.getMenus(category).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutStateCatalog.root.isVisible = true
                    binding.layoutStateCatalog.pbLoading.isVisible = true
                    binding.layoutStateCatalog.tvError.isVisible = false
                    binding.rvCatalogList.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateCatalog.root.isVisible = false
                    binding.layoutStateCatalog.pbLoading.isVisible = false
                    binding.layoutStateCatalog.tvError.isVisible = false
                    binding.rvCatalogList.isVisible = true
                    it.payload?.let { data -> bindMenuList(data) }
                },
                doOnError = {
                    binding.layoutStateCatalog.root.isVisible = true
                    binding.layoutStateCatalog.pbLoading.isVisible = false
                    binding.layoutStateCatalog.tvError.isVisible = true
                    binding.rvCatalogList.isVisible = false
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                },
            )
        }
    }

    private fun bindCategoryList(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        binding.rvCatalogList.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingListMode) 1 else 2)
        }
        menuAdapter?.submitData(data)
    }

    private fun setClickAction() {
        binding.ibChangeListMode.setOnClickListener {
            isUsingListMode = !isUsingListMode
            setIcon(isUsingListMode)
            setupListMenu(isUsingListMode)
            getMenuData(currentCategory)
            userPreference.setListMode(isUsingListMode)
        }
    }

    private fun setIcon(usingListMode: Boolean) {
        binding.ibChangeListMode.setImageResource(if (usingListMode) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }
}