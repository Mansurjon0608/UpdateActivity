package com.mstar004.projectonhilt

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.mstar004.projectonhilt.utils.ObjectUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


const val URL = "http://www.your_site/packege_name/app.apk" //here is your server URL

class UpdateActivity : BaseActivity(R.layout.activity_main) {

    lateinit var dirPath: String

    override fun initView(savedInstanceState: Bundle?) {
        dirPath = ObjectUtils.getRootDirPath(context = this).toString()

        PRDownloader.initialize(this)

        btnUpdate.setOnClickListener {
            val dialogDownload = ProgressDialog(this)
            dialogDownload.setCancelable(false)
            dialogDownload.setMessage(getString(R.string.text_app_loading))
            dialogDownload.isIndeterminate = true
            dialogDownload.setCanceledOnTouchOutside(false)
            dialogDownload.show()

            PRDownloader.download(URL, dirPath, "app.apk")
                .build()
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        Log.e("BAG", "onDownloadComplete: DONE")
                        dialogDownload.dismiss()

                        val file = File("$dirPath/app.apk")
                        val intent = Intent(Intent.ACTION_VIEW)

                        val uri = FileProvider.getUriForFile(this@UpdateActivity,
                            this@UpdateActivity.packageName + ".provider",
                            file)
                        intent.setDataAndType(uri, "application/vnd.android.package-archive")
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                        startActivity(intent)

                    }

                    override fun onError(error: Error?) {
                        Toast.makeText(this@UpdateActivity,
                            getString(R.string.text_err_replay),
                            Toast.LENGTH_SHORT).show()
                        Log.e("BAG", "onError: ERROR")
                        dialogDownload.dismiss()
                    }

                })
        }

    }

}