package br.com.emesistemas.topk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val login: String = "",
    val avatar_url: String? = "",
    val html_url: String? = ""
) : Parcelable