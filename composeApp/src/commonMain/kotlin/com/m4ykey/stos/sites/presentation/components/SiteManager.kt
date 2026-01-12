package com.m4ykey.stos.sites.presentation.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SiteManager {

    private val _selectedSite = MutableStateFlow("stackoverflow")
    val selectedSite = _selectedSite.asStateFlow()

    fun updateSite(newSite : String) {
        _selectedSite.value = newSite
    }
}