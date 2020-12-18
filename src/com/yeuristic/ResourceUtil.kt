package com.yeuristic

fun cleanResourceFolder(originalFolderName: String): String {
    val indexOfV4 = originalFolderName.indexOf("-v4")
    if (indexOfV4 >= 0) {
        return originalFolderName.substring(0, indexOfV4)
    }
    return originalFolderName
}