package Config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by docente05 on 20/04/2016.
 */
public class SQLite extends SQLiteOpenHelper {

    private static String NAME_DATABASE = "dbprueba";
    private static int VERSION=1;

    private String TABLE_USER ="CREATE TABLE facultad " +
            "(id_facultad INTEGER PRIMARY KEY AUTOINCREMENT , " +
            " facultad TEXT, " +
            " abv TEXT)";

    public SQLite(Context c) {
        super(c, NAME_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS facultad");
        this.onCreate(db);
    }
}
