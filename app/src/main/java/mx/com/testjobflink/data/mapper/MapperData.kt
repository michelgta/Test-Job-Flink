package mx.com.testjobflink.data.mapper

import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.data.models.MovieData

fun MovieData.toDomainModel() = Movie(
        id = id, title = title ?: "", overview = overview ?: "",
        posterPath = posterPath ?: "", releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0, voteCount = voteCount ?: 0L,
        isFavorite = isFavorite ?: false, page = page ?: 0
)