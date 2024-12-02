package com.example.posters.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.usecase.GetPosterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailPosterViewModel @Inject constructor(
    private val getPosterDetailUseCase: GetPosterDetailUseCase
):ViewModel() {
    private val _poster = MutableStateFlow(PresentationModel<PosterDetailPresentation>(screenState = ScreenState.DEFAULT))
    val poster = _poster.asStateFlow()

    fun getPoster(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            getPosterDetailUseCase.getPosterDetail(id).collect{
                _poster.value = it
            }
        }
    }
}