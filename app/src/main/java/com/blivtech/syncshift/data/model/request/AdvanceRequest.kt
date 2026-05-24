package com.blivtech.syncshift.data.model.request

data class AdvanceRequest( val btCode: String,

                           val companyCode: String,

                           val employeeCode: String,

                           val employeeName: String,

                           val activityDate: String,

                           val amount: Double,

                           val paymentMode: String,

                           val remarks: String)
