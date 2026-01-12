package com.m4ykey.stos.di

import com.m4ykey.stos.core.network.NetworkClient
import com.m4ykey.stos.sites.presentation.components.SiteManager
import org.koin.dsl.module

val networkModule = module {
    single { SiteManager() }

    single { NetworkClient.create(get(), get()) }
}