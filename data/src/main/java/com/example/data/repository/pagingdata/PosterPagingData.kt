package com.example.data.repository.pagingdata

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.database.LocalDatabaseKudaGo
import com.example.data.database.models.PosterCategoryDB
import com.example.data.models.Event
import com.example.data.models.toPlaceDB
import com.example.data.models.toPosterPresentation
import com.example.data.service.KudaGoService
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import java.time.LocalDateTime
import java.util.Calendar

class PosterPagingData(
    private val service: KudaGoService,
    private val databaseKudaGo: LocalDatabaseKudaGo,
    private val location: Location?,
    private val categories: String?,
    private val radius: Long?,
    private val error: (String) -> Unit,
) : PagingSource<Int, PosterPresentation>() {
    override fun getRefreshKey(state: PagingState<Int, PosterPresentation>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterPresentation> {
        val page: Int = params.key ?: 1

        val  data = LocalDateTime.now().minusMonths(1)
        val calendar = Calendar.getInstance()
        calendar.set(
             data.year,
             data.monthValue,
             data.dayOfMonth
        )
            val posters = mutableListOf<PosterPresentation>()

            val events =
                service.getEvents(
                    page = page,
                    startDate = calendar.timeInMillis,
                    lon = location?.lon,
                    lat = location?.lat,
                    radius = radius,
                    categories = categories

                )

       return if (events.error.isNullOrEmpty()) {

            val places =  events.results?.map { poster->
                service.getPlace(poster.place.id.toString())
            }
            if (places.isNullOrEmpty().not()){
                databaseKudaGo.placeDao().addAllPlace(places!!.map { it.toPlaceDB() })

                events.results.map { event: Event ->
                    val categoryList = mutableListOf<String>()
                    event.categories.forEach { categoty->
                        val categoryItem = databaseKudaGo.categoryDao().getCategorieByName(name = categoty)
                        categoryList.add(categoryItem.name)
                        databaseKudaGo.posterCategoryDao().addPosterCategory(PosterCategoryDB(posterId = event.id, categoryId =categoryItem.id ))
                    }
                    posters.add(event.toPosterPresentation(categoryList))
                }
            }

            val nextKey =
                if (events.next.isNullOrEmpty()) null else page + 1

            val prevKey = if (events.previous.isNullOrEmpty()) null else page - 1

           LoadResult.Page(posters, prevKey, nextKey)
        }else{
            error(events.error)
           LoadResult.Error(Throwable("No Internet, no cache"))
        }
    }
}
