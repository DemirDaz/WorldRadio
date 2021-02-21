package com.example.worldradio

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.ExoPlayerFactory.newSimpleInstance
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.metadata.icy.IcyHeaders
import com.google.android.exoplayer2.metadata.icy.IcyInfo
import com.google.android.exoplayer2.metadata.id3.Id3Decoder

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mobeedev.library.SlidingMenuBuilder
import saschpe.exoplayer2.ext.icy.IcyHttpDataSource
import saschpe.exoplayer2.ext.icy.IcyHttpDataSourceFactory


class MainActivity : AppCompatActivity() {

    private lateinit var radio1: Button
    private lateinit var radio2: Button
    private lateinit var radio3: Button
    private lateinit var radio4: Button
    private lateinit var radio5: Button
    private lateinit var radio6: Button
    private lateinit var side: Button
    private lateinit var back: Button

    private lateinit var play: Button
    private lateinit var pause: Button
    private lateinit var pesma: TextView
    private lateinit var pevac: TextView
    private lateinit var stanica: TextView
    private lateinit var pesma2: TextView
    private lateinit var pevac2: TextView

    val TAG = "MyMessage"

    //var player:SimpleExoPlayer = SimpleExoPlayer()
    lateinit var player :SimpleExoPlayer






    /* @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    var mediaPlayer:MediaPlayer= MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        ) }

    */
   //var player: SimpleExoPlayer? = Builder(context).build()

    @SuppressLint("ResourceType")
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
        radio5 = findViewById(R.id.radio5)
        radio6 = findViewById(R.id.radio6)
        side = findViewById(R.id.side)



        play = findViewById(R.id.play)
        pause = findViewById(R.id.pause)
        pesma = findViewById(R.id.pesma)
        stanica = findViewById(R.id.stanica)


        pevac = findViewById(R.id.pevac)
        pesma.isSelected = true
        pevac.isSelected = true


        var slidingNavigation = SlidingMenuBuilder(this)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(false)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.sidemenu)
            .inject()

        side.setOnClickListener {
            slidingNavigation.openMenu()
        }
        back = findViewById(R.id.back)
        pevac2 = findViewById(R.id.pevac2)
        pesma2 = findViewById(R.id.pesma2)
        pevac2.isSelected = true
        pesma2.isSelected = true
        back.setOnClickListener {
            slidingNavigation.closeMenu()
        }
       // this.player = SimpleExoPlayer.Builder(this).build()
        this.player = ExoPlayerFactory.newSimpleInstance(this).apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(), true
            )

            addMetadataOutput(object : MetadataOutput {

                override fun onMetadata(metadata: Metadata) {
                    for (n in 0 until metadata.length()) {
                        when (val md = metadata[n]) {

                            is com.google.android.exoplayer2.metadata.id3.Id3Frame -> {
                                println(md.toString())
                                if (md.id == "TPE1") {
                                    pevac.text = md.toString().substring(30)
                                    pevac2.text = md.toString().substring(30)
                                }
                                if (md.id == "TIT2") {
                                    pesma.text = md.toString().substring(30)
                                    pesma2.text = md.toString().substring(30)
                                }

                                //Log.d(TAG, "Title: ${md.id} ")
                            }
                            else -> {
                                //if( ${md.title}) {
                                //Log.d(TAG, "Some other sort of metadata: $md")
                               // Log.d(TAG, "Some other sort of metadata: $md something")
                            }


                }
            } } } ) }



        // override fun onMetadata(metadata: Metadata) {

                    //val tmpSongName = extractSongName(metadata.toString())
                   /* for (n in 0 until metadata.length()) {
                        when (val md = metadata[n]) {

                                is com.google.android.exoplayer2.metadata.icy.IcyInfo -> {
                                    Log.d(TAG, "Title: ${md.title} URL: ${md.url}")
                                }
                                else -> {
                                    //if( ${md.title}) {
                                    //Log.d(TAG, "Some other sort of metadata: $md")
                                    Log.d(TAG, "Some other sort of metadata: $md something")
                                } */




        radio1.setOnClickListener() {
            //setUrl("http://noisefm.ru:8000/live")
           try {
            val first: MediaItem = MediaItem.fromUri("https://stream.revma.ihrhls.com/zc797/hls.m3u8")
            this.player.setMediaItem(first)
            player.prepare()
// Start the playback.
// Start the playback.
            player.play()
            pesma.text = ""
               pevac.text = ""
               stanica.text = radio1.text
            play.callOnClick()
            }
           catch(e:java.lang.Exception) {}
        }

        radio2.setOnClickListener() {
            try {
                val secondItem: MediaItem = MediaItem.fromUri("https://stream.revma.ihrhls.com/zc713/hls.m3u8")
                this.player.setMediaItem(secondItem)
                player.prepare()
// Start the playback.
// Start the playback.
                player.play()
                pesma.text = ""
                pevac.text = ""
                stanica.text = radio2.text
                play.callOnClick()}
            catch(e:java.lang.Exception) {} }


            radio3.setOnClickListener() {
                try {
                    val third: MediaItem = MediaItem.fromUri("https://stream.revma.ihrhls.com/zc2780")
                    this.player.setMediaItem(third)
                    player.prepare()
// Start the playback.
// Start the playback.
                    player.play()
                    pesma.text = ""
                    pevac.text = ""
                    stanica.text = radio3.text
                    play.callOnClick()}
                catch(e:java.lang.Exception) {}
            }
            radio4.setOnClickListener() {
                try {
                    val third: MediaItem = MediaItem.fromUri("https://stream.revma.ihrhls.com/zc8470")
                    this.player.setMediaItem(third)
                    player.prepare()
// Start the playback.
// Start the playback.
                    player.play()
                    pesma.text = ""
                    pevac.text = ""
                    stanica.text = radio4.text
                    play.callOnClick()}
                catch(e:java.lang.Exception) {}
            }

        radio5.setOnClickListener() {
            try {
                val third: MediaItem = MediaItem.fromUri("https://live.wostreaming.net/direct/maxmedia-kfcofmaac-ibc1")
                this.player.setMediaItem(third)
                player.prepare()
// Start the playback.
// Start the playback.
                player.play()
                pesma.text = ""
                pevac.text = ""
                stanica.text = radio5.text
                play.callOnClick()}
            catch(e:java.lang.Exception) {}
        }
        radio6.setOnClickListener() {
            try {
                val third: MediaItem = MediaItem.fromUri("https://stream.revma.ihrhls.com/zc7784/hls.m3u8")
                this.player.setMediaItem(third)
                player.prepare()
// Start the playback.
// Start the playback.
                player.play()
                pesma.text = ""
                pevac.text = ""
                stanica.text = radio6.text
                play.callOnClick()}
            catch(e:java.lang.Exception) {}
        }
        //https://live.wostreaming.net/direct/maxmedia-kfcofmaac-ibc1
