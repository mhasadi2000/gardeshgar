package tn.yassin.discovery.Views.Activity


import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomDialogs
import tn.yassin.discovery.Utils.ReadyFunc
import tn.yassin.prayertimes.PrayJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//
var FajrResult = "--:--"
var DhuhrResult = "--:--"
var AsarResult = "--:--"
var MaghrebResult = "--:--"
var IshaResult = "--:--"
//

class PrayerTimes : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null

    private lateinit var txtFajr: TextView
    private lateinit var txtDhuhr: TextView
    private lateinit var txtAsar: TextView
    private lateinit var txtMaghrib: TextView
    private lateinit var txtIsha: TextView
    private lateinit var txtNextSalat: TextView
    private lateinit var txtTimeNextSalat: TextView
    ////////////////////////////////////////////////

    ///
    val Ready = ReadyFunc()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prayertimes)

        InitView()
        DayName()
        DynamicClock()


        ReadPositionData()

        FirstTimeRun()
    }

    fun FirstTimeRun() {
        val sharedPref = getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE)
        val FirstTimeRun = sharedPref.getBoolean("FirstTimeRun", true)
        if (FirstTimeRun) {
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.informations, null)
            val msg = CustomDialogs()
            msg.ShowDialogInformations(this, view)
        }
    }


    fun InitView() {
        txtFajr = findViewById(R.id.txtFajr)
        txtDhuhr = findViewById(R.id.txtDhuhr)
        txtAsar = findViewById(R.id.txtAsar)
        txtMaghrib = findViewById(R.id.txtMaghrib)
        txtIsha = findViewById(R.id.txtIsha)
        txtNextSalat = findViewById(R.id.txtNextSalat)
        txtTimeNextSalat = findViewById(R.id.txtTimeNextSalat)
        //RestTime = findViewById(R.id.RestTime)
        //////////////////////////////////////////

    }



    fun HideKeyboard(textView: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(textView.windowToken, 0)
    }


    fun DynamicClock() {
        val textClock = findViewById<View>(R.id.textClock) as TextClock
        textClock.format12Hour = null
        //textClock.format12Hour = "hh:mm:ss "
        //textClock.format24Hour = "k:mm:ss"
    }

    fun DayName() {
        val txtDayNow = findViewById<TextView>(R.id.txtDayNow)
        txtDayNow.text = getdayName()
    }

    fun getdayName(): String {
        var day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);
        when (day) {
            1 -> return "یکشنبه"
            2 -> return "دوشنبه"
            3 -> return "سه شنبه"
            4 -> return "چهارشنبه"
            5 -> return "پنج شنبه"
            6 -> return "جمعه"
            7 -> return "شنبه"
            else -> return "یکشنبه"
        }
    }

    fun LoadPrayTimeAsync() {

        val client = AsyncHttpClient()
        val url =
            "http://api.aladhan.com/v1/timingsByCity?city=" + "Tehran" + "&country=" + "Iran" + "&method=8"
        client.get(url, object : JsonHttpResponseHandler() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                super.onSuccess(statusCode, headers, response)
                val gson = Gson()
                val pray = gson.fromJson(response.toString(), PrayJson::class.java)

                FajrResult = pray.data.timings.Fajr
                DhuhrResult = pray.data.timings.Dhuhr
                AsarResult = pray.data.timings.Asr
                MaghrebResult = pray.data.timings.Maghrib
                IshaResult = pray.data.timings.Isha

                //////////////////////////////////////////////::
                txtFajr.text = FajrResult
                txtDhuhr.text = DhuhrResult
                txtAsar.text = AsarResult
                txtMaghrib.text = MaghrebResult
                txtIsha.text = IshaResult
                /////////////////////////////////////////////::
                ThenextSalatis(FajrResult, DhuhrResult, AsarResult, MaghrebResult, IshaResult)
                /////////////////////////////////////////////::

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
                super.onFailure(statusCode, headers, throwable, errorResponse)
                println(throwable?.message)
            }
        })

    }


    fun SavePositionData() {
        val sharedPreferences = getSharedPreferences("PrayTime", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply()
    }

    fun ReadPositionData() {

        LoadPrayTimeAsync()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ThenextSalatis(Fajer: String, Dhoher: String, Asar: String, Moghreb: String, Isha: String) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = current.format(formatter)

        var NexTsalaT = ""

        if (currentDate <= Fajer || currentDate > Isha) {
            txtNextSalat.setText("صبح")
            txtTimeNextSalat.text = Fajer
            NexTsalaT = Fajer
        }
        if (currentDate > Fajer && currentDate <= Dhoher) {
            txtNextSalat.setText("ظهر")
            txtTimeNextSalat.text = Dhoher
            NexTsalaT = Dhoher
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate <= Asar) {
            txtNextSalat.setText("عصر")
            txtTimeNextSalat.text = Asar
            NexTsalaT = Asar
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate > Asar && currentDate <= Moghreb) {
            txtNextSalat.setText("مغرب")
            txtTimeNextSalat.text = Moghreb
            NexTsalaT = Moghreb
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate > Asar && currentDate > Moghreb && currentDate <= Isha) {
            txtNextSalat.setText("عشاء")
            txtTimeNextSalat.text = Isha
            NexTsalaT = Isha
        }

        //CalculateDiffTwoTimes(NexTsalaT)
    }



    fun imgSoundClicked(imgSound : ImageView)
    {
        var showingFirst = true
        imgSound.setOnClickListener {
            if(showingFirst == true){
                imgSound.setImageResource(R.drawable.soundoff)
                showingFirst = false;
            }else{
                imgSound.setImageResource(R.drawable.sound)
                showingFirst = true;
            }
        }
    }



    fun PlayAdhan() {
        if (mMediaPlayer == null) {
            //mMediaPlayer = MediaPlayer.create(this, R.raw.adhan)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun RuninBackground()
    {
        val thread = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(1000)
                        runOnUiThread {

                            /// Code Here


                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        thread.start()
    }
}