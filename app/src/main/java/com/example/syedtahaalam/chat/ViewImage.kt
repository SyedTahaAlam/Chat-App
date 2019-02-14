package com.example.syedtahaalam.chat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.syedtahaalam.chat.Adapters.FullScreenImageAdapter
import com.example.syedtahaalam.chat.DbContract.chatReciever
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view_image.*
import java.util.ArrayList
import android.view.MotionEvent
import kotlinx.android.synthetic.main.adapter_item_view.*


class ViewImage : AppCompatActivity() {
    var adapter:FullScreenImageAdapter?=null
    var list: ArrayList<String>?=null
    var images: ArrayList<String>?=null
    var back:ImageView?=null
    var pager: ViewPager?=null
    var check=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        var childName=intent.extras.get("id").toString()
        Log.e("val",childName)
        pager=findViewById(R.id.pager)
//       var based:CoordinatorLayout=findViewById(R.id.base_frame)

        list=arrayListOf<String>()
        images=arrayListOf<String>()
        adapter= FullScreenImageAdapter(_activity = this,_imagePaths = list!!,check=true)
        pager!!.adapter=adapter
        pager!!.currentItem=0
        back=findViewById(R.id.back_button)
        var chat:String?=null
        var db=FirebaseDatabase.getInstance().getReference("messages").child("taha_admin")
        db.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
            var chat=p0.child(childName).getValue(chatReciever::class.java)
                //Log.e("value",chat!!.image1)
              images=chat!!.image
                images!!.forEach{
                Log.e("value",it)
                    list!!.add(it)
                    adapter!!.notifyDataSetChanged()
                }
                if (chat!!.message!=null){
                  messages.setText(chat!!.message)
                }

            }

        })
        back_button.setOnClickListener {
finish()
        }


    }
    fun click(){
            if(!check){
                toolbar.visibility = View.GONE
                messages.visibility=View.GONE

                check=true
            }else{
                toolbar.visibility = View.VISIBLE
                messages.visibility=View.VISIBLE
                check=false
            }
    }
}
