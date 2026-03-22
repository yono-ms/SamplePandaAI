package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 保存されているユーザー名の履歴を、新しい順（最後に使用された順）で取得する UseCase
 */
class GetUserNameHistoryUseCase @Inject constructor(
    private val repository: UserNameRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getUserNameHistory()
    }
}
