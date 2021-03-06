package com.example.moviedb.ui.trendsMore.adapter.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.model.genre.Genre
import com.example.moviedb.model.trends.TrendResult
import com.example.moviedb.ui.base.OnShowMovieSelectedListener
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class TrendTVViewHolder(private val view:View, private val listener: OnShowMovieSelectedListener?):RecyclerView.ViewHolder(view) {

    private val poster: ImageView = view.findViewById(R.id.trend_list_poster)
    private val title: TextView = view.findViewById(R.id.trend_list_title)
    private val genres: TextView = view.findViewById(R.id.trend_list_genres)

    fun bind(data: TrendResult?, genres:List<Genre>?){
        title.text = data?.name
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500" + data?.posterPath)
            .into(poster)
        poster.clipToOutline = true
        data?.genreIds?.let {
            val g = StringBuilder()
            try { g.append(bindGenre (it[0], genres))}catch (e:IndexOutOfBoundsException){}
            try { g.append(" / " + bindGenre (it[1], genres)) }catch (e:IndexOutOfBoundsException){}
            try { g.append(" / " + bindGenre (it[2], genres)) }catch (e:IndexOutOfBoundsException){}

            this.genres.text = g.toString()
        }
        ViewCompat.setTransitionName(poster, "headPoster${data?.id}")
        ViewCompat.setTransitionName(title, "headTitle${data?.id}")
        view.setOnClickListener {
            listener?.onItemSelected(it, data?.id ?: 0, OnShowMovieSelectedListener.TV_SHOW_TYPE)
        }
    }

    private fun bindGenre(genre:Int, genresList:List<Genre>?):String {
        genresList?.let {
            for (i in it) {
                if (i.id == genre) {
                    return i.name
                }
            }
        }
        throw IndexOutOfBoundsException()
    }

    companion object{
        fun create(parent: ViewGroup, listener: OnShowMovieSelectedListener?):TrendTVViewHolder{
            val v = LayoutInflater.from(parent.context).inflate(R.layout.trend_item, parent, false)
            return TrendTVViewHolder(v, listener)
        }
    }
}