package br.com.emesistemas.topk.presentation.databinding

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.emesistemas.topk.R
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey

@BindingAdapter("url", "ctx")
fun download(
    view: ImageView,
    url: String?,
    ctx: Context?

) {
    ctx?.let {
        Glide.with(it)
            .load(url ?: "")
            .signature(ObjectKey(url ?: ""))
            .centerCrop()
            .placeholder(R.color.colorGrey_300)
            .error(R.color.colorGrey_300)
            .fallback(R.color.colorGrey_300)
            .into(view)
    }
}