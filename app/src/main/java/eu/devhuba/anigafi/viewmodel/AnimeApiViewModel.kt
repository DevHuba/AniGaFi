package eu.devhuba.anigafi.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.devhuba.anigafi.model.api.ShikimoriApiRepo
import javax.inject.Inject

@HiltViewModel
class AnimeApiViewModel
@Inject
constructor(private val repo: ShikimoriApiRepo) : ViewModel() {
    val result = repo
}