package preview.android.activity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import preview.android.activity.api.AuthService
import preview.android.activity.api.FormService
import preview.android.activity.api.MentorService
import preview.android.activity.api.ReviewService
import preview.android.repository.*

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideLoginRepository(api: AuthService): LoginRepository = LoginRepository(api)

    @Provides
    @ViewModelScoped
    fun provideMentorRepository(api: MentorService) : MentorRepository = MentorRepository(api)

    @Provides
    @ViewModelScoped
    fun provideFormRepository(api: FormService) : FormRepository = FormRepository(api)

    @Provides
    @ViewModelScoped
    fun provideChatRepository() : ChatRepository = ChatRepository()

    @Provides
    @ViewModelScoped
    fun provideAlarmRepository() : AlarmRepository = AlarmRepository()

    @Provides
    @ViewModelScoped
    fun provideReviewRepository(api : ReviewService) : ReviewRepository = ReviewRepository(api)
}