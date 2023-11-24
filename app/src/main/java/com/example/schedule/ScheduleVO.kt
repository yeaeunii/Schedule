package com.example.schedule

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleVO(

    @SerializedName("id")
    val id: Int = 0,

    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,

    val start: String = "",
    val end: String = "",
//    @SerializedName("peopleNum", alternate = ["num", "count"])
    val peopleNum: String = "",
    val name: String = "",
    val createNm: String = "",
    val department: String = "",
    val contents: String = "",
    val isDelete: String = "",

    val cancelReason: String = "",
    val cancelNm: String = ""

) : Parcelable