package ru.mike.lab.mvp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Post {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("votes")
    @Expose
    var votes: Int? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("gifURL")
    @Expose
    var gifURL: String? = null

    @SerializedName("gifSize")
    @Expose
    var gifSize: Int? = null

    @SerializedName("previewURL")
    @Expose
    var previewURL: String? = null

    @SerializedName("videoURL")
    @Expose
    var videoURL: String? = null

    @SerializedName("videoPath")
    @Expose
    var videoPath: String? = null

    @SerializedName("videoSize")
    @Expose
    var videoSize: Int? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("width")
    @Expose
    var width: String? = null

    @SerializedName("height")
    @Expose
    var height: String? = null

    @SerializedName("commentsCount")
    @Expose
    var commentsCount: Int? = null

    @SerializedName("fileSize")
    @Expose
    var fileSize: Int? = null

    @SerializedName("canVote")
    @Expose
    var canVote: Boolean? = null

}