package com.mstar004.projectonhilt.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

object ObjectUtils {

    fun getRootDirPath(context: Context): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(context.applicationContext,
                null)[0]
            file.absolutePath + "/new_versions"
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }
}