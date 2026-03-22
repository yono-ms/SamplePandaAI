package com.example.samplepandaai.domain.usecase

import javax.inject.Inject

/**
 * GitHub ユーザー名のバリデーションを行う UseCase
 */
class ValidateGitHubUserNameUseCase @Inject constructor() {

    private val githubUserNameRegex = Regex("^[a-zA-Z0-9](?:[a-zA-Z0-9]|-(?=[a-zA-Z0-9])){0,38}$")

    /**
     * バリデーション結果を表す sealed class
     */
    sealed class Result {
        object Success : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(userName: String): Result {
        if (userName.isBlank()) {
            return Result.Error("ユーザー名を入力してください")
        }

        if (!githubUserNameRegex.matches(userName)) {
            // 漢字、絵文字、記号、不正なハイフン位置などはすべてここに含まれる
            return Result.Error("GitHubのユーザー名形式が正しくありません")
        }

        return Result.Success
    }
}
