@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.sites.data.helpers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SiteManager {

    private val _selectedSite = MutableStateFlow("stackoverflow")
    val selectedSite = _selectedSite.asStateFlow()

    fun updateSite(newSite : String) {
        _selectedSite.value = newSite
    }
}