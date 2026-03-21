package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import javax.inject.Inject

/**
 * 履歴からユーザー名を削除する UseCase
 */
class DeleteUserNameFromHistoryUseCase @Inject constructor(
    private val repository: UserNameRepository
) {
    suspend operator fun invoke(userName: String) {
        repository.deleteUserNameFromHistory(userName)
    }
}
