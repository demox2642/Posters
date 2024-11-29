package com.example.data.repository.pagingdata

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.database.LocalDatabaseKudaGo
import com.example.data.database.models.PosterCategoryDB
import com.example.data.models.Event
import com.example.data.models.toPlaceDB
import com.example.data.models.toPosterDB
import com.example.data.models.toPosterPresentation
import com.example.data.service.KudaGoService
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import java.time.LocalDateTime

class PosterPagingData(
    private val service: KudaGoService,
    private val databaseKudaGo: LocalDatabaseKudaGo,
    private val location: Location?,
    private val categories: String?,
    private val radius: Long?,
) : PagingSource<Int, PosterPresentation>() {
    override fun getRefreshKey(state: PagingState<Int, PosterPresentation>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterPresentation> {
        val page = params.key ?: 1
        return try {
            val  data = LocalDateTime.now().minusMonths(1)
            val posters = mutableListOf<PosterPresentation>()
            val events = service.getEvents(
                    page = page,
                    startDate = "${data.year}-${ data.monthValue}-${data.dayOfMonth} 00:00:00.000",
                    lon = location?.lon,
                    lat = location?.lat,
                    radius = radius,
                    categories = categories
            )

            if (events.results.isNullOrEmpty().not()){
                val places =  events.results?.filter { it.place != null }?.map { poster ->
                    service.getPlace(poster.place?.id.toString())
                }
                // Load Place to DB
                if (places.isNullOrEmpty().not()){
                    databaseKudaGo.placeDao().addAllPlace(places!!.map { it.toPlaceDB()})
                }

                val postersDB =  events.results?.filter { it.place != null }?.map { it.toPosterDB() }
                postersDB?.let {
                    databaseKudaGo.posterDao().addAllPoster(postersDB)
                }

                //Load Categories
                events.results!!.filter { it.place != null }.forEach { event: Event ->
                    //Load Poster
                    databaseKudaGo.posterDao().addPoster(event.toPosterDB())
                    Log.e("PosterS","${event}")
                    event.categories.forEach { categoty ->
                        val categoryItem = databaseKudaGo.categoryDao().getCategorieByName(name = categoty)
                        val posterCategoryList = databaseKudaGo.posterCategoryDao().getPosterCategory(event.id)
                        categoryItem.let {
                            if (posterCategoryList.contains(PosterCategoryDB(posterId = event.id, categoryId =categoryItem.id )).not()) {
                             databaseKudaGo.posterCategoryDao().addPosterCategory(
                                    PosterCategoryDB(

                                        posterId = event.id,
                                        categoryId = categoryItem.id
                                    )
                                )
                            }
                        }
                    }

                }
            }


            val dataBasePosters = databaseKudaGo.posterDao().getPosterList()
            Log.e("PosterList","${dataBasePosters}")
            databaseKudaGo.posterDao().getPosterList().forEach { poster->
               posters.add(poster.toPosterPresentation(
                   categories = databaseKudaGo.posterCategoryDao().getPosterCategory(poster.id).map { category->
                       databaseKudaGo.categoryDao().getCategorie(category.categoryId).name
                   },
                   placeDB = databaseKudaGo.placeDao().getPlace(poster.placeId)
               )

               )
               Log.e("PosterList","${posters}")
           }

            val nextKey =
                if (events.next.isNullOrEmpty()) null else page + 1

            val prevKey = if (events.previous.isNullOrEmpty()) null else page - 1

           LoadResult.Page(posters, prevKey, nextKey)

    }catch (e: Exception){
        Log.e("PagingError","$e")
            LoadResult.Error(Throwable(e.message))

        }
    }

}
