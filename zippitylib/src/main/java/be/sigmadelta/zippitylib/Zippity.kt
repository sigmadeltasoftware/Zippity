package be.sigmadelta.zippitylib

import java.io.*
import java.util.zip.ZipFile

/**
 * Created by Bojan Belic on 19/03/2018
 * Copyright © 2018 Sigma Delta Software Solutions - All rights reserved
 */
class Zippity {
    companion object {

        @Throws(IOException::class)
        fun unzip(zipFile: ZipFile, destDir: File) {

            zipFile.entries().asSequence().forEach {
                val subFile = File(destDir, it.name)
                subFile.parentFile.mkdirs()

                if (!it.isDirectory) {
                    writeToFile(zipFile.getInputStream(it), subFile)
                }
            }
        }

        @Throws(IOException::class, FileNotFoundException::class)
        private fun writeToFile(input: InputStream, destFile: File) {

            val fileOutStream = FileOutputStream(destFile)
            val origStream = BufferedInputStream(input)
            val destStream = BufferedOutputStream(fileOutStream)

            origStream.use { orig ->
                destStream.use { dest ->
                    orig.copyTo(dest)
                }
            }
        }
    }
}