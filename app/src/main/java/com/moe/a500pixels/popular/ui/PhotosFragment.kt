package com.moe.a500pixels.popular.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moe.a500pixels.R
import com.moe.a500pixels.databinding.FragmentPhotosBinding
import com.moe.a500pixels.di.Injectable
import com.moe.a500pixels.di.injectViewModel
import com.moe.a500pixels.ui.GridSpacingItemDecoration
import com.moe.a500pixels.ui.VerticalItemDecoration
import com.moe.a500pixels.util.ConnectivityUtil
import javax.inject.Inject

class PhotosFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel
    private lateinit var binding: FragmentPhotosBinding

    private val adapter: PhotosAdapter by lazy { PhotosAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var gridLayoutManager: GridLayoutManager
    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_small).toInt()
        )
    }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt()
        )
    }

    private var isLinearLayoutManager: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)

        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        context ?: return binding.root

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        setLayoutManager()
        binding.recyclerView.adapter = adapter

        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_representation, menu)
        setDataRepresentationIcon(menu.findItem(R.id.list))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.list -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setDataRepresentationIcon(item)
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun subscribeUi(adapter: PhotosAdapter) {
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.setOnRefreshListener {
                binding.progressBar.visibility = View.VISIBLE
                it.dataSource.invalidate()
                it.loadedCount
            }
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
        })
    }


    private fun setDataRepresentationIcon(item: MenuItem) {
        item.setIcon(
            if (isLinearLayoutManager)
                R.drawable.ic_grid_list_24dp else R.drawable.ic_list_white_24dp
        )
    }

    companion object {
        const val SPAN_COUNT = 3
    }


}
