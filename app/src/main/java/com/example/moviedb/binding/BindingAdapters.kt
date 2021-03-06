package com.example.moviedb.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.moviedb.R
import com.example.moviedb.model.discover.DiscoverResult
import com.example.moviedb.model.genre.Genre
import com.example.moviedb.model.movieDetail.ProductionCountry
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("loadImg")
fun loadImg(view:ImageView, path:String?){
    path?.let {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500$it")
            .into(view)
    }
}

@BindingAdapter("calculateProgress")
fun calculateProgress(view:ProgressBar, data:DiscoverResult?){
    data?.let {
        val res = data.voteAverage / 10 * 100
        view.progress = res.toInt()
    }
}

@BindingAdapter("setRanting")
fun setRating(view:ProgressBar, progress:Double?){
    progress?.let {
        view.progress = it.toInt()
    }
}

@BindingAdapter("doubleToString")
fun convertValue(view:TextView, progress:Double?){
    progress?.let {
        view.text = it.toString()
    }
}

@BindingAdapter("showProgress")
fun showProgress(view: TextView, data: DiscoverResult?){
    data?.let {
        val res = data.voteAverage / 10 * 100
        view.text = "${res.toInt()}%"
    }
}

@BindingAdapter("genresList")
fun getGenresList(view: TextView, data:List<Genre>?){
    data?.let {
        val res = StringBuffer()
        for (i in it){
            res.append(i.name + " / ")
        }
        val genres = res.toString()
        view.text = genres.substring(0, genres.length-2)
    }
}

@BindingAdapter("countriesList")
fun getCountriesList(view: TextView, data:List<ProductionCountry>?){
    data?.let {
        val res = StringBuffer()
        for (i in it){
            res.append(i.name + " / ")
        }
        val genres = res.toString()
        try {
            view.text = genres.substring(0, genres.length-2)
        }catch (e:StringIndexOutOfBoundsException){
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter(value = ["birthday", "deathday"], requireAll = false)
fun getAge(view:TextView, birthdayDate:String?, deathdayDate:String?){
    val birthday = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    if (birthdayDate != null) {
        birthday.time = sdf.parse(birthdayDate)
        if (deathdayDate == null) {
            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR))
                age--
            val intAge = age.toInt()
            view.text = "$intAge ${view.context.resources.getString(R.string.person_yesrs_old)}"
        } else {
            val today = Calendar.getInstance()
            today.time = sdf.parse(deathdayDate)
            var age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR))
                age--
            val intAge = age.toInt()
            view.text = "$intAge ${view.context.resources.getString(R.string.person_yesrs_old)}"
        }
    }
}
