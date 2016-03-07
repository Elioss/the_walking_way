package com.example.sanoyan.googlemaps.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Lionel on 16/02/2016.
 */
public class DBAdapter {

    DatabaseHelper DBHelper;
    Context context;
    SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public void Truncate() {
        db.execSQL("DELETE FROM " + DatabaseHelper.getTablePosition());
        db.execSQL("DELETE FROM " + DatabaseHelper.getTableWaypoint());
    }

    public void creerUnePosition(Position position) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.getKeyLongitude(), position.getLongitude());
        values.put(DatabaseHelper.getKeyLatitude(), position.getLatitude());

        db.insert(DatabaseHelper.getTablePosition(), null, values);
    }

    private Position cursorSurPosition(Cursor cursor) {
        Position position = new Position();
        position.setUtilisateur(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getKeyUtilisateur())));
        position.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLatitude())));
        position.setLongitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLongitude())));
        return position;
    }

    public ArrayList<Position> recupererTouteLesPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.getTablePosition(), null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Position position = cursorSurPosition(cursor);
            positions.add(position);
            cursor.moveToNext();
        }
        cursor.close();
        return positions;
    }

    public Position recupererDernierePosition() {
        Position position = new Position();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.getTablePosition(), null);

        cursor.moveToLast();
        position.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLatitude())));
        position.setLongitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLongitude())));

        return position;
    }


    public void CreerUnWaypoint(Waypoint waypoint) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.getKeyLongitude(), waypoint.getLongitude());
        values.put(DatabaseHelper.getKeyLatitude(), waypoint.getLatitude());

        db.insert(DatabaseHelper.getTablePosition(), null, values);
    }

    private Waypoint cursorSurWaypoint(Cursor cursor) {
        Waypoint waypoint = new Waypoint();
        waypoint.setUtilisateur(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getKeyUtilisateur())));
        waypoint.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLatitude())));
        waypoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLongitude())));
        return waypoint;
    }

    public Waypoint recupererDernierWaypoint() {
        Waypoint waypoint = new Waypoint();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.getTableWaypoint(), null);

        cursor.moveToLast();
        waypoint.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLatitude())));
        waypoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getKeyLongitude())));

        return  waypoint;
    }

    public ArrayList<Waypoint> recupererTouteLesWaypoints() {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.getTableWaypoint(), null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Waypoint waypoint = cursorSurWaypoint(cursor);
            waypoints.add(waypoint);
            cursor.moveToNext();
        }
        cursor.close();
        return waypoints;
    }
}
