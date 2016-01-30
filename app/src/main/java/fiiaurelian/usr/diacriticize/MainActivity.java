package fiiaurelian.usr.diacriticize;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createDatabase();
        dbHelper.openDatabase();

        //ADDED IN XML
        //LinearLayout linearLayout = ( LinearLayout ) findViewById( R.id.text_container );
        //EditText inputText = new EditText( this );
        //inputText.setId(R.id.input_text);
        //inputText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
        //        TableRow.LayoutParams.MATCH_PARENT));
        //linearLayout.addView(inputText);

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        createFloatingActionButtons();

        /*
        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_diacritics);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close floating action menu
                floatingActionMenu.close(true);

                addAccents(view);
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void createFloatingActionButtons() {
        FloatingActionButton fabAddDiacritics = new FloatingActionButton(this);
        fabAddDiacritics.setImageResource(R.drawable.ic_action_add);
        fabAddDiacritics.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabAddDiacritics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close floating action menu
                floatingActionMenu.close(true);

                addAccents(view);
            }
        });

        FloatingActionButton fabCopyToClipboard = new FloatingActionButton(this);
        fabCopyToClipboard.setImageResource(R.drawable.ic_action_copy);
        fabCopyToClipboard.setButtonSize(FloatingActionButton.SIZE_MINI);

        floatingActionMenu.addMenuButton(fabCopyToClipboard);
        floatingActionMenu.addMenuButton(fabAddDiacritics);
    }

    public void addAccents(View btn) {
        System.out.println("MainActivity.addAccents");
        EditText inputText = (EditText) findViewById(R.id.input_text);
        String text = inputText.getText().toString();
        LinearLayout linearLayout = ( LinearLayout ) findViewById( R.id.text_container );
        linearLayout.removeAllViews();

        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        textView.setTextSize(18); //TODO: find the default size of the EditText text
        textView.setMaxHeight(inputText.getMaxHeight());
        textView.setScrollContainer(true);
        textView.setVerticalScrollBarEnabled(true);
        linearLayout.addView(textView);
        makeWordClickable(textView, text);
    }

    public void makeWordClickable(TextView textView, String text) {
        System.out.println("MainActivity.makeWordClickable");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(SpannableSplitter.generateSpannableString(text, this), EditText.BufferType.SPANNABLE);
        textView.setClickable(true);
    }
}
