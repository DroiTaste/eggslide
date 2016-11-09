package app_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;


public class SQLiteHandler extends SQLiteOpenHelper {

    public static AppConfig db_name = new AppConfig();//appconfig에 있는 데이터베이스명 가져오기


    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = db_name.getDATABASE_NAME();



    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String KEY_UID = "uid";
    private static final String KEY_FB_ID = "fb_id";
    private static final String KEY_KT_ID = "kt_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NICK_NAME = "nick_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_PROFILE_IMG = "profile_img";
    private static final String KEY_CREATED_AT = "created_at";


    private static final String KEY_POPUP = "popup";
    private static final String KEY_ID = "key_id";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_UID + " TEXT PRIMARY KEY,"
                + KEY_FB_ID + " TEXT,"
                + KEY_KT_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_NICK_NAME + " TEXT,"
                + KEY_PHONE_NUMBER + " TEXT,"
                + KEY_PROFILE_IMG + " TEXT,"
                + KEY_CREATED_AT + " TEXT)";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create tables again
        onCreate(db);
    }

    /**
     * DB 정보 수정
     */
    public void updateUser(String email, String popup){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POPUP,popup);

        db.update(TABLE_LOGIN,values,"email = '"+email+"'", null);
        db.execSQL("UPDATE "+TABLE_LOGIN+" SET popup = '"+popup+"' WHERE email = '"+email+"'");
        this.close();
    }

    //EmailLogin 사용함!!(수정함)
    /**
     * DB에 유저 정보 저장
     * */
    public void addUser( String uid, String fb_id, String kt_id, String name
                            , String gender, String email, String nick_name
                            , String phone_number, String profile_img, String created_at) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid);
        values.put(KEY_FB_ID, fb_id);
        values.put(KEY_KT_ID, kt_id);
        values.put(KEY_NAME, name);
        values.put(KEY_GENDER, gender);
        values.put(KEY_EMAIL, email);
        values.put(KEY_NICK_NAME, nick_name);
        values.put(KEY_PHONE_NUMBER, phone_number);
        values.put(KEY_PROFILE_IMG, profile_img);
        values.put(KEY_CREATED_AT, created_at);

        // Inserting Row
        long id = db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * 유저의 정보를 받아오기
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("fb_id", cursor.getString(0));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("name", cursor.getString(4));
            user.put("popup", cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        //Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();

        //Log.d(TAG, "Deleted all user info from sqlite");
    }

}