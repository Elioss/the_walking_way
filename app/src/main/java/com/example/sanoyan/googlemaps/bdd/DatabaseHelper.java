package com.example.sanoyan.googlemaps.bdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lionel on 09/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;

    //version de la base de données
    private static final int DATABASE_VERSION = 1;
    //nom de la base de données
    private static final String DATABASE_NAME = "dbtww";

    //les différentes tables
    private static final String TABLE_POSITION = "position";
    private static final String TABLE_WAYPOINT = "waypoint";

    //nom des colonnes communes
    private static final String KEY_ID = "id";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";

    //nom des colonnes de table position
    private static final String KEY_UTILISATEUR = "utilisateur";

    //nom des colonnes de la table waypoint
    private static final String KEY_WAYPOINT = "waypoint";

    /////////////////
    // GETTERS
    /////////////////


    public static String getTablePosition() {
        return TABLE_POSITION;
    }

    public static String getTableWaypoint() {
        return TABLE_WAYPOINT;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyLongitude() {
        return KEY_LONGITUDE;
    }

    public static String getKeyLatitude() {
        return KEY_LATITUDE;
    }

    public static String getKeyUtilisateur() {
        return KEY_UTILISATEUR;
    }

    public static String getKeyWaypoint() {
        return KEY_WAYPOINT;
    }

    //////////////////////////////////////////////////////////
    //  Création des requetes pour la création des tables
    /////////////////////////////////////////////////////////

    //Création de la table position
    private static final String CREATE_TABLE_POSITION =
            "CREATE TABLE " + TABLE_POSITION + "("
            + KEY_ID + "REAL,"
            + KEY_UTILISATEUR + "TEXT,"
            + KEY_LONGITUDE + "REAL"
            + KEY_LATITUDE + "REAL" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
