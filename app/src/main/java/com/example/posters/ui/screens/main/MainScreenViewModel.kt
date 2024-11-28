package com.example.posters.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.PosterPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.usecase.GetCategoryListUseCase
import com.example.domain.usecase.GetPosterListUseCase
import com.example.posters.di.ConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel
    @Inject
    constructor(
        private val getCategoryListUseCase: GetCategoryListUseCase,
        private val getPosterListUseCase: GetPosterListUseCase,
        private val connectionManager: ConnectionManager,
    ) : ViewModel() {
        private val _categoryList = MutableStateFlow(PresentationModel<List<CategoryPresentation>>(screenState = ScreenState.DEFAULT))
        val categoryList = _categoryList.asStateFlow()

        private val _posterList = MutableStateFlow(PresentationModel<PagingData<PosterPresentation>>(screenState = ScreenState.DEFAULT))
        val posterList = _posterList.asStateFlow()

    init {
        getCategoryList()
    }

        fun getCategoryList() {

            viewModelScope.launch(Dispatchers.IO) {
                getCategoryListUseCase.getCategoryList().collect {
                    _categoryList.value = it
                }
            }
        }

        fun getPosterList() {
            viewModelScope.launch(Dispatchers.IO) {
                getPosterListUseCase
                    .getPosterList(
                        location = null,
                        categories =
                            _categoryList.value.data
                                ?.map { it.slug }
                                .toString()
                                .replace("[", "")
                                .replace("]", ""),
                        radius = null,
                        internetIsConnect = connectionManager.isConnected(),
                    ).collect {
                        _posterList.value = it
                    }
            }


        }

    fun changeCategoryItemState(id:Long){

        val casheList = _categoryList.value.data?.toMutableList()!!

        casheList.forEach {
            if (it.id == id){
                it.select = it.select.not()
            }
        }
        Log.e("Filters","$casheList")

        _categoryList.value =  PresentationModel<List<CategoryPresentation>>(screenState = ScreenState.RESULT, data = casheList.toList())
    }
    }
