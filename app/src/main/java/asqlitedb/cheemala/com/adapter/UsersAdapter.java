package asqlitedb.cheemala.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import asqlitedb.cheemala.com.R;
import asqlitedb.cheemala.com.model.User;
import asqlitedb.cheemala.com.utils.DatabaseCallbacks;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyHolder> {

    private Context context;
    private List<User> usrList;
    private DatabaseCallbacks databaseCallbacks;

    public UsersAdapter(Context context, List<User> usrList, DatabaseCallbacks databaseCallbacks){
        this.context = context;
        this.usrList = usrList;
        this.databaseCallbacks = databaseCallbacks;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View usrLyout = LayoutInflater.from(context).inflate(R.layout.usr_lst_item,parent,false);
        return new MyHolder(usrLyout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.getUsrNameTxt().setText(usrList.get(position).getUserName());
        holder.getDeleteBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tst_del_db_pos_"+usrList.get(position).getId(),"tst_del_lst_pos_"+position);
                databaseCallbacks.deleteRecord(usrList.get(position).getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usrList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView usrNameTxt;
        ImageButton editBtn,deleteBtn;

        public MyHolder(View itemView) {
            super(itemView);
            usrNameTxt = itemView.findViewById(R.id.usr_name_txt);
            editBtn = itemView.findViewById(R.id.edit_btn);
            deleteBtn = itemView.findViewById(R.id.dlt_btn);
        }

        public TextView getUsrNameTxt() {
            return usrNameTxt;
        }

        public void setUsrNameTxt(TextView usrNameTxt) {
            this.usrNameTxt = usrNameTxt;
        }

        public ImageButton getEditBtn() {
            return editBtn;
        }

        public void setEditBtn(ImageButton editBtn) {
            this.editBtn = editBtn;
        }

        public ImageButton getDeleteBtn() {
            return deleteBtn;
        }

        public void setDeleteBtn(ImageButton deleteBtn) {
            this.deleteBtn = deleteBtn;
        }

    }

    public void userDeleted(int postion){
        usrList.remove(postion);
        notifyDataSetChanged();
    }

    public void addUser(User newUser){
        usrList.add(newUser);
        notifyDataSetChanged();
    }
}
