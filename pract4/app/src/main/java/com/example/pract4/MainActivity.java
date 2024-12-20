package com.example.pract4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerTurn = true; // True = Player 1, False = Player 2 or Bot
    private String[][] board = new String[3][3];
    private SharedPreferences preferences;
    private TextView statisticsTextView;
    private int wins, losses, draws;
    private boolean isBotEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("game_settings", MODE_PRIVATE);
        updateTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statisticsTextView = findViewById(R.id.statisticsTextView);
        loadStatistics();

        final GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlePlayerMove(button);
                }
            });
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        isBotEnabled = preferences.getBoolean("botEnabled", false);
    }

    private void updateTheme() {
        String theme = preferences.getString("theme", "light");
        if ("dark".equals(theme)) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            recreate();
        }
    }

    private void handlePlayerMove(Button button) {
        if (!button.getText().toString().isEmpty() || isGameFinished()) {
            return;
        }

        String symbol = isPlayerTurn ? "X" : "O";
        button.setText(symbol);

        int row = getButtonRow(button);
        int col = getButtonCol(button);
        board[row][col] = symbol;

        if (checkForWin()) {
            if (isPlayerTurn) {
                wins++;
            } else {
                losses++;
            }
            updateStatistics();
            resetGame();
        } else if (isBoardFull()) {
            draws++;
            updateStatistics();
            resetGame();
        } else {
            isPlayerTurn = !isPlayerTurn;
            if (isBotEnabled && !isPlayerTurn) {
                botMove();
            }
        }
    }

    private void botMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != null);

        Button button = getButtonAt(row, col);
        handlePlayerMove(button);
    }

    private Button getButtonAt(int row, int col) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int index = row * 3 + col;
        return (Button) gridLayout.getChildAt(index);
    }

    private int getButtonRow(Button button) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int index = gridLayout.indexOfChild(button);
        return index / 3;
    }

    private int getButtonCol(Button button) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int index = gridLayout.indexOfChild(button);
        return index % 3;
    }

    private boolean checkForWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return true;
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
        }
        board = new String[3][3];
        isPlayerTurn = true;
    }

    private void loadStatistics() {
        wins = preferences.getInt("Победы", 0);
        losses = preferences.getInt("Поражения", 0);
        draws = preferences.getInt("Ничья", 0);
        updateStatistics();
    }

    @SuppressLint("SetTextI18n")
    private void updateStatistics() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Победы", wins);
        editor.putInt("Поражения", losses);
        editor.putInt("Ничья", draws);
        editor.apply();
        statisticsTextView.setText("Победы: " + wins + " | Поражения: " + losses + " | Ничья: " + draws);
    }

    private boolean isGameFinished() {
        return checkForWin() || isBoardFull();
    }
}