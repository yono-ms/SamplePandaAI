package com.example.samplepandaai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplepandaai.domain.usecase.AddUserNameToHistoryUseCase
import com.example.samplepandaai.domain.usecase.ValidateGitHubUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class UserNameInputViewModel @Inject constructor(
    private val validateGitHubUserNameUseCase: ValidateGitHubUserNameUseCase,
    private val addUserNameToHistoryUseCase: AddUserNameToHistoryUseCase
) : ViewModel() {
    private val logger = LoggerFactory.getLogger(UserNameInputViewModel::class.java)

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _errorType = MutableStateFlow<ValidateGitHubUserNameUseCase.Result.Error?>(null)
    val errorType: StateFlow<ValidateGitHubUserNameUseCase.Result.Error?> = _errorType.asStateFlow()

    fun onUserNameChanged(newName: String) {
        _userName.value = newName
        // 入力中のリアルタイムバリデーション（エラーメッセージのクリアのみを担当）
        if (newName.isEmpty() || validateGitHubUserNameUseCase(newName) is ValidateGitHubUserNameUseCase.Result.Success) {
            _errorType.value = null
        }
    }

    fun onSubmit(onSuccess: (String) -> Unit) {
        val currentName = _userName.value

        // UseCase を呼び出してバリデーションを実行
        when (val result = validateGitHubUserNameUseCase(currentName)) {
            is ValidateGitHubUserNameUseCase.Result.Error -> {
                _errorType.value = result
            }

            ValidateGitHubUserNameUseCase.Result.Success -> {
                _errorType.value = null
                viewModelScope.launch {
                    try {
                        addUserNameToHistoryUseCase(currentName)
                        onSuccess(currentName)
                    } catch (e: Exception) {
                        logger.error("Failed to save history", e)
                        onSuccess(currentName)
                    }
                }
            }
        }
    }
}
