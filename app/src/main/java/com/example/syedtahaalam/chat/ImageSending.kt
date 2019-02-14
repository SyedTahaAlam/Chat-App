package com.example.syedtahaalam.chat

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.syedtahaalam.chat.Adapters.FullScreenImageAdapter
import com.example.syedtahaalam.chat.DbContract.chatReciever
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_image_sending.*
import kotlinx.android.synthetic.main.activity_view_image.*
import kotlinx.android.synthetic.main.bottom_chat.*
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class ImageSending : AppCompatActivity() {
        var adapter:FullScreenImageAdapter?=null
        var list:ArrayList<String>?=null
        var uid:ArrayList<String>?=null
        var pager:ViewPager?=null
        var mFirebaseDatabase:FirebaseDatabase? = null
        var mDatabaseReference1:DatabaseReference?=null
        var mDatabaseReference2:DatabaseReference?=null
        var mFirebaseStorage:FirebaseStorage?=null
        var mDatabaseStorageReference1:StorageReference?=null
        var user="taha"
    private val SELECT_PHOTO:Int=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_sending)
        pick_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.send_white))
        bottom_chat_parent.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.black))
        mFirebaseDatabase=FirebaseDatabase.getInstance()
        mDatabaseReference1=mFirebaseDatabase!!.getReference("messages").child("taha_admin")
        mDatabaseReference2=mFirebaseDatabase!!.getReference("messages").child("admin_taha")
        mFirebaseStorage= FirebaseStorage.getInstance()
        mDatabaseStorageReference1=mFirebaseStorage!!.getReference("taha")

        list=arrayListOf<String>()
        uid=arrayListOf<String>()
        list!!.add(getIntent()!!.getStringExtra("uri"))

        pager=findViewById(R.id.pager)
        adapter= FullScreenImageAdapter(_activity = this,_imagePaths = list!!,check=false)
        pager!!.adapter=adapter
        pager!!.currentItem=0
        var bacl:ImageView=findViewById(R.id.back_button)
        bacl.setOnClickListener {
            finish()
        }
        add_image.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, SELECT_PHOTO)
        }

        pick_image.setOnClickListener {
            list!!.forEach { uid!!.add(UUID.randomUUID().toString()) }
            var chat=chatReciever()
            var pd = ProgressDialog(this@ImageSending)
            pd.setMessage("Sending...")
            pd.show()
            //TODO: THIS WILL HAVE TO SHOE THE REAL NAME OF USER

            val date = Date()
            val dateFormat = SimpleDateFormat("dd-MM-yy")

            val dateFormat2 = SimpleDateFormat("hh:mm aa")
            val calender=Calendar.getInstance()
            val time=dateFormat2.format(calender.time).toString()
            val da=dateFormat.format(date).toString()
            Log.e("date",da)
            chat.date=dateFormat.format(date).toString()
            chat.time=time

            chat!!.insert()

                    var intent:Intent=Intent(this@ImageSending,MainActivity::class.java)
                    intent.putStringArrayListExtra("uris",list)
                    intent.putStringArrayListExtra("uuid",uid)
                    intent.putExtra("message",message_area.text.toString())
                    startActivity(intent)
                    finish()

//            mDatabaseStorageReference1!!.child(UUID.randomUUID().toString()).putFile(Uri.parse(list!![0])).addOnSuccessListener {
//                pd.dismiss()
//                mDatabaseReference1!!.push().setValue(chat)
//                mDatabaseReference2!!.push().setValue(chat)
//                var intent:Intent=Intent(this@ImageSending,MainActivity::class.java)
//                    intent.putStringArrayListExtra("uri",list)
//                startActivity(intent)
//            }.addOnFailureListener{
//                pd.dismiss()
//                Toast.makeText(
//                        this@ImageSending,
//                        it.message,
//                        Toast.LENGTH_LONG
//                ).show()
//            }.addOnProgressListener {
//                var no=(100.0 * it.getBytesTransferred()) / it.getTotalByteCount()
//                pd.setMessage(no.toString())
//            }


        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_PHOTO -> if (resultCode === Activity.RESULT_OK) {
                try {
                    val imageUri = data!!.getData()

                   list!!.add(imageUri.toString())
                    adapter!!.notifyDataSetChanged()

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }

}
