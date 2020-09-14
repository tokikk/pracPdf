package net.tkkk42.pracpdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import permissions.dispatcher.RuntimePermissions
import java.io.File

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        openPdfFile()
    }


    fun openPdfFile() {
        val path = "sdcard/Download/はじめに.pdf"
        val file = File(path)
        val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)
        var curPage = renderer.openPage(0)

        val bmp = Bitmap.createBitmap(curPage.width, curPage.height, Bitmap.Config.ARGB_8888)
        curPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        val imgView = findViewById<ImageView>(R.id.imageView)
        imgView.setImageBitmap(bmp)

        curPage.close()
        renderer.close()
        pfd.close()
    }

}