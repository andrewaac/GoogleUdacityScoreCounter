package com.googleudacity.andrewcunningham.scorekeeper;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * A user should be able to launch the app and chose whether they want to track a score for
 * themselves or whether it is going to be between two people.
 * <p>
 * From this Activity, we will launch a different activity depending on the user's choice.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Other
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.cheers);

        Button onePlayerButton = (Button) findViewById(R.id.one_player_button);
        Button twoPlayerButton = (Button) findViewById(R.id.two_player_button);
        Button hiddenButton = (Button) findViewById(R.id.hidden_button);

        onePlayerButton.setOnClickListener(this);
        twoPlayerButton.setOnClickListener(this);
        hiddenButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        vibrator.vibrate(50);
        if (v.getId() == R.id.one_player_button) {
            launchCounter(CounterActivity.ONE_PLAYER);
        } else if (v.getId() == R.id.two_player_button) {
            launchCounter(CounterActivity.TWO_PLAYERS);
        } else if (v.getId() == R.id.hidden_button) {
            mediaPlayer.start();
        }
    }

    private void launchCounter(int counterType) {
        Intent counterIntent = new Intent(this, CounterActivity.class);
        counterIntent.putExtra(CounterActivity.COUNTER_TYPE, counterType);
        startActivity(counterIntent);
    }


}
