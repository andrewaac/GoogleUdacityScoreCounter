package com.googleudacity.andrewcunningham.scorekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import static android.view.View.GONE;

/**
 * This class is set up to reorganize its layout depending on whether it is to be used for one
 * player or by two. By getting out the extra from the intent that started it, the Activity will
 * then set itself up.
 */
public class CounterActivity extends Activity implements View.OnClickListener {

    // Values
    public static final String COUNTER_TYPE = "COUNTER_TYPE";
    public static final int ONE_PLAYER = 1;
    public static final int TWO_PLAYERS = 2;
    private int counterType;
    private int teamOneScoreCount = 0;
    private int teamTwoScoreCount = 0;

    private TextView teamTitle, teamDesc, teamOneScore, teamTwoScore, teamOneTitle;

    // Other
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        counterType = getIntent().getIntExtra(COUNTER_TYPE, 1);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        // Title Box
        teamTitle = (TextView) findViewById(R.id.team_title);
        teamDesc = (TextView) findViewById(R.id.team_in_lead);

        // Team Titles and Scores
        teamOneTitle = (TextView) findViewById(R.id.team_one_title);
        teamOneScore = (TextView) findViewById(R.id.team_one_score);
        teamTwoScore = (TextView) findViewById(R.id.team_two_score);

        // Buttons
        Button resetButton = (Button) findViewById(R.id.reset_button);
        ImageButton teamOnePlusBtn = (ImageButton) findViewById(R.id.team_one_plus);
        ImageButton teamOneMinusBtn = (ImageButton) findViewById(R.id.team_one_minus);
        ImageButton teamTwoPlusBtn = (ImageButton) findViewById(R.id.team_two_plus);
        ImageButton teamTwoMinusBtn = (ImageButton) findViewById(R.id.team_two_minus);

        resetButton.setOnClickListener(this);
        teamOnePlusBtn.setOnClickListener(this);
        teamOneMinusBtn.setOnClickListener(this);
        teamTwoPlusBtn.setOnClickListener(this);
        teamTwoMinusBtn.setOnClickListener(this);

        // Layouts
        LinearLayout twoPlayerLayout = (LinearLayout) findViewById(R.id.team_two_layout);

        if (counterType == 1) twoPlayerLayout.setVisibility(GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (counterType == ONE_PLAYER) {
            teamTitle.setText(R.string.your_score);
            String score = String.format(Locale.UK, "%d", teamOneScoreCount);
            teamDesc.setText(score);
            teamOneTitle.setVisibility(GONE);
            teamOneScore.setVisibility(GONE);
        } else {
            String teamOneString = String.format(Locale.UK, "%d", teamOneScoreCount);
            teamOneScore.setText(teamOneString);
            String teamTwoString = String.format(Locale.UK, "%d", teamTwoScoreCount);
            teamTwoScore.setText(teamTwoString);
            updateWinner();
        }
    }

    @Override
    public void onClick(View v) {
        vibrator.vibrate(50);
        if (v.getId() == R.id.team_one_plus) {
            alterTeamOneScore(1);
        } else if (v.getId() == R.id.team_one_minus) {
            alterTeamOneScore(-1);
        } else if (v.getId() == R.id.team_two_plus) {
            alterTeamTwoScore(1);
        } else if (v.getId() == R.id.team_two_minus) {
            alterTeamTwoScore(-1);
        } else if (v.getId() == R.id.reset_button){
            resetScores();
        }

    }

    private void alterTeamOneScore(int value) {
        if (teamOneScoreCount + value >= 0) {
            teamOneScoreCount += value;
            String teamOneString = String.format(Locale.UK, "%d", teamOneScoreCount);
            if (counterType == ONE_PLAYER) {
                teamDesc.setText(teamOneString);
            } else {
                teamOneScore.setText(teamOneString);
                updateWinner();
            }
        }
    }

    private void alterTeamTwoScore(int value) {
        if (teamTwoScoreCount + value >= 0) {
            teamTwoScoreCount += value;
            String teamTwoString = String.format(Locale.UK, "%d", teamTwoScoreCount);
            teamTwoScore.setText(teamTwoString);
            updateWinner();
        }
    }

    private void updateWinner() {
        String toDisplay = "";
        if (teamOneScoreCount == teamTwoScoreCount) {
            toDisplay = getString(R.string.equal);
        } else if (teamOneScoreCount > teamTwoScoreCount) {
            toDisplay = getString(R.string.team_one);
        } else if (teamOneScoreCount < teamTwoScoreCount) {
            toDisplay = getString(R.string.team_two);
        }
        teamDesc.setText(toDisplay);
    }

    private void resetScores(){
        teamOneScoreCount = 0;
        teamTwoScoreCount = 0;
        String teamOneString = String.format(Locale.UK, "%d", teamOneScoreCount);
        String teamTwoString = String.format(Locale.UK, "%d", teamTwoScoreCount);

        if (counterType == ONE_PLAYER) {
            teamDesc.setText(teamOneString);
        } else {
            teamOneScore.setText(teamOneString);
            teamTwoScore.setText(teamTwoString);
            updateWinner();
        }
    }


}
