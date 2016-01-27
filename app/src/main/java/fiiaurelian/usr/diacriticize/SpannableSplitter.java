package fiiaurelian.usr.diacriticize;


import android.content.Context;
import android.text.SpannableString;

public class SpannableSplitter {

    public static SpannableString generateSpannableString( String text, Context context ) {
        System.out.println("SpannableSplitter.generateSpannableString");
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.openDatabase();
        SpannableString spannableString = new SpannableString( text );

        int start = 0;
        int end = 0;
        for(; end < text.length(); ++end) {
            if( Character.isLetter( text.charAt( end ) ) == false ) {
                if(start < end) {
                    String word = text.substring(start, end);
                    if(dbHelper.getAlternativeForWord(word).size() > 0 ) {
                        spannableString.setSpan(new WordClickable(text, start, end, context),
                                start,
                                end,
                                0);
                    }
                }
                start = end + 1;
            }
        }
        if(start < end) {
            String word = text.substring(start, end);
            if(dbHelper.getAlternativeForWord(word).size() > 0) {
                spannableString.setSpan(new WordClickable(text, start, end, context),
                        start,
                        end,
                        0);
            }
        }

        return spannableString;
    }
}
