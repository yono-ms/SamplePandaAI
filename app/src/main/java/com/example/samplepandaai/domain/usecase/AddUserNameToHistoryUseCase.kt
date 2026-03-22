package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import javax.inject.Inject

/**
 * ユーザー名を履歴に追加・更新する UseCase
 */
class AddUserNameToHistoryUseCase @Inject constructor(
    private val repository: UserNameRepository
) {
    suspend operator fun invoke(userName: String) {
        repository.addUserNameToHistory(userName)
    }
}
