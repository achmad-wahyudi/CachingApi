package com.wahyuapp.cachingapi.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: Int,
    var username: String,
    var avatar: String
)