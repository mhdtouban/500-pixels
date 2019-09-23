package com.moe.a500pixels.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PhotoAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO
