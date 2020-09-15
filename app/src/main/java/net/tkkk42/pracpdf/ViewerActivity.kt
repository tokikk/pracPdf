package net.tkkk42.pracpdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import permissions.dispatcher.RuntimePermissions
import java.io.File

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val bundle = Bundle()
        bundle.putString("PATH", "/sdcard/Download/NMQ8311-01付属書2-P.pdf")

        val fragment = PageFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(R.id.vliner, fragment)
            .commit()
    }



}