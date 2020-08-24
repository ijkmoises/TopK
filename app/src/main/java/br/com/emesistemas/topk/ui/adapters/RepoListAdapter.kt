package br.com.emesistemas.topk.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.ui.custom.ImageLoader
import kotlinx.android.synthetic.main.adapter_repo_list_item.view.*

class RepoListAdapter(
    private val context: Context,
    private val itemsDataSet: MutableList<Item> = mutableListOf(),
    var onClick: (item: Item) -> Unit = {}
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return RepoViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_repo_list_item,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return itemsDataSet.size
    }

    fun submitList(items: List<Item>) {
        itemsDataSet.addAll(items)
        updateList()
    }

    open fun updateList() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class RepoViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private lateinit var item: Item

        init {
            itemView.setOnClickListener {
                if (::item.isInitialized) {
                    onClick(item)
                }
            }
        }

        override fun onBind(position: Int) {
            this.item = itemsDataSet[position]

            with(itemView){
                tvAutor.text = item.owner.login
                tvRepoName.text = item.name
                tvStars.text = item.stargazers_count.toString()
                tvForks.text = item.forks_count.toString()
            }

            ImageLoader.download(
                context,
                item.owner.avatar_url,
                itemView.ivAvatar
            )
        }
    }
}