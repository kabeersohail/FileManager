package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private const val FOLDER_VIEW_TYPE = 0
private const val FILE_VIEW_TYPE = 1

class FileSystemAdapter(private val items: List<FileSystemItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FOLDER_VIEW_TYPE -> FolderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false))
            FILE_VIEW_TYPE -> FileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder.itemViewType) {
            FOLDER_VIEW_TYPE -> {
                val folderViewHolder = holder as FolderViewHolder
                val folderItem = item as FileSystemItem.Folder
                folderViewHolder.bind(folderItem)
            }
            FILE_VIEW_TYPE -> {
                val fileViewHolder = holder as FileViewHolder
                val fileItem = item as FileSystemItem.File
                fileViewHolder.bind(fileItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FileSystemItem.Folder -> FOLDER_VIEW_TYPE
            is FileSystemItem.File -> FILE_VIEW_TYPE
        }
    }

    // Define ViewHolder classes for Folder and File items
    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val folderIcon: ImageView = itemView.findViewById(R.id.folderIcon)
        private val folderName: TextView = itemView.findViewById(R.id.folderName)

        fun bind(folder: FileSystemItem.Folder) {
            // Bind folder data to views
            folderIcon.setImageResource(R.drawable.baseline_folder_24)
            folderName.text = folder.name
        }
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileIcon: ImageView = itemView.findViewById(R.id.fileIcon)
        private val fileName: TextView = itemView.findViewById(R.id.fileName)

        fun bind(file: FileSystemItem.File) {
            // Bind file data to views
            fileIcon.setImageResource(getFileIcon(file.type))
            fileName.text = file.name
        }

        private fun getFileIcon(fileType: FileType): Int {
            return when (fileType) {
                FileType.PDF -> R.drawable.ic_pdf
                FileType.IMAGE -> R.drawable.ic_png
                FileType.VIDEO -> R.drawable.ic_video_file
                FileType.OTHER -> R.drawable.ic_file
            }
        }
    }
}
