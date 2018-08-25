package asqlitedb.cheemala.com.activity;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

import asqlitedb.cheemala.com.R;
import asqlitedb.cheemala.com.adapter.UsersAdapter;
import asqlitedb.cheemala.com.database.AppDb;
import asqlitedb.cheemala.com.model.User;
import asqlitedb.cheemala.com.utils.Constants;
import asqlitedb.cheemala.com.utils.DatabaseCallbacks;
import asqlitedb.cheemala.com.utils.ShowMsg;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText adUsrNameEdt;
    private Button addUsrBtn;
    private AppDb appDb;
    private List<User> userList;
    private UsersAdapter usersAdapter;
    private DatabaseCallbacks databaseCallbacks;
    private RecyclerView usrLstRcyrVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Need to initialise the views first
        initialiseViews();
        showAddedUsers();
    }

    private void initialiseViews() {
        //Get database instance
        appDb = new AppDb(Home.this);
        //Create instance for users data
        userList = new ArrayList<>();
        //Create instance for toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Create instance for recycler view
        usrLstRcyrVw = (RecyclerView) findViewById(R.id.usrs_list_rcyclrVw);
        adUsrNameEdt = toolbar.findViewById(R.id.ad_itm_edt);
        addUsrBtn = toolbar.findViewById(R.id.add_usr_btn);
        addUsrBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_usr_btn:
                addUser();
                break;
        }
    }

    private void addUser() {

        String usrNameEdtVal = adUsrNameEdt.getText().toString();

        if (usrNameEdtVal.contentEquals("")){
            ShowMsg.showMsg(Home.this,"Add user name please!");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.getDbUserName(),usrNameEdtVal);

        if (appDb.addUser(contentValues) != -1){
            ShowMsg.showMsg(Home.this,"User added successfully!");

            userList = appDb.fetchAllDta();
            if (usersAdapter == null){
                populateUsrDtaIntoRcyrVw(userList);
                adUsrNameEdt.setText("");
            }else {
                User user = userList.get(userList.size()-1);
                usersAdapter.addUser(user);
                adUsrNameEdt.setText("");
            }
        }

    }

    private void showAddedUsers() {
        userList = appDb.fetchAllDta();
        populateUsrDtaIntoRcyrVw(userList);
    }

    private void populateUsrDtaIntoRcyrVw(List<User> userList) {
        if (userList != null && userList.size() > 0){
            usersAdapter = new UsersAdapter(Home.this, userList, new DatabaseCallbacks() {
                @Override
                public void deleteRecord(String dBpostion,int listPosition) {
                    deleteUser(dBpostion,listPosition);
                }

                @Override
                public void editRecord(String position) {

                }
            });
            usrLstRcyrVw.setLayoutManager(new LinearLayoutManager(this));
            usrLstRcyrVw.setHasFixedSize(true);
            usrLstRcyrVw.setAdapter(usersAdapter);
        }
    }

    private void deleteUser(String postion, int listPosition) {
        if (appDb != null){
            if (appDb.deleteUser(postion) != 0){
                ShowMsg.showMsg(Home.this,"Deletion success!");
                updateRecyclerVw(listPosition);
            }else {
                ShowMsg.showMsg(Home.this,"Something went wrong!");
            }
        }
    }

    private void updateRecyclerVw(int listPosition) {
        userList = appDb.fetchAllDta();
        usersAdapter.userDeleted(listPosition);
    }
}