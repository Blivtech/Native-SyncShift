package com.blivtech.syncshift.data.model

data class Employee(val id: Int,
                    val name: String,
                    val age: Int,
                    val city: String,
                    val gender: String,
                    var status: String = "None")
