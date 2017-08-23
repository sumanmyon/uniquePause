package com.example.suman.uniquepausefinal.SettingAndAboutUs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suman.uniquepausefinal.R;

public class AboutUs extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageViewProfile1, imageViewProfile2, imageViewProfile3, imageViewProfile4;
    TextView textViewProfile1, textViewProfile2, textViewProfile3, textViewProfile4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO REMOVE TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TODO set activity for this class
        setContentView(R.layout.activity_about_us);

        toolbar = (Toolbar)findViewById(R.id.aboutUs_toolBar);

        imageViewProfile1 = (ImageView)findViewById(R.id.imageViewProfile1);
        imageViewProfile2 = (ImageView)findViewById(R.id.imageViewProfile2);
        imageViewProfile3 = (ImageView)findViewById(R.id.imageViewProfile3);
        imageViewProfile4 = (ImageView)findViewById(R.id.imageViewProfile4);

        textViewProfile1 = (TextView)findViewById(R.id.textViewProfile1);
        textViewProfile2 = (TextView)findViewById(R.id.textViewProfile2);
        textViewProfile3 = (TextView)findViewById(R.id.textViewProfile3);
        textViewProfile4 = (TextView)findViewById(R.id.textViewProfile4);

        //TODO setting and showing ToolBar
        toolbar.setTitle(R.string.aboutUs);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp);
        setSupportActionBar(toolbar);

        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.suman2);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.sonu);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.dinesh2);
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.arjunprd);

        imageViewProfile1.setImageBitmap(getCircleBitmap(bm1));
        imageViewProfile2.setImageBitmap(getCircleBitmap(bm2));
        imageViewProfile3.setImageBitmap(getCircleBitmap(bm3));
        imageViewProfile4.setImageBitmap(getCircleBitmap(bm4));

        textViewProfile1.setText("Suman Prasad Neupane"+"\n Backend Developer");
        textViewProfile2.setText("Sonu Thapa Kshetri"+"\n Backend Developer");
        textViewProfile3.setText("Dinesh Neupane"+"\n Backend Developer");
        textViewProfile4.setText("Arjun Prasad Sharma"+"\n Frontend Developer");
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}
