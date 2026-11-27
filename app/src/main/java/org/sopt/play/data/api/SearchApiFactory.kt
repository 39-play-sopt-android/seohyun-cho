package org.sopt.play.data.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.play.data.service.AuthService
import org.sopt.play.data.service.SearchService
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object SearchApiFactory {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://playsopt.o-r.kr/")
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .build()

    val searchService: SearchService =
        retrofit.create(SearchService::class.java)

    val authService: AuthService =
        retrofit.create(AuthService::class.java)

}