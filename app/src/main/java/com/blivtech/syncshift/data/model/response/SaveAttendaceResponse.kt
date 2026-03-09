package com.blivtech.syncshift.data.model.response


data class SaveAttendaceResponse(
    val status: Boolean,
    val message: String,
    val data: BodyRequest?
)
data class BodyRequest(
        val planid: String,
        val created_by: String,

    )

