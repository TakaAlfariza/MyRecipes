package comtugas.bintangrestu.myrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeToActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_to);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int click = view.getId();
        if(click == R.id.btnStart){
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }
    }
}