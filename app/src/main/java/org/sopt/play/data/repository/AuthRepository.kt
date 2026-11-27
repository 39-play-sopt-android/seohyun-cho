package org.sopt.play.data.repository

import kotlinx.coroutines.CancellationException
import org.sopt.play.data.LocalStorage
import org.sopt.play.data.dto.AuthorizationRequestDto
import org.sopt.play.data.service.AuthService

class AuthRepository(
    private val authService: AuthService,
    private val localStorage: LocalStorage,
) {

    suspend fun issueAuthorizationCode(): Result<Unit> {
        return try {
            val response = authService.issueAuthorizationCode(
                request = AuthorizationRequestDto(
                    part = "ANDROID",
                ),
            )

            if (response.isSuccess) {
                localStorage.saveAuthorizationCode(
                    authorizationCode = response.data.authorizationCode,
                )

                Result.success(Unit)
            } else {
                Result.failure(
                    IllegalStateException(response.message),
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}