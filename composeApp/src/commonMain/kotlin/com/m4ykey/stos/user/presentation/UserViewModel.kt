@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.user.domain.use_case.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    private val _id = MutableStateFlow<Int?>(null)

    val userQuestions : Flow<PagingData<Question>> = _id
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapLatest { id ->
            useCase.getUserQuestions(id = id)
                .cachedIn(viewModelScope)
        }

    fun initUser(id : Int) {
        if (_id.value == id) return

        _id.value = id
        getUser(id)
    }

    private fun getUser(id : Int) {
        _userUiState.update { it.copy(error = null, loading = true) }

        viewModelScope.launch {
            useCase.getUser(id)
                .catch { exception ->
                    _userUiState.update { it.copy(loading = false, error = exception.message) }
                }
                .collect { result ->
                    handleApiResult(
                        result = result,
                        success = { data ->
                            _userUiState.update { it.copy(loading = false, user = data) }
                        },
                        failure = { msg ->
                            _userUiState.update { it.copy(loading = false, error = msg, user = null) }
                        }
                    )
                }
        }
    }
}