package com.bermet.dictionary;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    public static final String DATABASE_NAME = "my_dic_test.db";
    public static int DATABASE_VERSION = 1;
    private String DATABASE_LOCATION = " ";
    private String DATABASE_FULL_PATH = " ";

    private final String TBL_ENG_RUS = "eng_rus ";
  //  private final String TBL_RUS_RUS = "rus_rus ";
    private final String TBL_RUS_ENG = "rus_eng ";
    private final String TBL_BOOKMARK = "bookmark";

    private final String COL_KEY = "key";
    private final String COL_VALUE = "value";
    private final String COL_VALUE_UPPER = "VALUE";


    public SQLiteDatabase mDB;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DATABASE_LOCATION = "data/data/" + mContext.getPackageName() + "/database/";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;
        if (!isExistingDB()) {
            File dbLocation = new File(DATABASE_LOCATION);
            dbLocation.mkdirs();
            try {
                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
                // e.fillInStackTrace();

            }


        }
        Log.i("DBHelper", "DBHelper: " + DATABASE_FULL_PATH);
        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);

    }


    boolean isExistingDB() {
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();


    }

    public void extractAssetToDatabaseDirectory(String fileName)
            throws IOException {
        int length;
        InputStream sourceDatabase = this.mContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);
        byte[] buffer = new byte[4046];
        while ((length = sourceDatabase.read(buffer)) > 0) {
            destination.write(buffer, 0, length);

        }
        sourceDatabase.close();
        destination.flush();
        destination.close();

        {


        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public ArrayList<String> getWord(int dicType) {
        String tableName = getTableName(dicType);

        String q = "SELECT * FROM " + tableName;
        Cursor result = mDB.rawQuery(q, null);
        ArrayList<String> source = new ArrayList<>();

        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        result.close();
        return source;

    }


    public Word getWord(String key, int dicType) {
        String tableName = getTableName(dicType);

        String q = "SELECT * FROM " + tableName + " WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q, new String[]{key});
        Word word = new Word();


        while (result.moveToNext()) {
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            if (!tableName.equals(TBL_RUS_ENG))
                word.value = result.getString(result.getColumnIndex(COL_VALUE));
            else
                word.value = result.getString(result.getColumnIndex(COL_VALUE_UPPER));
        }
        result.close();
        return word;

    }

    /**
     * Добавляет заголовки в базу
     * @param word ыводаоывд
     */
    public void addBookmark(Word word) {
        try {
            String q = "INSERT INTO bookmark([" + COL_KEY + "],[" + COL_VALUE + "]) VALUES(?, ?);";
            mDB.execSQL(q, new Object[]{word.key, word.value});


        } catch (SQLException ex) {

        }

    }

    /**
     *  Удаляет заголовки
     * @param word -
     */
    public void removeBookmark(Word word) {
        try {
            String q = "DELETE FROM bookmark WHERE upper([" + COL_KEY + "])=upper(?) AND [" + COL_VALUE + "] = '?';";
            mDB.execSQL(q, new Object[]{word.key, word.value});


        } catch (SQLException ex) {

        }

    }

    public ArrayList<String> getALLWordFromBookmark(String key) {

        String q = "SELECT * FROM bookmark ORDER BY[date] DESC;";
        Cursor result = mDB.rawQuery(q, new String[]{key});
        ArrayList<String> source = new ArrayList<>();


        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;

    }

    public boolean isWordMark(Word word) {
        String q = "SELECT * FROM bookmark WHERE upper([key])=upper(?) AND [value] = ?";
        Cursor result = mDB.rawQuery(q, new String[]{word.key, word.value});
        result.close();
        return result.getCount() > 0;


    }

    public Word getWordFromBookmark(String key) {
        String q = "SELECT * FROM bookmark WHERE upper([key])=upper(?)";
        // Cursor result = mDB.rawQuery(q, new String[]{key});
        Cursor result = mDB.rawQuery(q, null);
        //  Word word = null;
        Word word = new Word();
        while (result.moveToNext()) {
            //  word = new Word();
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));

        }
        result.close();
        return word;

    }

    public String getTableName(int dicType) {
        String tableName = " ";
        if (dicType == R.id.action_rus_eng) {
            tableName = TBL_RUS_ENG;
      //  } else if (dicType == R.id.action_rus_rus) {
      //      tableName = TBL_RUS_RUS;

        } else if (dicType == R.id.action_eng_rus) {
            tableName = TBL_ENG_RUS;


        }
        return tableName;
    }
}
