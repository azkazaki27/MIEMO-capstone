package com.capstone.miemo.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Memo (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "memo")
    val memo: String? = null,

    @ColumnInfo(name = "quote")
    val quote: String? = null,

    @ColumnInfo(name = "date")
    val date: String? = null
): Parcelable