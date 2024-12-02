package com.example.posters.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.usecase.ChangeFilterStateUseCase
import com.example.domain.usecase.GetCategoryListUseCase
import com.example.domain.usecase.GetPosterListUseCase
import com.example.posters.di.ConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel
    @Inject
    constructor(
        private val getCategoryListUseCase: GetCategoryListUseCase,
        private val getPosterListUseCase: GetPosterListUseCase,
        private val connectionManager: ConnectionManager,
        private val changeFilterStateUseCase: ChangeFilterStateUseCase
    ) : ViewModel() {
        private val _categoryList = MutableStateFlow(PresentationModel<List<CategoryPresentation>>(screenState = ScreenState.DEFAULT))
        val categoryList = _categoryList.asStateFlow()

        private val _posterList = MutableStateFlow(PagingData.empty<PosterPresentation>())
        val posterList = _posterList.asStateFlow()

    private val _dialogState = MutableStateFlow(true)
    val dialogState = _dialogState.asStateFlow()
    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()


    init {
        getCategoryList()
    }

    fun changeDialogState(){
        _dialogState.value = false
    }

        fun getCategoryList() {

            viewModelScope.launch(Dispatchers.IO) {
                getCategoryListUseCase.getCategoryList().collect {
                    _categoryList.value = it
                    if (it.data != null){
                        getPosterList()
                    }

                }
            }
        }

        fun getPosterList() {
            viewModelScope.launch(Dispatchers.IO) {
                getPosterListUseCase
                    .getPosterList(
                        location = _location.value,
                        categories =
                            _categoryList.value.data?.filter { it.select }
                                ?.map { it.slug }
                                .toString()
                                .replace("[", "")
                                .replace("]", "")
                                .replace(" ",""),
                        radius = if (_location.value == null) null else 20000,
                        internetIsConnect = connectionManager.isConnected(),
                        error = {

                        }
                    ).collect {paging->
                        _posterList.value = paging
                    }
            }


        }

    fun changeCategoryItemState(id:Long){
viewModelScope.launch(Dispatchers.IO)  {
    changeFilterStateUseCase.updateCategoryList(id).collect{
        _categoryList.value = it
        getPosterList()
    }
}
    }

    fun saveLocation(location: Location) {
        _location.value = location
        getCategoryList()
    }
}
