package org.sopt.play.data.service

import org.sopt.play.data.dto.CreatePostRequestDto
import org.sopt.play.data.dto.CreatePostResponseDto
import org.sopt.play.data.dto.DeletePostResponseDto
import org.sopt.play.data.dto.SearchDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SearchService {
    @GET("api/v1/posts")
    suspend fun getPosts(): SearchDto

    @POST("api/v1/posts")
    suspend fun createPost(
        @Header("Authorization") authorizationCode: String,
        @Body request: CreatePostRequestDto,
    ): CreatePostResponseDto

    @DELETE("api/v1/posts/{postId}")
    suspend fun deletePost(
        @Header("Authorization") authorizationCode: String,
        @Path("postId") postId: Long,
    ): DeletePostResponseDto
}