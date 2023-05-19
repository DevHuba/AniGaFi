package eu.devhuba.anigafi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.devhuba.anigafi.model.api.ShikimoriApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeApiViewModel
@Inject
constructor(private val repo: ShikimoriApiRepo) : ViewModel() {
    val result = repo.animes

    init {
        retrieveAnimes()
    }

    private fun retrieveAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.query()
        }
    }

    fun onQueryUpdate() {
        Log.i(
            "this",
            "this is onQueryUpdate"
        )
    }
}