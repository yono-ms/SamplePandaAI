package com.example.samplepandaai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplepandaai.domain.usecase.DeleteUserNameFromHistoryUseCase
import com.example.samplepandaai.domain.usecase.GetUserNameHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class UserNameHistoryViewModel @Inject constructor(
    private val getUserNameHistoryUseCase: GetUserNameHistoryUseCase,
    private val deleteUserNameFromHistoryUseCase: DeleteUserNameFromHistoryUseCase
) : ViewModel() {
    private val logger = LoggerFactory.getLogger(UserNameHistoryViewModel::class.java)

    val history: StateFlow<List<String>> = getUserNameHistoryUseCase()
        .stateIn(
            scope = viewModelScope,
            // WhileSubscribed(5000) の理由:
            // 画面回転時など、UIが一瞬だけ非アクティブになる際にストリームが即座に停止するのを防ぐ。
            // 5秒の猶予（バッファ）を設けることで、回転後も即座に以前のキャッシュ状態を表示できる。
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDeleteUserName(userName: String) {
        viewModelScope.launch {
            try {
                deleteUserNameFromHistoryUseCase(userName)
                logger.info("Deleted user name from history via UseCase: {}", userName)
            } catch (e: Exception) {
                logger.error("Failed to delete user name from history", e)
            }
        }
    }
}
