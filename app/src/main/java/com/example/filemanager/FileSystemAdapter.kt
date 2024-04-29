package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// FileAdapter.kt
class FileSystemAdapter(private val filePaths: List<String>) : RecyclerView.Adapter<FileSystemAdapter.FileViewHolder>() {

    private val rootFolders: MutableList<FolderX> = mutableListOf()
    private var onItemClick: ((FolderX) -> Unit)? = null

    init {
        extractRootFolders()
    }

    fun setOnItemClickListener(listener: (FolderX) -> Unit) {
        onItemClick = listener
    }

    fun updateItems(items: List<FolderX>) {
        rootFolders.clear()
        rootFolders.addAll(items)
        notifyDataSetChanged()
    }


    private fun extractRootFolders() {
        val rootFolderSet = mutableSetOf<FolderX>()

        for (path in filePaths) {
            val segments = path.split("/")
            if (segments.isNotEmpty()) {
                rootFolderSet.add(FolderX(segments.first(), segments.first()))
            }
        }

        rootFolders.addAll(rootFolderSet)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = rootFolders[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return rootFolders.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.folderName)

        fun bind(item: FolderX) {
            fileNameTextView.text = item.name
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    data class FolderX(
        val name: String,
        val path: String
    )

}

