package com.example.tripletactics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripletactics.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Binding to View
    ActivityMainBinding bind;

    // Variables to store players names and their scores
    String player1,player2;
    int score1 = 0;
    int score2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize view
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // Get the players names from the previous activity
        Intent intent = getIntent();
        player1 = intent.getStringExtra("p1");
        player2 = intent.getStringExtra("p2");

        // Shows players names in textViews
        bind.txtP1Name.setText(player1);
        bind.txtP2Name.setText(player2);

        bind.txtP1Score.setText(String.valueOf(score1));
        bind.txtP2Score.setText(String.valueOf(score2));

        // Set up onclick event for back button
        bind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Back to landing page
                startActivity(new Intent(MainActivity.this, LandingPage.class));
                finish();
            }
        });
    }

    // Boolean to track if the game is still active
    boolean isGameActive = true;

    // variable to track which player's turn it is
    int currentPlayer = 0;

    // Array to represent the state of the board (2 means empty)
    int[] boardState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //    State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null

    // Possible winning combinations on board
    int[][] winningCombinations = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};

    // Method tp handle player's tap
    public void Playertap(View view) {
        ImageView tappedImageView = (ImageView) view;
        int tappedIndex = Integer.parseInt(tappedImageView.getTag().toString());

        // If the game is over, reset the game
        if (!isGameActive) {
            resetGame(view);
        }

        // Check if the tapped spot is empty
        if (boardState[tappedIndex] == 2) {
            boardState[tappedIndex] = currentPlayer;
            tappedImageView.setTranslationY(-1000f);

            // Update the board with the current player's symbol
            if (currentPlayer == 0) {
                tappedImageView.setImageResource(R.drawable.cross);
                currentPlayer = 1;

                // Update the status to show it's the other player's turn
                TextView statusTextView = findViewById(R.id.status);
                statusTextView.setText(player2 + "'s Turn");
            } else {
                tappedImageView.setImageResource(R.drawable.nought);
                currentPlayer = 0;
                TextView statusTextView = findViewById(R.id.status);
                statusTextView.setText(player1 + "'s Turn");
            }
            tappedImageView.animate().translationYBy(1000f).setDuration(300);
        }

        // Check if any player has won
        for (int[] winningPosition : winningCombinations) {
            TextView statusTextView = findViewById(R.id.status);

            if (boardState[winningPosition[0]] == boardState[winningPosition[1]] &&
                    boardState[winningPosition[1]] == boardState[winningPosition[2]] &&
                    boardState[winningPosition[0]] != 2) {

                // Determine the winner
                String winningMessage;
                isGameActive = false;

                if (boardState[winningPosition[0]] == 0) {
                    winningMessage = player1 + " has won";
                    score1++;
                    bind.txtP1Score.setText(String.valueOf(score1));
                } else {
                    winningMessage = player2 + " has won";
                    score2++;
                    bind.txtP2Score.setText(String.valueOf(score2));
                }

                statusTextView.setText(winningMessage);

                // Reset the game after a short delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGame(view);
                    }
                }, 1300);

                return; // Exit method after a win
            }
        }

        // Check for a draw (if all spots are filled and no winner)
        boolean isDraw = true;
        for (int state : boardState) {
            if (state == 2) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            isGameActive = false;
            TextView statusTextView = findViewById(R.id.status);
            statusTextView.setText("It's a Draw!");

            // Reset the game after a short delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetGame(view);
                }
            }, 1300);
        }
    }

    // Method to reset the game
    public void resetGame(View view) {
        isGameActive = true;
        currentPlayer = 0;

        // Reset the board state
        for (int i = 0; i < boardState.length; i++) {
            boardState[i] = 2;
        }

        // Clear the images on the board
        ((ImageView)findViewById(R.id.c1)).setImageResource(0);
        ((ImageView)findViewById(R.id.c2)).setImageResource(0);
        ((ImageView)findViewById(R.id.c3)).setImageResource(0);
        ((ImageView)findViewById(R.id.c4)).setImageResource(0);
        ((ImageView)findViewById(R.id.c5)).setImageResource(0);
        ((ImageView)findViewById(R.id.c6)).setImageResource(0);
        ((ImageView)findViewById(R.id.c7)).setImageResource(0);
        ((ImageView)findViewById(R.id.c8)).setImageResource(0);
        ((ImageView)findViewById(R.id.c9)).setImageResource(0);

        // Reset the status text to indicate player 1's turn
        TextView statusTextView = findViewById(R.id.status);
        statusTextView.setText(player1 + "'s Turn - Tap to play");
    }

    // Method to refresh the game and reset the scores
    public void refresh(View view){
        resetGame(view);

        // Reset the scores
        score1 = 0;
        score2 = 0;

        bind.txtP1Score.setText(String.valueOf(score1));
        bind.txtP2Score.setText(String.valueOf(score2));
    }
}
