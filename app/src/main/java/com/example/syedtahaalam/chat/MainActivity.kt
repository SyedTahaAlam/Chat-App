package com.example.syedtahaalam.chat

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.widget.*

import java.io.FileNotFoundException
import android.text.Editable
import android.text.format.Time
import android.util.Log
import com.example.syedtahaalam.chat.Adapters.messagesAdapter
import com.example.syedtahaalam.chat.DbContract.chatReciever
import com.example.syedtahaalam.chat.utils.messeages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.bottom_chat.*

import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity()
{

    private val SELECT_PHOTO:Int=100
    private var mMessageBody:EditText?=null
    private var mPickImage: ImageView?=null
    private var selectedImage:Bitmap?=null
    private var chats:RecyclerView?=null
    private var listOfChat:ArrayList<messeages>?=null
    private var uri:ArrayList<String>?=null
    private var uuid:ArrayList<String>?=null
    private var messagesAdapter:messagesAdapter?=null
    private var ids:String?=null
    var check=false
    var mFirebaseDatabase: FirebaseDatabase? = null
    var mDatabaseReference1: DatabaseReference?=null
    var mDatabaseReference2:DatabaseReference?=null
    var user="admin"
    override fun onCreate(savedInstanceState: Bundle?) {
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState)

        /** Making this activity, full screen */
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main)
//        supportActionBar!!.hide()
//       supportActionBar!!.setCustomView(R.layout.toolbar)
        var intents=intent
        var iconChech=false
        //ids=intent.getStringExtra("id")
        FirebaseAuth.getInstance().signInWithEmailAndPassword("h@gmail.com","4500234")
        mMessageBody=findViewById(R.id.message_area)
        mPickImage=findViewById(R.id.pick_image)
        chats=findViewById(R.id.chat_list)

        listOfChat= arrayListOf()
        uuid= arrayListOf()
        uri= arrayListOf()
            var bundle=intent.extras
        uri=intents.getStringArrayListExtra("uris")
        uuid=intents.getStringArrayListExtra("uuid")
        messagesAdapter = messagesAdapter(listOfChat!!, baseContext,windowManager.defaultDisplay)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        chats!!.setLayoutManager(mLayoutManager)
        chats!!.setItemAnimator(DefaultItemAnimator())
        mFirebaseDatabase=FirebaseDatabase.getInstance()
        mDatabaseReference1=mFirebaseDatabase!!.getReference("messages").child("taha_admin")
        mDatabaseReference2=mFirebaseDatabase!!.getReference("messages").child("admin_taha")
        chats!!.setAdapter(messagesAdapter)
        attachListener()
        if(uuid!=null)
        if(!uuid!!.isEmpty())
        {
            Log.e("comm","taggagagagga")
            Log.e("comm",uuid!![0])
            var chats=chatReciever()
            chats.user=user
            chats.message=intent.extras!!.get("message").toString()
            val date = Date()
            val dateFormat = SimpleDateFormat("dd-MM-yy")

            val dateFormat2 = SimpleDateFormat("hh:mm aa")
            val calender=Calendar.getInstance()
            val time=dateFormat2.format(calender.time).toString()
            val da=dateFormat.format(date).toString()
            Log.e("date",da)
            chats.date=dateFormat.format(date).toString()
            chats.time=time
            for(i in 0 until uri!!.size){
                chats!!.image!!.add(uuid!!.get(i))
            }
            chats!!.insert()
            FirebaseStorage.getInstance().getReference("taha").child(uuid!![0]).putFile(Uri.parse(uri!![0])).addOnSuccessListener {

                if(it.task.isSuccessful){
                Log.e("comm",it.toString())
                mDatabaseReference2!!.push().setValue(chats)
                var dbf= mDatabaseReference1!!.push()
                ids=dbf.key
                dbf.setValue(chats).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        messagesAdapter!!.notifyDataSetChanged()
                        uuid!!.clear()
                        uri!!.clear()
                        ids=""
                    }
                } }
            }
        }

        mPickImage!!.setOnClickListener {
           if (!iconChech) {
               val photoPickerIntent = Intent(Intent.ACTION_PICK)
               photoPickerIntent.type = "image/*"

               startActivityForResult(photoPickerIntent, SELECT_PHOTO)
           }
            else
           {
               //TODO: THIS WILL HAVE TO SHOE THE REAL NAME OF USER
               var chat=chatReciever(message_area.text.toString(),user)
               val date = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yy")
               val dateFormat2 = SimpleDateFormat("hh:mm aa")
               val calender=Calendar.getInstance()
        val time=dateFormat2.format(calender.time).toString()
    val da=dateFormat.format(date).toString()
               Log.e("date",da)
               chat.date=dateFormat.format(date).toString()
               chat.time=time
               message_area.setText("")
               iconChech=false
               mDatabaseReference1!!.push().setValue(chat)
               mDatabaseReference2!!.push().setValue(chat)
           }

        }


//        val date = Date()
//        val dateFormat = SimpleDateFormat("dd-MM-HH")
//        Log.v("date",dateFormat.format(date))
//        val dateFormat2 = SimpleDateFormat("hh:mm aa")
////        val formattedDate = dateFormat.format().toString()
//       val calender=Calendar.getInstance()
//        val time=dateFormat2.format(calender.time).toString()
//;
//        val time2=
//        Log.v("date3",time)


        mPickImage!!.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.camera))  //line to change the image of send button

        /**
         * to watch the change the text in the message edit text
         */
        mMessageBody!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().trim()!!.length ==0) {
                    iconChech=false
                   mPickImage!!.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.camera))
                } else {
                    iconChech=true
                   mPickImage!!.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.send))
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        }

    private fun attachListener() {
        var message:messeages

        mDatabaseReference1!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
               listOfChat!!.clear()
                for (dataSnapshot in snapshot.children)
                {
                    var chat=dataSnapshot.getValue(chatReciever::class.java)
                    if (dataSnapshot.key==ids) {
                        message = messeages(chat!!.message, chat!!.image1, dataSnapshot.key, uri!![0], true, chat)
                    }else {
                        message = messeages(chat!!.message, chat!!.image1, dataSnapshot.key, "", false, chat)
                    }

                    listOfChat!!.add(message)
                    messagesAdapter!!.notifyDataSetChanged()
                }


            }
    })
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

                    val intent: Intent = Intent(this, ImageSending::class.java)
                    Toast.makeText(this,imageUri.toString(),Toast.LENGTH_LONG).show()

                    intent!!.putExtra("uri",imageUri.toString());
                    startActivity(intent);

                        check=true;

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun addMessage(message:String){

    }

    fun click(id:String?){
        var intent=Intent(this@MainActivity,ViewImage::class.java)
        intent.putExtra("id",id)
        startActivity(intent)

    }
}
