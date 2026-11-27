package org.sopt.play.data.repository

import kotlinx.coroutines.CancellationException
import org.sopt.play.data.dto.CreatePostRequestDto
import org.sopt.play.data.dto.PostDto
import org.sopt.play.data.service.SearchService

class SearchRepository(
    private val searchService: SearchService,
) {
    suspend fun getPosts(): Result<List<PostDto>> {
        return try {
            val response = searchService.getPosts()

            if (response.isSuccess) {
                Result.success(response.data.posts)
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

    suspend fun createPost(
        authorizationCode: String,
        title: String,
        content: String,
        author: String,
    ): Result<Unit> {
        return try {
            val response = searchService.createPost(
                authorizationCode = authorizationCode,
                request = CreatePostRequestDto(
                    title = title,
                    content = content,
                    author = author,
                ),
            )

            if (response.isSuccess) {
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

    suspend fun deletePost(
        authorizationCode: String,
        postId: Long,
    ): Result<Unit> {
        return try {
            val response = searchService.deletePost(
                authorizationCode = authorizationCode,
                postId = postId,
            )

            if (response.isSuccess) {
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