//https://stream.revma.ihrhls.com/zc2780
         //https://stream.revma.ihrhls.com/zc7784/hls.m3u8

        play.setOnClickListener() {
            play.setBackgroundResource(R.drawable.ic_play_circle_orange)
            pause.setBackgroundResource(R.drawable.ic_pause_circle_gray)

           try {




               player.prepare()

               player.play()

           }

           catch(e:Exception) { print(e)}

        }
        pause.setOnClickListener() {
            try {
                player.pause()
                play.setBackgroundResource(R.drawable.ic_play_circle_gray)
                pause.setBackgroundResource(R.drawable.ic_pause_circle_orange)
            }
            catch(e:java.lang.Exception) {}
        }


    }

}


    /*private fun initMediaPlayer() {
        this.mediaPlayer = MediaPlayer()
        //Set up MediaPlayer event listeners
        mediaPlayer!!.setOnCompletionListener(this);
        //mediaPlayer!!.setOnErrorListener(this);
        mediaPlayer!!.setOnPreparedListener(this);

        //mediaPlayer.setOnInfoListener(this);
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            // Set the data source to the mediaFile location
            mediaPlayer.setDataSource("https://current.stream.publicradio.org/kcmp.mp3")
        } catch (e: IOException) {
            e.printStackTrace()
            stopMedia()
        }
//        mediaPlayer!!.prepareAsync()

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

        this.mediaPlayer?.setDataSource("http://wgltradio.ilstu.edu:8000/wgltjazz.mp3")
            ///prepare() // might take long! (for buffering, etc)
            this.mediaPlayer?.start()
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

private fun MediaPlayer.setOnCompletionListener(mainActivity: MainActivity) {
 mainActivity.onCompletion(mainActivity.mediaPlayer)
}

private fun MediaPlayer.setOnPreparedListener(mainActivity: MainActivity) {
    mainActivity.onPrepared(mainActivity.mediaPlayer)
}
*/








//private fun MediaPlayer.setOnErrorListener(mainActivity: MainActivity) {
  //  mainActivity.onError(mainActivity.mediaPlayer)
//}


