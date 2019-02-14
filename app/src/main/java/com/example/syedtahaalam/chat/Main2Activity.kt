package com.example.syedtahaalam.chat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.Nullable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.FileNotFoundException
import javax.sql.DataSource
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception
import com.bumptech.glide.load.engine.GlideException
import com.example.syedtahaalam.chat.R.id.images
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


class Main2Activity : AppCompatActivity() {
    private val SELECT_PHOTO:Int=100
    var image:Uri?=null
    var img:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    img=findViewById(R.id.images)
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, SELECT_PHOTO)
    }
    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_PHOTO -> if (resultCode === Activity.RESULT_OK) {
                try {
                    image = data!!.getData()
                        var check:Boolean=false
                    val bitmap = MediaStore.Images.Media.getBitmap(this@Main2Activity.getContentResolver(), image)
//                   Glide.with(this)
//                           .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGTVf63Vm3XgOncMVSOy0-jSxdMT8KVJIc8WiWaevuWiPGe0Pm"
//                           )
//                           .listener(object: RequestListener<Drawable> {
//
//                               override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
//                                   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                               }
//
//                               override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
//                                   // log exception
//                                   Log.e("TAG", "Error loading image")
//
//                                   check=true
//                                   return false
//                               }
//
//
//                           }).into(images)
//
                    // Instantiate the RequestQueue.
                    val imageView:ImageView=findViewById(R.id.images)
                    imageView.setImageBitmap(bitmap)
                    val queue = Volley.newRequestQueue(this)
                     FirebaseStorage.getInstance().getReference("taha").child("4d729745-30c2-434e-a3a9-ea54b4ca2740").downloadUrl.addOnSuccessListener(){

                         Glide.with(this).load(it.toString()).into(imageView)




                     }.addOnFailureListener {
                         imageView.setImageBitmap(bitmap)
                         Log.e("TAG",it.localizedMessage)
                     }

// Request a string response from the provided URL.

                            if(check){
                                img!!.setImageBitmap(bitmap!!)
                                check=false
                            }

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }

    }


    fun onFail(){
        Glide.with(this@Main2Activity).load(image).into(images)
    }

}
