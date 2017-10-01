package one2play.mobot.itunesapi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import one2play.mobot.itunesapi.Model.MusicModel;
import one2play.mobot.itunesapi.Service.MusicList;

/**
 * Created by fastroni on 01/10/2017.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MusicsDB";
    private static final String TABLE_NAME = "Musics";
    private static final String KEY_ID = "id";
    private static final String KEY_MUSICNAME= "name_music";
    private static final String KEY_ARTISTNAME = "name_artist";
    private static final String KEY_PHOTO = "photo";
    private static final String[] COLUMNS = {KEY_ID, KEY_MUSICNAME, KEY_ARTISTNAME, KEY_PHOTO};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Musics ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name_music TEXT, "
                + "name_artist TEXT, "
                + "photo TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<MusicModel> allMusics(){

        List<MusicModel> musics = new LinkedList<MusicModel>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MusicModel music = null;

        if(cursor.moveToFirst()){
            do{
                music = new MusicModel();
                music.setArtistId(Integer.parseInt(cursor.getString(0)));
                music.setTrackName(cursor.getString(1));
                music.setArtistName(cursor.getString(2));
                music.setArtworkUrl100(cursor.getString(3));
                musics.add(music);
            }while(cursor.moveToNext());
        }

        return musics;
    }

    public void addMusic(MusicModel music) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MUSICNAME, music.getTrackName());
        values.put(KEY_ARTISTNAME, music.getArtistName());
        values.put(KEY_PHOTO, music.getArtworkUrl100());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
}
