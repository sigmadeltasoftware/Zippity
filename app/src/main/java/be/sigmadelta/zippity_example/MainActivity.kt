package be.sigmadelta.zippity_example

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import be.sigmadelta.zippitylib.Zippity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipFile

/**
 * Created by Bojan Belic on 19/03/2018
 * Copyright © 2018 Persgroep - All rights reserved
 */
class MainActivity: Activity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_unzip_btn).setOnClickListener { _ ->

            try {
                val zip = getTestZip(this).absolutePath
                Log.d(TAG, "FilePath: $zip")

                Zippity.unzip(ZipFile(zip), File("${getExternalFilesDir(null)}"))
                Toast.makeText(this, "Successfully unzipped the testfile!", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Log.e(TAG, e.localizedMessage)
            }
        }

        findViewById<Button>(R.id.main_zip_btn).setOnClickListener { _ ->
            try {

                val fileList = arrayListOf(
                        File("${getExternalFilesDir(null)}/readme.txt"),
                        File("${getExternalFilesDir(null)}/readme_2.txt"),
                        File("${getExternalFilesDir(null)}/readme_dir/readme.txt"))

                Zippity.zip(fileList, File("${getExternalFilesDir(null)}/newzip.zip"))

                Toast.makeText(this, "Successfully zipped the new zipfile!", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Log.e(TAG, e.localizedMessage)
            }
        }
    }

    @Throws(IOException::class)
    private fun getTestZip(context: Context): File {
        val cacheFile = File(context.cacheDir, "test")

        try {
            assets.open("test.zip").use { input ->
                FileOutputStream(cacheFile).use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: IOException) {
            throw IOException("Could not open testzip", e)
        }
        return cacheFile
    }

}