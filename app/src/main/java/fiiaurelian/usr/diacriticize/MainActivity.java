package fiiaurelian.usr.diacriticize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createDatabase();
        dbHelper.openDatabase();

        FrameLayout frameLayout = ( FrameLayout ) findViewById( R.id.text_container );
        EditText inputText = new EditText( this );
        inputText.setId(R.id.input_text);
        inputText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        frameLayout.addView( inputText );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void addAccents(View btn) {
        System.out.println("MainActivity.addAccents");
        EditText inputText = (EditText) findViewById(R.id.input_text);
        String text = inputText.getText().toString();
        FrameLayout frameLayout = ( FrameLayout ) findViewById( R.id.text_container );
        frameLayout.removeAllViews();

        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        frameLayout.addView(textView);
        makeWordClickable(textView, text);
    }

    public void makeWordClickable(TextView textView, String text) {
        System.out.println("MainActivity.makeWordClickable");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(SpannableSplitter.generateSpannableString(text, this), EditText.BufferType.SPANNABLE);
        textView.setClickable(true);
    }
}
