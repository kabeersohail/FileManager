package com.example.filemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var lastKnownPath: String = "/"
    private var adapter: FileSystemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create and set up the RecyclerView adapter
        val adapter = FileSystemAdapter(filePaths)
        this.adapter = adapter
        val recyclerView: RecyclerView =  findViewById(R.id.files_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener { itemName ->
            // Fetch sub-folders and files for the clicked folder
            val subFoldersAndFiles = fetchSubFoldersAndFiles(itemName.path)
            // Update the adapter with the contents of the clicked folder
            adapter.updateItems(
                subFoldersAndFiles.map {
                    lastKnownPath = itemName.path
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

    override fun onBackPressed() {
        // Check if current path is not root, then navigate back
        if (lastKnownPath != "/") {

            if(!lastKnownPath.contains("/")) {
                adapter?.extractRootFolders()
                return
            }

            val parentFolderPath = lastKnownPath.substringBeforeLast("/")
            lastKnownPath = parentFolderPath

            // Fetch sub-folders and files for the parent folder
            val parentSubFoldersAndFiles = fetchSubFoldersAndFiles(parentFolderPath)
            // Update the adapter with the contents of the parent folder
            adapter?.updateItems(
                parentSubFoldersAndFiles.map {
                    FileSystemAdapter.FolderX(name = it, path = "$parentFolderPath/$it")
                }
            )
        } else {
            super.onBackPressed() // Call default behavior if already at root
        }
    }

}