package com.example.dicodingevent.data.model

data class EventEntity(
    val id: Int,
    val name: String,
    val description: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val registrants: Int,
    val imageLogo: String,
    val beginTime: String,
    val endTime: String,
    val link: String,
    var isBookmarked: Boolean
)
