package com.example.contactdefinitive

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contato(
    val name: String,
    val num: String
) : Parcelable