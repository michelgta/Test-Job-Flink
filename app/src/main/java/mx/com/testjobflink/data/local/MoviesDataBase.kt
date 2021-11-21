package mx.com.testjobflink.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.com.testjobflink.data.models.MovieData


@Database(entities = [MovieData::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}