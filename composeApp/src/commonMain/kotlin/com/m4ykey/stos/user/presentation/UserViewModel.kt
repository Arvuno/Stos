package com.m4ykey.stos.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.user.domain.use_case.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    fun getUser(id : Int) {
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