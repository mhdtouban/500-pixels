package com.moe.a500pixels.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.moe.a500pixels.R
import com.moe.a500pixels.databinding.FragmentPhotoDetailsBinding
import com.moe.a500pixels.di.Injectable

/**
 * A fragment representing a single Photo detail screen.
 */
class PhotoDetailsFragment : Fragment(), Injectable {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentPhotoDetailsBinding>(
                inflater, R.layout.fragment_photo_details, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

}
