package com.yeuristic

import java.io.*

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("unused-resources.txt is not defined")
        return
    }
    val unusedResourcesFilePath = args[0]
    val unusedResourcesFile = File(unusedResourcesFilePath)
    val androidProjectFolderPath = if (args.size >= 2) args[1] else "."
    val androidProjectFolder = File(androidProjectFolderPath)

    val outputFile = File(androidProjectFolder, "removed-resources.txt")
    val outputWriter = BufferedWriter(FileWriter(outputFile))

    val reader = BufferedReader(InputStreamReader(FileInputStream(unusedResourcesFile)))
    var line = reader.readLine()
    val resourceDataList: MutableList<ResourceData> = ArrayList()
    while (line != null) {
        val temp = line.split("/")
        if (temp.size == 3) {
            val folder = cleanResourceFolder(temp[1])
            val file = temp[2]
            resourceDataList.add(ResourceData(folder, file))
        }
        line = reader.readLine()
    }
    val androidModuleDataList: List<ModuleData> = androidProjectFolder.listFiles(FileFilter {
        it.isDirectory && File(it, "src/main/res").exists()
    }
    ).map {
        val resFolders = File(it, "src/main").listFiles { childFile ->
            childFile.name.startsWith("res")
        }.toList()
        ModuleData(it.name, resFolders)
    }

    resourceDataList.forEach { resourceData ->
        val contains = androidModuleDataList.filter {
            var found = false
            for (resFolder in it.resFolders) {
                val file = File(resFolder, "${resourceData.folder}/${resourceData.file}")
                if (file.exists()) {
                    file.delete()
                    found = true
                }
            }
            found
        }
        val message = if (contains.isEmpty()) {
             "$resourceData not found"

        } else {
           "$resourceData exist in ${contains.joinToString { it.moduleName }}"
        }
        println(message)
        outputWriter.append(message)
        outputWriter.newLine()
    }

    outputWriter.flush()
    outputWriter.close()
}
