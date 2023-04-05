package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinListBinding
import com.example.swiftyproteins.presentation.activity.MainActivity
import com.example.swiftyproteins.presentation.adapters.LigandListAdapter
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.hideKeyboard
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.models.ProteinListStateScreen
import com.example.swiftyproteins.presentation.navigation.FromProteinList
import com.example.swiftyproteins.presentation.navigation.Screens
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProteinListFragment :
    BaseScreenStateFragment<FromProteinList, ProteinListStateScreen, ProteinListViewModel>() {

    private var binding: FragmentProteinListBinding? = null
    private var adapter: LigandListAdapter? = null
    override val viewModel: ProteinListViewModel by viewModel()
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProteinListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()
        binding?.toolbar?.setNavigationOnClickListener {
            viewModel.onBackPressed()
        }
    }

    private fun initToolbar() {
        binding?.toolbar?.let { toolbar ->
            (requireActivity() as MainActivity).apply {
                setSupportActionBar(toolbar)
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.protein_list_menu, menu)
        initSearchView(menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initSearchView(menu: Menu) {
        val item: MenuItem = menu.findItem(R.id.app_bar_search)
        searchView = item.actionView as SearchView
        searchView.setBackgroundResource(R.drawable.search_back)
        searchView.queryHint = getString(R.string.hint_search_ligand)
        initOnChangeSearchTextListener()
    }

    private fun initOnChangeSearchTextListener() {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(enteredText: String?): Boolean {
                    if (enteredText != null) {
                        viewModel.onSearchTextChanged(enteredText)
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return true
                }
            })
    }

    private fun initList() {
        adapter = LigandListAdapter(viewModel::onProteinClick)
        binding?.rvLigands?.adapter = adapter
    }

    override fun handleModel(model: ProteinListStateScreen) {
        adapter?.submitList(model.proteins)
        binding?.rvLigands?.smoothScrollToPosition(model.scrollPosition)
    }

    override fun handleAction(action: FromProteinList) {
        when (action) {
            is FromProteinList.Navigate.Protein ->
                router.navigateTo(Screens.Protein(action.proteinName))
            is FromProteinList.Command.GetFirstVisibleItem ->
                getFirstVisibleItem()
            is FromProteinList.Exit ->
                router.exit()
            is FromProteinList.BackTo.Login ->
                router.newRootScreen(Screens.Login)
        }
    }

    private fun getFirstVisibleItem() {
        (binding?.rvLigands?.layoutManager as LinearLayoutManager?)
            ?.findFirstVisibleItemPosition()?.let(viewModel::onGetFirstItemPosition)
    }

//    override fun onPause() {
//        viewModel.onViewPause()
//        super.onPause()
//    }
}