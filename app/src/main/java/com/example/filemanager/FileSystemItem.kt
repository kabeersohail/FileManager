package com.example.filemanager

sealed class FileSystemItem {
    data class Folder(val name: String) : FileSystemItem()
    data class File(val name: String, val type: FileType) : FileSystemItem()
}
