package mx.com.testjobflink.data.repository

import mx.com.testjobflink.data.mapper.toDomainModel
import mx.com.testjobflink.data.models.MovieData
import mx.com.testjobflink.data.models.Movie

interface BaseRepository {

    fun sortMovies(movies: List<MovieData>): List<Movie>
        = movies.map { result ->
            result.toDomainModel()
        }.sortedByDescending { item -> item.voteAverage }

}