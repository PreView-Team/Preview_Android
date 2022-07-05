package preview.android.activity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import preview.android.activity.api.AuthService
import preview.android.repository.LoginRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideLoginRepository(api: AuthService): LoginRepository = LoginRepository(api)
}