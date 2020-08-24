package br.com.emesistemas.topk.app

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@com.bumptech.glide.annotation.GlideModule
class GlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {

        //Memory cache
        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))

        //Bitmap pool
        val calculator = MemorySizeCalculator.Builder(context)
            .setBitmapPoolScreens(3f)
            .build()
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

        //Disk cache


        //Disk cache
        val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context,
                "FastShopMoviesPictures",
                diskCacheSizeBytes.toLong()
            )
        )

        //Work with Hardware Bitmaps
        builder.setDefaultRequestOptions(RequestOptions()
            .format(DecodeFormat.PREFER_ARGB_8888))

        builder.setLogLevel(Log.DEBUG)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}