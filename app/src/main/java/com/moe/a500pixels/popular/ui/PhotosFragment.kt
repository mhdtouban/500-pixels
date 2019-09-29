package com.moe.a500pixels.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moe.a500pixels.databinding.FragmentPhotosBinding
import com.moe.a500pixels.di.Injectable
import com.moe.a500pixels.di.injectViewModel
import com.moe.a500pixels.util.ConnectivityUtil
import javax.inject.Inject

class PhotosFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel
    private lateinit var binding: FragmentPhotosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          viewModel = injectViewModel(viewModelFactory)
          viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)

         binding = FragmentPhotosBinding.inflate(inflater, container, false)
         context ?: return binding.root
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        viewModel.photos.observe(viewLifecycleOwner, Observer { result ->

            Toast.makeText(this.context, "result = $result", Toast.LENGTH_LONG).show()
        })
    }

}
