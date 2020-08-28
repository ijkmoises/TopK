package br.com.emesistemas.topk.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.emesistemas.topk.databinding.AdapterRepoListItemBinding
import br.com.emesistemas.topk.model.Item

class RepoListAdapter(
    private val context: Context,
    private val dataSet: MutableSet<Item> = mutableSetOf(),
    var onClick: (item: Item) -> Unit = {}
) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoListAdapter.RepoViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = AdapterRepoListItemBinding
            .inflate(inflater, parent, false)
        return RepoViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun submitList(items: List<Item>) {
        dataSet.addAll(items)
        updateDataSet()
    }

    fun updateDataSet() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(dataSet.elementAt(position))
    }

    inner class RepoViewHolder(private val viewDataBinding: AdapterRepoListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

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