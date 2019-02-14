package com.example.syedtahaalam.chat.DbContract

import java.util.ArrayList

class chatReciever {
    var image1: String? = null
    var image2: String? = null
    var image3: String? = null
    var image4: String? = null
    var image5: String? = null
    var image6: String? = null
    var image7: String? = null
    var image8: String? = null
    var image9: String? = null
    var image10: String? = null
    var message: String? = null
    var image:ArrayList<String>?=null
    var user:String?=null
    var time:String?=null
    var date:String?=null

    constructor() {
        image= arrayListOf()
    }
    fun insert(){
        if (image!!.size>0)image1=image!!.get(0)else image1=""
        if (image!!.size>1)image2=image!!.get(1)else image2=""
        if (image!!.size>2)image3=image!!.get(2)else image3=""
        if (image!!.size>3)image4=image!!.get(3)else image4=""
        if (image!!.size>4)image5=image!!.get(4)else image5=""
        if (image!!.size>5)image6=image!!.get(5)else image6=""
        if (image!!.size>6)image7=image!!.get(6)else image7=""
        if (image!!.size>8)image8=image!!.get(7)else image8=""
        if (image!!.size>9)image9=image!!.get(8)else image9=""
        if (image!!.size>10)image10=image!!.get(9)else image10=""
    }
    constructor(image1: String, image2: String, image3: String, image4: String, image5: String, image6: String, image7: String, image8: String, image9: String, image10: String, message: String,user:String,date: String,time:String) {
        this.image1 = image1
        this.image2 = image2
        this.image3 = image3
        this.image4 = image4
        this.image5 = image5
        this.image6 = image6
        this.image7 = image7
        this.image8 = image8
        this.image9 = image9
        this.image10 = image10
        image= arrayListOf()
        this.date=date
        this.time=time
        this.user=user
        this.message = message
    }


    constructor( message: String,user:String,date: String,time:String) {
        this.image1 = ""
        this.image2 = ""
        this.image3 = ""
        this.image4 = ""
        this.image5 = ""
        this.image6 = ""
        this.image7 = ""
        this.image8 = ""
        this.image9 = ""
        this.image10 = ""
        image= arrayListOf()
        this.user=user
        this.date=date
        this.time=time
        this.message = message
    }

    constructor(message: String?, user: String?) {
        this.image1 = ""
        this.image2 = ""
        this.image3 = ""
        this.image4 = ""
        this.image5 = ""
        this.image6 = ""
        this.image7 = ""
        this.image8 = ""
        this.image9 = ""
        this.image10 = ""
        image= arrayListOf()
        this.message = message
        this.user = user
    }
}
