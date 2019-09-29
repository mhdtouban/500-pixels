package com.moe.a500pixels.di


import com.moe.a500pixels.popular.ui.PhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePhotosFragment(): PhotosFragment

}
