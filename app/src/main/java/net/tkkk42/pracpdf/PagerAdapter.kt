package net.tkkk42.pracpdf

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PagerAdapter(private val items: List<Bitmap>) :RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

        fun bind(img: Bitmap, position: Int) {
            image.setImageBitmap(img)
            image.setTag("img_${position.toString()}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_image, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)

    }

    fun getItem(position: Int) {

    }

    override fun getItemCount(): Int = items.size

}