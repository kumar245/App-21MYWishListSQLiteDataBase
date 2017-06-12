package com.kumar.user.app_21mywishlistsqlitedatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

public class DisplayWishes extends AppCompatActivity {
    private DatabaseHandler dba;
    private ArrayList<MyWish> dbWishes=new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wishes);
        listView= (ListView) findViewById(R.id.list);
        refreshData();
    }

    private void refreshData() {
        dbWishes.clear();
        dba=new DatabaseHandler(getApplicationContext());
        ArrayList<MyWish> wishesFromDB= dba.getWishes();
        for (int i=0;i<wishesFromDB.size();i++){
            String title=wishesFromDB.get(i).getTitle();
            String dateText=wishesFromDB.get(i).getRecordDate();
            String content=wishesFromDB.get(i).getContent();
            int mId=wishesFromDB.get(i).getItemId();

            MyWish myWish=new MyWish();
            myWish.setTitle(title);
            myWish.setContent(content);
            myWish.setRecordDate(dateText);
            myWish.setItemId(mId);

            dbWishes.add(myWish);
        }
        dba.close();
        //Set Up Adapter
        wishAdapter=new WishAdapter(DisplayWishes.this,R.layout.wish_row,dbWishes);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();
    }
    public class WishAdapter extends ArrayAdapter<MyWish>{

        Activity activity;
        int layoutResource;
        MyWish myWish;
        ArrayList<MyWish> mData=new ArrayList<>();

        public WishAdapter(@NonNull Activity act, @LayoutRes int resource, @NonNull ArrayList<MyWish> data) {
            super(act, resource, data);
            activity=act;
            layoutResource=resource;
            mData=data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public MyWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(@Nullable MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            ViewHolder holder=null;
            if (row==null || (row.getTag())==null){
                LayoutInflater inflater=LayoutInflater.from(activity);
                row=inflater.inflate(layoutResource,null);
                holder=new ViewHolder();
                holder.mTitle= (TextView) row.findViewById(R.id.name);
                holder.mDate= (TextView) row.findViewById(R.id.dateText);
                row.setTag(holder);
            }
            else {
                holder= (ViewHolder) row.getTag();
            }
            holder.myWish=getItem(position);
            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getRecordDate());
            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text= finalHolder.myWish.getContent().toString();
                    String dateText=finalHolder.myWish.getRecordDate().toString();
                    String title=finalHolder.myWish.getTitle().toString();
                    int mId=finalHolder.myWish.getItemId();

                    Intent intent=new Intent(DisplayWishes.this,Wish_Detail.class);
                    intent.putExtra("content",text);
                    intent.putExtra("date",dateText);
                    intent.putExtra("title",title);
                    intent.putExtra("id",mId);
                    startActivity(intent);




                }
            });

            return row;
        }
    }
    class ViewHolder{
        MyWish myWish;
        TextView mTitle;
        TextView mId;
        TextView mContent;
        TextView mDate;
    }
}
