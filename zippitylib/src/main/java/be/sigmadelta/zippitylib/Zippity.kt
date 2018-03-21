package be.sigmadelta.zippitylib

import android.util.Log
import java.io.*
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry


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
                    BufferedInputStream(zipFile.getInputStream(it)).use { orig ->
                        BufferedOutputStream(FileOutputStream(subFile)).use { dest ->
                            orig.copyTo(dest)
                        }
                    }
                }
            }
        }

        @Throws(IOException::class, FileNotFoundException::class)
        fun zip(files: List<File>, outputZip: File) {

            if (files.isEmpty()) {
                return
            }

            val baseLineUrl = files[0].absolutePath.removeSuffix(files[0].name)
            FileOutputStream(outputZip).use { fileOutStream ->

                try {
                    ZipOutputStream(BufferedOutputStream(fileOutStream)).use { out ->
                        files.forEach { writeFileToZip(out, it, baseLineUrl) }
                    }
                } catch (e: IOException) {
                    outputZip.delete()
                    throw e
                } catch (e: RuntimeException) {
                    outputZip.delete()
                    throw e
                }

            }
        }

        @Throws(IOException::class, FileNotFoundException::class, IllegalArgumentException::class)
        fun zip(directoryToZip: String, outputZip: File) {

            val dirFile = File(directoryToZip)

            if (dirFile.exists() && dirFile.isDirectory) {
                zip(dirFile.listFiles().toList(), outputZip)
            } else {
                throw IllegalArgumentException("Invalid directory specified: $directoryToZip")
            }
        }


        @Throws(IOException::class)
        private fun writeFileToZip(out: ZipOutputStream, file: File, baseLineUrl: String) {
            Log.d("TAG", "file: ${file.absolutePath.removePrefix(baseLineUrl)}")

            when {
                file.isFile -> {
                    val zipEntry = ZipEntry(file.absolutePath.removePrefix(baseLineUrl))
                    out.putNextEntry(zipEntry)

                    out.write(file.readBytes())
                }

                file.isDirectory -> {
                    file.listFiles().forEach { subFile ->
                        val zipEntry = ZipEntry(subFile.absolutePath.removePrefix(baseLineUrl))
                        out.putNextEntry(zipEntry)

                        out.write(subFile.readBytes())
                    }
                }
            }
            out.closeEntry()
        }
    }
}