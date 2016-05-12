package Controller;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Config.SQLite;

/**
 * Created by docente05 on 20/04/2016.
 * Esta clase es para mi CRUD
 */
public class ClsFacultad {
    SQLite cx;

    public ClsFacultad(Context context) {
        cx = new SQLite(context);
    }

    public void insert_facultad(JSONArray jsonArray) {
        try {
            cx.getWritableDatabase().execSQL("DELETE FROM facultad");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                cx.getWritableDatabase().execSQL("INSERT INTO facultad(facultad, abv) " +
                        " VALUES ('"+ jo.getString("facultad") + "','"+ jo.getString("abv") + "' )");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Cursor readFacultad(){
        return cx.getReadableDatabase().rawQuery("SELECT id_facultad as _id, facultad, abv FROM facultad",null);
    }
}
