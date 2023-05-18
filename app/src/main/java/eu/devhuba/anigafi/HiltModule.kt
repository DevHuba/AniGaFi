package eu.devhuba.anigafi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import eu.devhuba.anigafi.model.api.ApiService
import eu.devhuba.anigafi.model.api.ShikimoriApiRepo

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAnimeApiRepo() = ShikimoriApiRepo(ApiService.animeApi)
}