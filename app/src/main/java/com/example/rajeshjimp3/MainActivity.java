package com.example.rajeshjimp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {


    Button playBtn;
    Button pauseBtn;
    ImageView imgSong;
    Button nextBtn;
    Button prevBtn;
    SeekBar seekBarVol;
    SeekBar seekBarAudio;


    int[] songResource = {R.raw.jisgalimeintera,R.raw.aatejaatekhoobsurat,
            R.raw.bheegibheegiraatooinmein,R.raw.gungunarahehaibhanwre,
            R.raw.mainetereliyehi,R.raw.teramerasaathrahe,R.raw.yehsamasamahai,
            R.raw.yehshaammastani};

    int [] imageResource = {R.drawable.jisgalimeinteraghar,R.drawable.aatejatekhoobsurat,
    R.drawable.bheegibheegiraatoinmein,R.drawable.gungunarahehain,
    R.drawable.mainetereliehisaat, R.drawable.teramerasaathrahe,
            R.drawable.yehsamasamahaiye,R.drawable.yehshaammastani};

    Timer timer;
    Timer timerForAudioSeek;

    int currentSong = 0;


    private AudioManager audioManager;
    private  MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  playBtn = findViewById(R.id.playBtn);
  imgSong = findViewById(R.id.imageView4);
  pauseBtn =findViewById(R.id.pauseBtn);
  nextBtn = findViewById(R.id.nextBtn);
  prevBtn = findViewById(R.id.prevBtn);

  seekBarVol = findViewById(R.id.seekBar2);
  seekBarAudio = findViewById(R.id.seekBar);
  audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
 mediaPlayer = MediaPlayer.create(MainActivity.this,songResource[currentSong]);
int maxVolOfDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
int currentVolOfDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

seekBarVol.setMax(maxVolOfDevice);
seekBarVol.setProgress(currentVolOfDevice);

seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser == true){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});



  playBtn.setOnClickListener(this);
  pauseBtn.setOnClickListener(this);
  nextBtn.setOnClickListener(this);
  prevBtn.setOnClickListener(this);

  seekBarAudio.setMax(mediaPlayer.getDuration());
  seekBarAudio.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {

    switch (v.getId()){

        case R.id.playBtn:
   // if(currentSong!=0){timer.cancel(); }

    timer = new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            mediaPlayer.start();
        }
    },0);

    timerForAudioSeek = new Timer();
     timerForAudioSeek.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
             seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
         }
     },0,1000);




    break;

        case R.id.pauseBtn:
    mediaPlayer.pause();
    timer.cancel();
    timerForAudioSeek.cancel();


   break;

        case R.id.nextBtn:
     mediaPlayer.pause();
   if(currentSong+1<songResource.length){
       currentSong++;

       mediaPlayer = MediaPlayer.create
               (MainActivity.this,songResource[currentSong]);

   } else if(currentSong+1>=songResource.length){
       currentSong=0;
       mediaPlayer= MediaPlayer.create
               (MainActivity.this,songResource[currentSong]);
   }
   imgSong.setImageResource(imageResource[currentSong]);

   timer=new Timer();
   timer.schedule(new TimerTask() {
    @Override
    public void run() {
    mediaPlayer.start();
    }
    },0);



            timerForAudioSeek = new Timer();
            timerForAudioSeek.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
                }
            },0,1000);






   break;

        case R.id.prevBtn:
            mediaPlayer.pause();
   if(currentSong==0){currentSong=songResource.length-1;}
   else if(currentSong!=0){currentSong--;}
            mediaPlayer= MediaPlayer.create
    (MainActivity.this,songResource[currentSong]);

        imgSong.setImageResource(imageResource[currentSong]);



        timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mediaPlayer.start();
                }
            },0);


       timerForAudioSeek = new Timer();
       timerForAudioSeek.scheduleAtFixedRate(new TimerTask() {
       @Override
       public void run() {
       seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
       }
       },0,1000);







   break;





    }





    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

     if(fromUser==true){
     mediaPlayer.seekTo(progress);

     }



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
