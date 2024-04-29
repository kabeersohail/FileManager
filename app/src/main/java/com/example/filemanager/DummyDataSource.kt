package com.example.filemanager

val filePaths = listOf(
    "Mosaic Hindi/.DS_Store",
    "Mosaic Hindi/Educational Content/.DS_Store",
    "Mosaic Hindi/PPT/New Microsoft PowerPoint Presentation.pptx",
    "Mosaic Hindi/PPT/New Microsoft PowerPoint Presentation.pdf",
    "Mosaic Hindi/Testimonials/Sugarcane/15 seconder Canes/Sugarcane_Rana14Mar.mp4",
    "Mosaic Hindi/Testimonials/Sugarcane/15 seconder Canes/Sugarcane_23Apr.mp4",
    "Mosaic Hindi/Testimonials/Sugarcane/WhatsApp version/WhatsApp Video 2020-05-22 at 12.06.50 PM.mp4",
    "Mosaic Hindi/Testimonials/Sugarcane/Farmer Experience/Animation-Mosaic sugarcane_11 Oct.mp4",
    "Mosaic Hindi/Testimonials/Sugarcane/Farmer Experience/Farmer Testimonial by Vishal.mp4",
    "Mosaic Hindi/Testimonials/Sugarcane/Farmer Experience/Farmer testimonial by Vishal UP.mp4",
    "Mosaic Hindi/Educational Content/Groundnut/Magna One Pager/Groundnut Z+B One Pager.jpg",
    "Mosaic Hindi/Educational Content/Groundnut/Leaflet/Groundnut Leaflet.pdf",
    "Mosaic Hindi/Educational Content/Green Grams/Leaflet/Green Gram Leaflet.pdf",
    "Mosaic Hindi/Educational Content/Mosaic Magna Z+B+C All Crop Dose/Zinc/Zinc All Crop Dose.jpg",
    "Mosaic Hindi/Educational Content/Mosaic Magna Z+B+C All Crop Dose/Calcium/Calcium All Crop Dose.pdf",
    "Mosaic Hindi/Educational Content/Mosaic Magna Z+B+C All Crop Dose/Boron/Boron All Crop Dose.jpg"
)

// Convert file paths to FileSystemItem objects
val items = filePaths.mapNotNull { path ->
    val components = path.split('/')
    val itemName = components.last()
    if (itemName.startsWith(".")) {
        // Hidden file, ignore it
        null
    } else if (itemName.contains('.')) {
        // File
        FileSystemItem.File(itemName, getFileType(itemName.substringAfterLast('.')))
    } else {
        // Folder
        FileSystemItem.Folder(itemName)
    }
}

// Helper function to get file type
fun getFileType(extension: String): FileType {
    return when (extension.toLowerCase()) {
        "pdf" -> FileType.PDF
        "jpg", "jpeg", "png", "gif" -> FileType.IMAGE
        "mp4", "avi", "mov" -> FileType.VIDEO
        else -> FileType.OTHER
    }
}