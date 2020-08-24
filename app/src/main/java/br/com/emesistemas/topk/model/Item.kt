package br.com.emesistemas.topk.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Item(
    @PrimaryKey
    val id: Int,
    val forks: Int = 0,
    val name: String? = "",
    val forks_count: Int = 0,
    val fork: Boolean = false,
    val full_name: String? = "",
    val description: String? = "",
    val stargazers_count: Int = 0,
    val created_at: String? = "",
    val updated_at: String? = "",
    val open_issues: Int?,
    @Embedded
    val owner: Owner = Owner(),
    var page: Int = 0
) : Parcelable