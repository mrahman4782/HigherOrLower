package com.example.finalproject;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    public Data A;
    public Data B;
    public PlayGame game;
    public Boolean gameEnd;
    public String type;
    public String beforeData = "";
    public String afterData = "";

    public String globalScore = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent i = getIntent();
        String passed = i.getExtras().getString("buttonId");
        TextView name = (TextView) findViewById(R.id.mode);
        TextView verb1 = (TextView) findViewById(R.id.verb1);
        TextView verb2 = (TextView) findViewById(R.id.verb2);

        type = passed;
        switch (passed) {
            case "Networth":
                name.setText("Net Worth");
                verb1.setText("has");
                verb2.setText("has");
                break;
            case "Youtube":
                name.setText("Subscribers");
                verb1.setText("has");
                verb2.setText("has");
                break;
            case "Grocery":
                name.setText("Grocery Prices");
                verb1.setText("costs");
                verb2.setText("costs");
                break;
            case "Heights":
                name.setText("Celeb Heights");
                verb1.setText("is");
                verb2.setText("is");
                break;
        }

        startGame(passed);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag, new instructionFragment());
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }
    public void startGame(String mode){
        FileProcess a = new FileProcess(mode,getApplicationContext());
        Map<Integer, Data> temp = a.getFiltered();
        if(mode.equals("Networth") || mode.equals("Grocery")){
            beforeData = "$";
        }
        if(mode.equals("Youtube")){
            afterData = " subs";
        }
        if(mode.equals("Heights")){
            afterData = "in";
        }
        if(temp.size()!=0){
            game = new PlayGame(temp);
            A = game.getASingleStartingData();
            B = game.getComparingData(A);
            gameEnd = false;
            setData();
        }

    }
    public void processUserChoice(Boolean result){
        if(result == false){

            System.out.println("Failed");
            A = new Data("",0.0,"");
            B = new Data("",0.0,"");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag, new endFragment(globalScore));
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);

        }else{
            A = B;
            B = game.getComparingData(A);

        }
        System.out.println("new Process");
        setData();
    }
    public void answerSelection(View view) {
        switch (view.getId()) {
            case R.id.moreButton:
                System.out.println("More");
                processUserChoice(game.compare("G",A,B));
                break;
            case R.id.lessButton:
                System.out.println("Less");
                processUserChoice(game.compare("L",A,B));
                break;
        }
    }

    public void setData(){
        if(A.getName()!="" && B.getName() !="") {
            TextView scoreView = (TextView) findViewById(R.id.score);
            scoreView.setText("Score: " + game.getScore().toString());
            globalScore = game.getScore().toString();

            TextView name1 = (TextView) findViewById(R.id.name1);
            name1.setText(A.getName());
            TextView val1 = (TextView) findViewById(R.id.value1);


            System.out.println("|||||||||||");
            System.out.println(A.getData());
            System.out.println(game.format(A.getData()));
            System.out.println(B.getData());
            System.out.println("|||}||||||");
            val1.setText(beforeData+ game.format(A.getData())+afterData);

            String imageUri = A.getUrl();
            ImageView imgageOfA = (ImageView) findViewById(R.id.img1);
            Picasso.get().load(imageUri).resize(400, 400).centerCrop().into(imgageOfA);

            String imageUriB = B.getUrl();
            ImageView imgageOfB = (ImageView) findViewById(R.id.img2);
            Picasso.get().load(imageUriB).resize(400, 400).centerCrop().into(imgageOfB);
            TextView name2 = (TextView) findViewById(R.id.name2);
            name2.setText(B.getName());
        }

    }

    public void closeOk(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentManager.popBackStack();
    }

    public void closeExit(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        Intent intent = new Intent(this, ModeActivity.class);
        startActivity(intent);

    }


}