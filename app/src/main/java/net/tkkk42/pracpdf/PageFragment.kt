package net.tkkk42.pracpdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.nfc.Tag
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_image.view.*
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
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val adapter = viewpager2.adapter
                if (adapter != null) {
                    cur_page.text = "${position+1}/${adapter.itemCount}" }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    viewpager2.getChildAt(0).findViewWithTag<ImageView>("img_${viewpager2.currentItem.toString()}").scaleX = 1.0f
                    viewpager2.getChildAt(0).findViewWithTag<ImageView>("img_${viewpager2.currentItem.toString()}").scaleY = 1.0f
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(path: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString("PATH", path)
                }
            }
    }
}