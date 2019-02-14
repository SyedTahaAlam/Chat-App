package com.example.syedtahaalam.chat.Adapters



import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout

import com.example.syedtahaalam.chat.R
import java.io.InputStream
import kotlin.collections.ArrayList
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.example.syedtahaalam.chat.ViewImage
import com.google.firebase.storage.FirebaseStorage


class FullScreenImageAdapter(private val _activity: Activity, private val _imagePaths: ArrayList<String>,var check:Boolean) : PagerAdapter() {
    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return this._imagePaths.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imgDisplay:TouchImageView?=null


        inflater = _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewLayout = inflater!!.inflate(R.layout.adapter_item_view, container,

                false)
        imgDisplay = viewLayout.findViewById(R.id.imgDisplay)as TouchImageView
        if(check){
            FirebaseStorage.getInstance().getReference("taha").child(_imagePaths[position]).downloadUrl.addOnSuccessListener() {
                Log.e("taha",it.toString())
                Glide.with(_activity).load(it.toString()).into(imgDisplay)
                Log.e("TAG", "hel i was")

            }.addOnFailureListener(){
                Log.e("TAG", it.localizedMessage)
            }

            imgDisplay!!.setOnClickListener {
                (it as TouchImageView).click()
            }
        }else
            Glide.with(_activity)
                    .load(_imagePaths[position])
                    .into(imgDisplay)

//        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//
//        val inputStream: InputStream =_activity.getContentResolver().openInputStream(Uri.parse(_imagePaths[position]));
//        val bitmap:Bitmap=BitmapFactory.decodeStream(inputStream)
//        val bitmap = MediaStore.Images.Media.getBitmap(_activity.getContentResolver(), Uri.parse(_imagePaths!![position]))
//        imgDisplay!!.setImageBitmap(bitmap)


        (container as ViewPager).addView(viewLayout)

        return viewLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout)

    }
}