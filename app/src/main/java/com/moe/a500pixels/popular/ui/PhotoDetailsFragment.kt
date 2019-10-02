package com.moe.a500pixels.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moe.a500pixels.R
import com.moe.a500pixels.databinding.FragmentPhotoDetailsBinding
import kotlinx.android.synthetic.main.fragment_photo_details.view.*
import kotlinx.android.synthetic.main.layout_photo_header.view.*

/**
 * A fragment representing a single Photo detail screen.
 */
class PhotoDetailsFragment : Fragment() {

    private val args: PhotoDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_photo_details, container, false
        )

        setHasOptionsMenu(true)

        populateUi()

        return binding.root
    }

    private fun populateUi() {

        val photo = args.photo

        binding.photo.title.text = photo?.name
        binding.photo.author.text = photo?.user?.fullname
        binding.photo.text_comment.text = photo?.votes_count.toString()
        binding.photo.text_like.text = photo?.comments_count.toString()
        binding.root.text_rating.text = photo?.rating.toString()
        binding.root.text_views.text = photo?.times_viewed.toString()
        binding.root.text_description.text = photo?.description.toString()

        Glide.with(this).load(photo?.user?.userpic_url)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.photo.user_picture)


        Glide.with(this).load(photo?.image_url?.get(0))
            .fitCenter()
            .into(binding.photo.image)
    }

}
