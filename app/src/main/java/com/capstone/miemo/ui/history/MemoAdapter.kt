package com.capstone.miemo.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.miemo.R
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.ui.detail.DetailActivity

class MemoAdapter(private var memoList: List<Memo>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    // Fungsi untuk memperbarui data adapter dengan daftar memo yang baru
    fun updateData(newMemoList: List<Memo>) {
        memoList = newMemoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_history, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo = memoList[position]
        holder.bind(memo)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("MEMO_ID", memo.id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = memoList.size

    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val memoTextView: TextView = itemView.findViewById(R.id.tv_item_desc)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_item_name)

        fun bind(memo: Memo) {
            memoTextView.text = memo.memo
            dateTextView.text = memo.date
        }
    }

}
