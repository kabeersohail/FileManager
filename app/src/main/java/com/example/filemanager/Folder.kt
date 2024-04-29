package com.example.filemanager

data class Folder(
    val name: String,
    var folders: List<Folder> = emptyList(),
    var files: List<File> = emptyList()
)