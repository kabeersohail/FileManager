package com.example.filemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create and set up the RecyclerView adapter
        val adapter = FileSystemAdapter(filePaths)
        val recyclerView: RecyclerView =  findViewById(R.id.files_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener { itemName ->
            // Fetch sub-folders and files for the clicked folder
            val subFoldersAndFiles = fetchSubFoldersAndFiles(itemName.path)
            // Update the adapter with the contents of the clicked folder
            adapter.updateItems(
                subFoldersAndFiles.map {
                    FileSystemAdapter.FolderX(name = it, path = "${itemName.path}/$it")
                }
            )
        }
    }

    private fun fetchSubFoldersAndFiles(folderPath: String): List<String> {
        // Filter the list of file paths to get sub-folders and files for the given folder path
        val subFoldersAndFiles = filePaths.filter { filePath ->
            // Check if the file path starts with the clicked folder path
            filePath.startsWith(folderPath)
        }

        // We need to further process the filtered list to extract immediate sub-folders and files
        val immediateSubFoldersAndFiles = subFoldersAndFiles.mapNotNull { filePath ->
            val relativePath = filePath.removePrefix(folderPath)
            val segments = relativePath.split("/")
            if (segments.size > 1) {
                segments[1] // Second segment represents immediate sub-folders or files
            } else {
                null
            }
        }.distinct()

        return immediateSubFoldersAndFiles
    }


}