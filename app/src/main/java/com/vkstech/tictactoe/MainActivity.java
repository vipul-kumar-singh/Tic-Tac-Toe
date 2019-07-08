package com.vkstech.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger(MainActivity.class.getName());

    private ImageView counter;

    private int activePlayer = 0; //0:yellow , 1: red, 2: empty

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameActive = true;

    private Button replayButton;
    private TextView winnerText;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replayButton = findViewById(R.id.replayButton);
        winnerText = findViewById(R.id.winnerName);
        winnerText.setVisibility(View.INVISIBLE);
        gridLayout = findViewById(R.id.gridLayout);
    }

    public void dropIn(View view) {
        logger.info("MainActivity dropIn..");

        counter = (ImageView) view;

        Integer tappedCounter = Integer.parseInt(counter.getTag().toString());

        logger.info("Current Tag : " + tappedCounter);

        if (gameState[tappedCounter] != 2 || !gameActive)
            return;

        gameState[tappedCounter] = activePlayer;

        counter.setTranslationY(-1500);

        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.red);
            activePlayer = 1;
        } else {
            counter.setImageResource(R.drawable.yellow);
            activePlayer = 0;
        }

        counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                gameActive = false;

                String message;

                if (activePlayer == 0)
                    message = "Winner is Yellow";
                else
                    message = "Winner is Red";

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                winnerText.setText(message);
                replayButton.setVisibility(View.VISIBLE);
                winnerText.setVisibility(View.VISIBLE);

            }
        }
    }

    public void playAgain(View view) {
        logger.info("MainActivity playAgain..");
        replayButton.setVisibility(View.INVISIBLE);
        winnerText.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        gameActive = true;

        for (int i = 0; i < gameState.length; i++)
            gameState[i] = 2;

    }
}
