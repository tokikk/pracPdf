package net.tkkk42.pracpdf

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import permissions.dispatcher.RuntimePermissions
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            val btn = findViewById<Button>(R.id.button)
            btn.setOnClickListener { onButtonClick() }
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CODE)
        }

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener{ onButtonClick() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Handler().post(Runnable {
                        RuntimePermissionUtils().showAlertDialog(supportFragmentManager, "ストレージの読み込み")
                    })
                }
                return
            }
        }
    }

    fun onButtonClick(){
        val intent = Intent(this, ViewerActivity::class.java )
        startActivity(intent)
    }

}