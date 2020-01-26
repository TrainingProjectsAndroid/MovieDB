package com.example.moviedb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviedb.data.local.dao.MoviesDao
import com.example.moviedb.data.local.dao.PopularMoviesDao
import com.example.moviedb.data.local.dao.TrendsDao
import com.example.moviedb.data.local.dao.TvDao
import com.example.moviedb.model.popular.PopularResult
import com.example.moviedb.model.theathre.ResultMovie
import com.example.moviedb.model.trendsOfDay.TrendResult
import com.example.moviedb.model.tv.ResultTV
import com.example.moviedb.until.ListIntConverter
import com.example.moviedb.until.ListStringConverter

@Database(entities = [ResultMovie::class,
    ResultTV::class,
    PopularResult::class,
    TrendResult::class], version = 1, exportSchema = false)
@TypeConverters(ListIntConverter::class, ListStringConverter::class)
abstract class FilmsDB: RoomDatabase() {

    abstract fun getMoviesDao():MoviesDao

    abstract fun getTvDao():TvDao

    abstract fun getPopularMoviesDao():PopularMoviesDao

    abstract fun getTrendsDao():TrendsDao

    companion object{
        @Volatile
        private var instance:FilmsDB? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this){
            instance ?: buildDataBase(context).also{ instance = it}
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context, FilmsDB::class.java, "FilmsDB"
        ).build()
    }
}