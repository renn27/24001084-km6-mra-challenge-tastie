package com.refood.tastie.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.refood.tastie.R
import com.refood.tastie.data.datasource.category.CategoryApiDataSource
import com.refood.tastie.data.datasource.menu.MenuApiDataSource
import com.refood.tastie.data.model.Category
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.repository.CategoryRepository
import com.refood.tastie.data.repository.CategoryRepositoryImpl
import com.refood.tastie.data.repository.MenuRepository
import com.refood.tastie.data.repository.MenuRepositoryImpl
import com.refood.tastie.data.repository.UserRepositoryImpl
import com.refood.tastie.data.source.local.pref.UserPreference
import com.refood.tastie.data.source.local.pref.UserPreferenceImpl
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.refood.tastie.data.source.network.services.TastieApiService
import com.refood.tastie.databinding.FragmentHomeBinding
import com.refood.tastie.presentation.detailmenu.DetailMenuActivity
import com.refood.tastie.presentation.home.adapter.CategoryListAdapter
import com.refood.tastie.utils.GenericViewModelFactory
import com.refood.tastie.utils.GridSpacingItemDecoration
import com.refood.tastie.utils.proceedWhen
import com.rendi.foodorderapp.presentation.home.adapter.MenuListAdapter
import com.rendi.foodorderapp.presentation.home.adapter.OnItemClickedListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    private val viewModel: HomeViewModel by viewModels {
        val service = TastieApiService.invoke()
        val firebaseAuth = FirebaseAuth.getInstance()
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val userDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val userRepository = UserRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository,userRepository))
    }

    private lateinit var userPreference: UserPreference
    private var isUsingListMode: Boolean = false
    private var currentCategory: String? = null

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            // when category clicked
            currentCategory = it.name
            getMenuData(currentCategory)
        }
    }
    private var menuAdapter: MenuListAdapter? = null

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
                doOnSuccess = {
                    it.payload?.let { data -> bindCategoryList(data) }
                }
            )
        }
    }

    private fun getMenuData(category: String? = null) {
        viewModel.getMenus(category).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindMenuList(data) }
                }
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