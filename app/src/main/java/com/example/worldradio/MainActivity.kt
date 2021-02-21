package com.example.worldradio

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var radio1: Button
    private lateinit var radio2: Button
    private lateinit var radio3: Button
    private lateinit var radio4: Button

    private lateinit var play: Button
    private lateinit var pause: Button


    var mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

        radio1 = findViewById(R.id.radio)
        radio2 = findViewById(R.id.radio2)
        radio3 = findViewById(R.id.radio3)
        radio4 = findViewById(R.id.radio4)

        play = findViewById(R.id.play)
        pause = findViewById(R.id.pause)

        initMediaPlayer()

        radio1.setOnClickListener() {
            setUrl()
        }


    }

    private fun initMediaPlayer() {
        this.mediaPlayer = MediaPlayer()
        //Set up MediaPlayer event listeners
        mediaPlayer!!.setOnCompletionListener(this);
        mediaPlayer!!.setOnErrorListener(this);
        mediaPlayer!!.setOnPreparedListener(this);

        //mediaPlayer.setOnInfoListener(this);
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            // Set the data source to the mediaFile location
            //mediaPlayer.setDataSource(mediaFile)
        } catch (e: IOException) {
            e.printStackTrace()
            stopMedia()
        }
        mediaPlayer!!.prepareAsync()

    }

    fun onCompletion(mp: MediaPlayer?) {
        //Invoked when playback of a media source has completed.
        stopMedia()
        //stop the servic
    }

    //Handle errors
    fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        //Invoked when there has been an error during an asynchronous operation
        when (what) {
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK -> Log.d(
                "MediaPlayer Error",
                "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK $extra"
            )
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> Log.d(
                "MediaPlayer Error",
                "MEDIA ERROR SERVER DIED $extra"
            )
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> Log.d(
                "MediaPlayer Error",
                "MEDIA ERROR UNKNOWN $extra"
            )
        }
        return false
    }

    fun onPrepared(mp: MediaPlayer?) {
        //Invoked when the media source is ready for playback.
        playMedia()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUrl(url:String) {
        //val url = "http://........" // your URL here
        this.mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare() // might take long! (for buffering, etc)
            start()
        }
    }
    private fun playMedia() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
        }
    }

    private fun stopMedia() {
        if (mediaPlayer == null) return
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        }
    }





}

private fun MediaPlayer.setOnCompletionListener(mainActivity: MainActivity) {
 mainActivity.onCompletion(mainActivity.mediaPlayer)
}

private fun MediaPlayer.setOnPreparedListener(mainActivity: MainActivity) {
    mainActivity.onPrepared(mainActivity.mediaPlayer)
}

private fun MediaPlayer.setOnErrorListener(mainActivity: MainActivity) {
    mainActivity.onError(mainActivity.mediaPlayer)
}


