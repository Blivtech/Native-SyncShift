package com.blivtech.syncshift.ui.company

data class CompanySaveRequestItem(
    val btCode :String,
    val companyName:String,
    val companyCode:String,
    val companyType:String,
    val townCode:String,
    val townName:String,
    val shiftDetails:List<ShiftEntity>

)
