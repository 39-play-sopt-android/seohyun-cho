package org.sopt.play.data.service

import org.sopt.play.data.dto.AuthorizationRequestDto
import org.sopt.play.data.dto.AuthorizationResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/members/authorization-code")
    suspend fun issueAuthorizationCode(
        @Body request: AuthorizationRequestDto,
    ): AuthorizationResponseDto
}