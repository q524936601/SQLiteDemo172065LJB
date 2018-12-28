package com.example.stu.sqlitedemo172065ljb;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtTxtName,edtTxtPhone;
    TextView tvDisplay;
    ListView lvDisplay;
    List<Infor>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTxtName=findViewById(R.id.edtTxt_main_name);
        edtTxtPhone=findViewById(R.id.edtTxt_main_phone);
        lvDisplay=findViewById(R.id.lv_main_display);


        findViewById(R.id.btn_main_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加
                //点击按钮，调用适配器的insert();对象,方法();
                String name=edtTxtName.getText().toString().trim();
               Long phone=Long.valueOf(edtTxtPhone.getText().toString().trim());
                Infor infor=new Infor();
                infor.setName(name);
                infor.setPhone(phone);
                MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
                adapter.insert(infor);
            }
        });
        findViewById(R.id.btn_main_query).setOnClickListener(new View.OnClickListener() {//查询
            @Override
            public void onClick(View v) {
                MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
               list=adapter.queryAll();
               lvDisplay.setAdapter(new MyAdpater());
                //list
               /* Iterator<Infor>iterator=list.iterator();
                tvDisplay.setText(null);
                while (iterator.hasNext()){
                    Infor infor=iterator.next();
                    tvDisplay.append("ID:"+String.valueOf(infor.get_id())+"   ");
                    tvDisplay.append("name:"+infor.getName()+"   ");
                    tvDisplay.append("phone:"+String.valueOf(infor.getPhone())+"\n");
                }
                */

            }
        });



        findViewById(R.id.btn_main_delete).setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {
                //根据姓名，删除数据库表里的记录，1，2,3，。。。
                String name=edtTxtName.getText().toString().trim();
                MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
                int num= 0;
                num=adapter.deleteByName(name);

                Toast.makeText(getApplicationContext(),"删除记录条数："+String.valueOf(num),
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_main_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Infor infor=new Infor();
                String name =edtTxtName.getText().toString().trim();
                infor.setName(name);
                Long phone=Long.valueOf(edtTxtPhone.getText().toString().trim());
                infor.setPhone(phone);
                MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
                int num=adapter.update(infor);
                tvDisplay.setText("更新记录条数"+String.valueOf(num));
            }
        });


        MyHelper myHelper=new MyHelper(getApplicationContext(),"database.db",null,1);
        SQLiteDatabase db=myHelper.getWritableDatabase();
    }

    //内部类
    class  MyAdpater extends BaseAdapter{//抽象
        //实现4个方法，前三个作为处理数据，最后一个是处理视图
        @Override
        public int getCount() {
            return list.size();//记录数量
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);//数据对象个体
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(getApplicationContext(),R.layout.list_item,null);//每行的格式布局文件
            TextView tvID=view.findViewById(R.id.tv_listitem_id);
            TextView tvName=view.findViewById(R.id.tv_listitem_name);
            TextView tvPhone=view.findViewById(R.id.tv_listitem_phone);

            Infor infor=(Infor) getItem(position);
            tvID.setText(String.valueOf(infor.get_id()));
            tvName.setText(infor.getName());
            tvPhone.setText(String.valueOf(infor.getPhone()));

            return view;
        }
    }
}
