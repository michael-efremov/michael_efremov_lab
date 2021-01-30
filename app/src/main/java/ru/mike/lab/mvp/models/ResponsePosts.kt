package ru.mike.lab.mvp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePosts {

    @SerializedName("result")
    @Expose
    var posts: List<Post>? = null
}