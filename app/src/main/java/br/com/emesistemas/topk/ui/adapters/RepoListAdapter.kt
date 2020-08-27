package br.com.emesistemas.topk.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.emesistemas.topk.databinding.AdapterRepoListItemBinding
import br.com.emesistemas.topk.model.Item

class RepoListAdapter(
    private val itemsDataSet: MutableSet<Item> = mutableSetOf(),
    var onClick: (item: Item) -> Unit = {}
) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoListAdapter.RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding = AdapterRepoListItemBinding
            .inflate(inflater, parent, false)
        return RepoViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return itemsDataSet.size
    }

    fun submitList(items: List<Item>) {
        itemsDataSet.addAll(items)
        updateList()
    }

    private fun updateList() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(itemsDataSet.elementAt(position))
    }

    inner class RepoViewHolder(private val viewDataBinding: AdapterRepoListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root),View.OnClickListener {

        private lateinit var item: Item

        init {
            viewDataBinding.listener = this
        }

        fun onBind(item: Item) {
            this.item = item
            viewDataBinding.item = item
        }

        override fun onClick(v: View?) {
            if (::item.isInitialized) {
                onClick(item)
            }
        }
    }
}