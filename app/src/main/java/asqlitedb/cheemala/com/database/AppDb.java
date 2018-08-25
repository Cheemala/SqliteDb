package asqlitedb.cheemala.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import asqlitedb.cheemala.com.model.User;
import asqlitedb.cheemala.com.utils.Constants;
import asqlitedb.cheemala.com.utils.ShowMsg;

public class AppDb extends SQLiteOpenHelper {

    private String TAG = "App_Db_";
    private Context context;
    private String CREATE_TABLE_COMMAND = "CREATE TABLE "+Constants.getDbTableName()+"("+Constants.getDbIdName()+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Constants.getDbUserName()+" TEXT);";
    private String DROP_TABLE_COMMAND = "DROP TABLE IF EXISTS "+Constants.getDbTableName();
    private String FETCH_ALL_USERS_COMMAND = "SELECT * FROM "+Constants.getDbTableName();

    public AppDb(Context context) {
        super(context, Constants.getDbName(), null, Constants.getDbVersion());
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMMAND);
        Log.d("table_","created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_COMMAND);
        onCreate(db);
    }

    public long addUser(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(Constants.getDbTableName(),null,contentValues);
    }

    public List<User> fetchAllDta(){
        List<User> allUsers = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor usersCursor = db.rawQuery(FETCH_ALL_USERS_COMMAND,null);
        if (usersCursor != null && usersCursor.getCount() > 0){
            allUsers = new ArrayList<>();
            usersCursor.moveToFirst();
           do{
               ShowMsg.showMsg(context,String.valueOf(usersCursor.getString(usersCursor.getColumnIndexOrThrow(Constants.getDbIdName()))));
               allUsers.add(new User(String.valueOf(usersCursor.getString(usersCursor.getColumnIndexOrThrow(Constants.getDbIdName()))),usersCursor.getString(usersCursor.getColumnIndexOrThrow(Constants.getDbUserName()))));
           }while (usersCursor.moveToNext());
           return allUsers;
        }
        return null;
    }

    public long deleteUser(String position){
        SQLiteDatabase db = this.getWritableDatabase();
        //Log.d("pos","_"+actlPos);
        return db.delete(Constants.getDbTableName(),Constants.getDbIdName()+ "=" +position,null);
    }
}
