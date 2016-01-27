package fiiaurelian.usr.diacriticize;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WordClickable extends ClickableSpan {
    private Context context;
    private String text;
    private int start;
    private int end;

    public WordClickable( String text ,int start, int end, Context context ) {
        this.text = text;
        this.start = start;
        this.end = end;
        this.context = context;
    }

    @Override
    public void onClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.openDatabase();
        List<String> wordList = dbHelper.getAlternativeForWord( text.substring( start, end ) );
        final CharSequence[] alternatives = wordList.toArray(new CharSequence[wordList.size()]);
        builder.setSingleChoiceItems(alternatives, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changedText = "";
                if (start > 0)
                    changedText = changedText + text.substring(0, start);
                changedText = changedText + alternatives[which] + text.substring(end);
                MainActivity activity = (MainActivity) context;
                activity.makeWordClickable((TextView) view, changedText); // changes needed here!

                // added such that the dialog will close once the user has selected a choice
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
