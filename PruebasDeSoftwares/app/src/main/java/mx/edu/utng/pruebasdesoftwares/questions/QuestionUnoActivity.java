package mx.edu.utng.pruebasdesoftwares.questions;

/**
 * Created by Luis Albero Grimaldo  on 25/02/2016.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import mx.edu.utng.pruebasdesoftwares.R;
import mx.edu.utng.pruebasdesoftwares.dbhelper.DBHelperCuestionario;
import mx.edu.utng.pruebasdesoftwares.model.Question;

public class QuestionUnoActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;//inicia el la posicion de la pregunta
    Question currentQ;
    TextView txtQuestion, times, scored;
    Button buttonUno, buttonDos, buttonTres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_uno_main_layout);

        DBHelperCuestionario db = new DBHelperCuestionario(this);  // my question bank class
        quesList = db.getAllQuestions();  // this will fetch all quetonall questions
        currentQ = quesList.get(qid); // the current question
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed
        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        buttonUno = (Button) findViewById(R.id.buttonUno);
        buttonDos = (Button) findViewById(R.id.buttonDos);
        buttonTres = (Button) findViewById(R.id.buttonTres);
        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);
        // the timer
        times = (TextView) findViewById(R.id.timers);
        // method which will set the things up for our game
        setQuestionView();
        times.setText("00:50:00");
        // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
        CounterClass timer = new CounterClass(60000, 1000);
        timer.start();
        // button click listeners
        buttonUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passing the button text to other method
                // to check whether the anser is correct or not
                // same for all three buttons
                getAnswer(buttonUno.getText().toString());
            }
        });
        buttonDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(buttonDos.getText().toString());
            }
        });
        buttonTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(buttonTres.getText().toString());
            }
        });
    }
    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {
            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            scored.setText("Score : " + score);
        } else {
            // if unlucky start activity and finish the game
            Intent intent = new Intent(QuestionUnoActivity.this,
                   ResultActivity.class);

            // passing the int value
           Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();

        }
        if (qid <3) {// terminar en la pregunta
            currentQ = quesList.get(qid);
            setQuestionView();
        } else {
            // if over do this
            Intent intent = new Intent(QuestionUnoActivity.this,
                    ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // El resultado es
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {
            times.setText("Time is up");
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);
        }
    }
    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        buttonUno.setText(currentQ.getOPTA());
        buttonDos.setText(currentQ.getOPTB());
        buttonTres.setText(currentQ.getOPTC());
        qid++;
    }
}
