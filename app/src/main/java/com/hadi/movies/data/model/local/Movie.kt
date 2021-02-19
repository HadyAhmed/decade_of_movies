package com.hadi.movies.data.model.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hadi.movies.utils.convertToLineStrings

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("cast")
    val cast: List<String>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: Int
) {
    fun castString(): String = cast.convertToLineStrings()
    fun genresString(): String = genres.convertToLineStrings()
}