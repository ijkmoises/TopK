package br.com.emesistemas.topk.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



    open fun onBind(position:Int){
        //currentPosition = position
    }
}