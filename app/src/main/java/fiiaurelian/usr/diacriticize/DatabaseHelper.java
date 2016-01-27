package fiiaurelian.usr.diacriticize;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_PATH    = "/data/data/fiiaurelian.usr.diacriticize/databases/";
    public static final String DB_NAME    = "words.sqlite";
    public static final String WORD_TABLE = "words";
    public static final String DIACRITICS = "diacritics";
    public static final String SIMPLE     = "simple";
    public static final int    DB_VERSION = 2;

    private SQLiteDatabase db;
    private Context        context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close(){
        if(db != null){
            db.close();
        }
        super.close();
    }

    public void openDatabase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void copyDatabase() {
        try {
            Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.words);

            String outputFileName = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = inputStream.read(buffer))>0){
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() {
        if (checkDatabase() == false) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;
        String dbPath = DB_PATH + DB_NAME;
        try {
            tempDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (tempDB != null)
            tempDB.close();
        return (tempDB != null);
    }

    public List<String> getAlternativeForWord(String word) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            cursor = db.query(WORD_TABLE, new String[] {DIACRITICS}, SIMPLE + "=?", new String[] {word}, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                while(cursor.isAfterLast() == false) {
                    result.add(cursor.getString(cursor.getColumnIndex(DIACRITICS)));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
