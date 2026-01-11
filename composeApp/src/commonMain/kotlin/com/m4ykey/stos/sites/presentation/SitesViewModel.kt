package com.m4ykey.stos.sites.presentation

import androidx.lifecycle.ViewModel
import com.m4ykey.stos.sites.domain.use_case.SitesUseCase

class SitesViewModel(
    private val useCase: SitesUseCase
) : ViewModel() {
}