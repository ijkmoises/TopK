package br.com.emesistemas.topk.ui.custom

import android.content.Context
import android.widget.ImageView
import br.com.emesistemas.topk.R
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey

object ImageLoader {

    fun download(
        context: Context?,
        url: String?,
        iv: ImageView
    ) {
        context?.let {
            Glide.with(context.applicationContext)
                .load(url ?: "")
                .signature(ObjectKey(url ?: ""))
                .placeholder(R.color.colorGrey_300)
                .error(R.color.colorGrey_300)
                .fallback(R.color.colorGrey_300)
                .into(iv)
        }
    }
}