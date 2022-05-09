package com.example.youtube

import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

class MelonDetailActivity : AppCompatActivity() {
    lateinit var playPauseButton : ImageView
    lateinit var mediaPlayer: MediaPlayer
    lateinit var melonItemList: ArrayList<MelonItem>
    var position = 0
        set(value){
            if(value <= 0) field = 0
            else if(value >= melonItemList.size ) field = melonItemList.size-1
            else field = value
        }
    var is_playing: Boolean = true // is_playing의 값에 따라 재생버튼을 보일지 멈춤버튼을 보일지 설정
        set(value){
            if(value == true){ // 음악을 재생중인 경우
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(R.drawable.pause, this.theme) // 멈춤 버튼이 보이게
                )
            }else{ // 음악이 재생중이 아닌 경우
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(R.drawable.play, this.theme) // 재생 버튼이 보이게
                )
            }
            field = value
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melon_detail)
        melonItemList = intent.getSerializableExtra("melon_item_list") as ArrayList<MelonItem> //melonItemList를 Serializable을 통해 가져옴
        position = intent.getIntExtra("position", 0) // 클릭한 뷰의 position을 가져옴
        playMelonItem(melonItemList[position]) // 노래 바로 재생
        changeThumbnail(melonItemList[position]) // 썸네일 변경

        playPauseButton = findViewById(R.id.play)
        playPauseButton.setOnClickListener { // 재생/멈춤 버튼 클릭 이벤트 처리
            if(is_playing) { // 재생 중
                is_playing = false // is_playing 값을 갱신 하면 위에 set에서 처리한 바와 같이 멈춤 or 재생 버튼을 보이게 함
                mediaPlayer.pause() // 멈춤
            }
            else { // 재생 중 아님
                is_playing = true
                mediaPlayer.start()
            }
        }
        findViewById<ImageView>(R.id.back).setOnClickListener { // 이전 노래 버튼 클릭 이벤트 처리
            mediaPlayer.stop() // 현재 재생 중인 노래를 일단 멈추고
            position -= 1 // position = position -1
            playMelonItem(melonItemList[position]) //노래 재생
            changeThumbnail(melonItemList[position]) //썸네일 변경

        }
        findViewById<ImageView>(R.id.next).setOnClickListener { // 다음 노래 버튼 클릭 이벤트 처리
            mediaPlayer.stop()
            position += 1
            playMelonItem(melonItemList[position])
            changeThumbnail(melonItemList[position])
        }

    }
    fun playMelonItem(melonItem: MelonItem){ // 파라미터로 받은 melonItem 노래를 재생하는 함수
        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(melonItem.song)
        )

        mediaPlayer.start()

    }
    fun changeThumbnail(melonItem: MelonItem){ // 썸네일을 melonItem의 썸네일로 변경하는 함수
        findViewById<ImageView>(R.id.thumbnail).apply{
            val glide = Glide.with(this@MelonDetailActivity)
            glide.load(melonItem.thumbnail).into(this)
        }
    }


}