@file:Suppress("UNCHECKED_CAST")
package mx.com.testjobflink.data.repository

import mx.com.testjobflink.data.local.MoviesDao
import mx.com.testjobflink.data.model.PageMovie
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.data.remote.MovieEndpoints


interface PopularRepository : BaseRepository {
    suspend fun getPopularDB(page: Int): List<Movie>
    suspend fun getPopularNetwork(page: Int): List<Movie>
}

class PopularRepositoryImpl(private val movieEndpoints: MovieEndpoints,
                                  private val moviesDao: MoviesDao
) : PopularRepository {
    private suspend fun saveOnDataBase(pageMovie: PageMovie) {
        pageMovie.results.toList().also { listMovies ->
            listMovies.forEach {
                it.page = pageMovie.page
                moviesDao.insert(it)
            }
        }
    }

    override suspend fun getPopularDB(page: Int): List<Movie>
            = sortMovies(moviesDao.getPopularLocalMovies(page))

    override suspend fun getPopularNetwork(page: Int): List<Movie> {
        val pageMovie = movieEndpoints.getPopularMovies(page)
        saveOnDataBase(pageMovie)
        return sortMovies(pageMovie.results)
    }
}

