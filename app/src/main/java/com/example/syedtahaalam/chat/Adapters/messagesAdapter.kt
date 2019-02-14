package com.example.syedtahaalam.chat.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

import com.bumptech.glide.Glide
import com.example.syedtahaalam.chat.R
import com.example.syedtahaalam.chat.R.id.images
import com.example.syedtahaalam.chat.R.id.imgDisplay
import com.example.syedtahaalam.chat.utils.messeages
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main2.*

import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.*
import android.widget.*
import kotlin.math.roundToInt
import android.util.DisplayMetrics
import com.example.syedtahaalam.chat.MainActivity


class messagesAdapter(internal var messages: ArrayList<messeages>, internal var context: Context,internal var window:Display) : RecyclerView.Adapter<messagesAdapter.MyView>() {

    inner class MyView(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var message: TextView
        var mImageView: ImageView
        var date:TextView
        var base:LinearLayout
        init {
            message = itemView!!.findViewById(R.id.message)
            mImageView = itemView!!.findViewById(R.id.image)
            date=itemView!!.findViewById(R.id.time_of_message)
            base=itemView!!.findViewById(R.id.base_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        var itemView:View?=null
        if(viewType==0) {
             itemView=LayoutInflater.from(parent.context).inflate(R.layout.chat_send, parent, false)
        }
        else{
            itemView=LayoutInflater.from(parent.context).inflate(R.layout.chat_recieve, parent, false)
        }
        return MyView(itemView)
        //

    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.message.text = messages[position].messages
        var point: Point=Point()
        val displaymetrics = DisplayMetrics()
        window.getMetrics(displaymetrics)
        val width=(displaymetrics.widthPixels*.6).roundToInt()
        holder.message.maxWidth=width
//        holder.mImageView.maxWidth=x
//        Log.e("x",)
//        Toast.makeText(context,(x).toString(),Toast.LENGTH_LONG).show()
        if (!messages[position].imagesUrl!!.isNullOrEmpty()&& messages[position].imagesUrl!=null && !messages[position].imagesUrl!!.equals("")){
        if (!messages[position].uri!!.isNullOrEmpty() &&  messages[position].imagesUrl!=null ){
            window.getSize(point)
            var x=((point.x)*0.6).roundToInt()
            holder.mImageView.layoutParams.width=x
            var y=((point.y)*.25).roundToInt()
            holder.mImageView.layoutParams.height=y
            Glide.with(context).load(messages[position].uri).into(holder.mImageView)
            Log.e("TAG", "hel i is")
        }

        if (messages[position].isUriPresent!!){
        if (!messages[position].imagesUrl!!.isNullOrEmpty()&& messages[position].imagesUrl!=null ){
            window.getSize(point)
            var x=((point.x)*0.6).roundToInt()
            holder.mImageView.layoutParams.width=x
            var y=((point.y)*.25).roundToInt()
            holder.mImageView.layoutParams.height=y
            //Glide.with(context).load(messages.get(position).getImagesUrl()).error().into(holder.mImageView);
            FirebaseStorage.getInstance().getReference("taha").child(messages[position].imagesUrl!!).downloadUrl.addOnSuccessListener() {

                Glide.with(context).load(it).into(holder.mImageView)

                }.addOnFailureListener {

                Glide.with(context).load(messages[position].uri).into(holder.mImageView)
                Log.e("TAG", it.localizedMessage)
            }
        }}
        else{
            if (!messages[position].imagesUrl!!.isNullOrEmpty()&& messages[position].imagesUrl!=null ){
            window.getSize(point)
            var x=((point.x)*0.6).roundToInt()
            holder.mImageView.layoutParams.width=x
            var y=((point.y)*.25).roundToInt()
            holder.mImageView.layoutParams.height=y
            FirebaseStorage.getInstance().getReference("taha").child(messages[position].imagesUrl!!).downloadUrl.addOnSuccessListener() {
                Log.e("taha",it.toString())
                Glide.with(context).load(it.toString()).into(holder.mImageView)
                Log.e("TAG", "hel i was")

            }.addOnFailureListener(){
                Log.e("TAG", it.localizedMessage)
            }
        }}}
        var date=SimpleDateFormat("dd-MM-yy").parse(messages[position].chats.date)
        val dates = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yy")
        Log.v("date",dateFormat.format(date))
        var currentDate=SimpleDateFormat("dd-MM-yy").parse(dateFormat.format(dates))
        if(date.compareTo(currentDate)<0){
            holder.date.setText(messages[position].chats.time+" "+messages[position].chats.date)
        }
        else{
            holder.date.setText(messages[position].chats.date)
        }
        holder.base.setOnClickListener {
            (it.context as MainActivity).click(messages[position].id)
        }
    }


    override fun getItemViewType(position: Int): Int {
        if(messages[position].chats.user!!.equals("taha"))
        {
            return 1
        }
        else{
            return 0
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
