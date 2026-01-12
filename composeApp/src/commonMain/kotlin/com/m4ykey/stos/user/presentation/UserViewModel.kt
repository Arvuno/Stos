@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.sites.data.helpers.SiteManager
import com.m4ykey.stos.user.domain.use_case.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class UserViewModel(
    private val useCase: UserUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    private val _id = MutableStateFlow<Int?>(null)

    val userQuestions : Flow<PagingData<Question>> = combine(
        _id.filterNotNull().distinctUntilChanged(),
        siteManager.selectedSite
    ) { id, _ -> id }
        .flatMapLatest { id ->
            useCase.getUserQuestions(id)
                .cachedIn(viewModelScope)
        }

    val userUiState : StateFlow<UserUiState> = combine(
        _id.filterNotNull().distinctUntilChanged(),
        siteManager.selectedSite
    ) { id, _ -> id }
        .flatMapLatest { id ->
            useCase.getUser(id)
                .map { result ->
                    var state = UserUiState()
                    handleApiResult(
                        result = result,
                        success = { data -> state = UserUiState(loading = false, user = data) },
                        failure = { msg -> state = UserUiState(error = msg, loading = false) }
                    )
                    state
                }
                .onStart { emit(UserUiState(loading = true)) }
                .catch { emit(UserUiState(loading = false, error = it.message)) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UserUiState(loading = true)
        )

    fun initUser(id : Int) {
        if (_id.value == id) return
        _id.value = id
    }
}