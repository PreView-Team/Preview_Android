package preview.android.activity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import preview.android.activity.api.AuthService
import preview.android.activity.api.FormService
import preview.android.activity.api.MentorService
import preview.android.activity.api.ReviewService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    val baseUrl = "https://www.preview-master.shop:8088"

    private fun <T : Any> buildRetrofit(
        baseUrl: String,
        service: KClass<T>,
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(service.java)

    @Provides
    @ViewModelScoped
    fun provideAuthService(): AuthService = buildRetrofit(baseUrl, AuthService::class)

    @Provides
    @ViewModelScoped
    fun provideMentorService(): MentorService = buildRetrofit(baseUrl, MentorService::class)

    @Provides
    @ViewModelScoped
    fun provideFormService(): FormService = buildRetrofit(baseUrl, FormService::class)

    @Provides
    @ViewModelScoped
    fun provideReviewService() : ReviewService = buildRetrofit(baseUrl, ReviewService::class)

}