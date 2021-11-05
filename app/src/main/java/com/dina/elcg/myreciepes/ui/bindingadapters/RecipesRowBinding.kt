package com.dina.elcg.myreciepes.ui.bindingadapters

import android.graphics.Color
import android.graphics.Color.green
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.dina.elcg.myreciepes.R

class RecipesRowBinding {

    companion object {

        @BindingAdapter("setNumbers")
        @JvmStatic
        fun setNumbers(textView: TextView, value: Int) {
            textView.text = value.toString()
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url:String) {
            imageView.load(url){
                crossfade(600)
                error(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
            }
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, isVegan: Boolean) {
            if (isVegan) {
                if (view is TextView) view.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
                if (view is ImageView) view.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
            }
        }

    }
}