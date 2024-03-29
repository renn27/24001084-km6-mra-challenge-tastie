package com.refood.tastie.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.refood.tastie.R
import com.refood.tastie.data.datasource.category.DummyCategoryDataSource
import com.refood.tastie.data.datasource.menu.DummyMenuDataSource
import com.refood.tastie.data.model.Category
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.repository.CategoryRepository
import com.refood.tastie.data.repository.CategoryRepositoryImpl
import com.refood.tastie.data.repository.MenuRepository
import com.refood.tastie.data.repository.MenuRepositoryImpl
import com.refood.tastie.data.source.local.pref.UserPreference
import com.refood.tastie.data.source.local.pref.UserPreferenceImpl
import com.refood.tastie.databinding.FragmentHomeBinding
import com.refood.tastie.presentation.detailmenu.DetailMenuActivity
import com.refood.tastie.presentation.home.adapter.CategoryListAdapter
import com.refood.tastie.utils.GenericViewModelFactory
import com.rendi.foodorderapp.presentation.home.adapter.MenuListAdapter
import com.rendi.foodorderapp.presentation.home.adapter.OnItemClickedListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private var categoryAdapter: CategoryListAdapter? = null
    private var catalogAdapter: MenuListAdapter? = null

    private lateinit var userPreference: UserPreference
    private var isUsingListMode: Boolean = false

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
        bindCategoryList(viewModel.getCategories())
        bindCatalogList(isUsingListMode, viewModel.getMenus())
        setClickAction()
    }

    private fun setUserPreference() {
        userPreference = UserPreferenceImpl(requireContext())
        isUsingListMode = userPreference.getListMode()
        setIcon(isUsingListMode)
    }

    private fun bindCategoryList(data: List<Category>) {
        categoryAdapter = CategoryListAdapter()
        binding.rvCategoryItem.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter?.submitData(data)
    }

    private fun setClickAction() {
        binding.ibChangeListMode.setOnClickListener {
            isUsingListMode = !isUsingListMode
            setIcon(isUsingListMode)
            bindCatalogList(isUsingListMode, viewModel.getMenus())
            userPreference.setListMode(isUsingListMode)
        }
    }

    private fun setIcon(usingListMode: Boolean) {
        binding.ibChangeListMode.setImageResource(if (usingListMode) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun bindCatalogList(isUsingListMode: Boolean, data: List<Menu>) {
        val listMode = if (isUsingListMode) MenuListAdapter.MODE_LIST else MenuListAdapter.MODE_GRID
        catalogAdapter = MenuListAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvCatalogList.apply {
            adapter = this@HomeFragment.catalogAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingListMode) 1 else 2)
        }
        catalogAdapter?.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }
}