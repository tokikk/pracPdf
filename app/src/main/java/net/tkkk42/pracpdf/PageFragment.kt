package net.tkkk42.pracpdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_page.*
import java.io.File

class PageFragment : Fragment() {

    private var path: String? = ""
    private var images: MutableList<Bitmap> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = arguments!!.getString("PATH")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val file = File(path)
        val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)

        for (i in 0..renderer.pageCount-1) {
            var curPage = renderer.openPage(i)
            var bmp = Bitmap.createBitmap(curPage.width, curPage.height, Bitmap.Config.ARGB_8888)
            curPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            images.add(bmp)
            curPage.close()
        }

        renderer.close()
        pfd.close()

        viewpager2.adapter = PagerAdapter(images)
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.adapter.cou
    }

    companion object {
        @JvmStatic
        fun newInstance(path: String, param2: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString("PATH", path)
                }
            }
    }
}