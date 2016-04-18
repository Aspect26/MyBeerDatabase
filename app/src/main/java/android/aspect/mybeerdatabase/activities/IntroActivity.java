package android.aspect.mybeerdatabase.activities;

import android.aspect.mybeerdatabase.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Calendar;

/**
 * Created by Aspect on 4/18/2016.
 */
public class IntroActivity extends AppCompatActivity {
    private ImageView image;

    private final int DISPLAY_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        image = ((ImageView)findViewById(R.id.intro_image));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(IntroActivity.this, MainMenuActivity.class);
                IntroActivity.this.startActivity(mainIntent);
                IntroActivity.this.finish();
            }
        }, DISPLAY_TIME);

        // ANIMATION
        int phases = 3;   // set number of inflaations with this variable

        // don't change anything below
        AnimationSet animation = new AnimationSet(true);
        float fromScale = 1.0f;
        float toScale = 1.1f;
        int animationDuration = DISPLAY_TIME / (phases * 2);

        for(int currentPhase=0; currentPhase<phases; currentPhase++){
            Animation inflate = new ScaleAnimation(fromScale, toScale, fromScale, toScale, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            inflate.setDuration(animationDuration);
            inflate.setStartOffset( animationDuration * (2*currentPhase) );
            animation.addAnimation(inflate);

            Animation deflate = new ScaleAnimation(toScale, fromScale, toScale, fromScale, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            deflate.setDuration(animationDuration);
            deflate.setStartOffset( animationDuration * (2*currentPhase)+animationDuration );
            animation.addAnimation(deflate);
        }

        // Launch animation
        image.startAnimation(animation);
    }
}
