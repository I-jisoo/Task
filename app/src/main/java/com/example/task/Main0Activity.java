package com.example.task;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Main0Activity extends AppCompatActivity {

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        this.setTitle("注 册");

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
    }

    //点击取消按钮返回登录页面
    public void backClicked(View view) {
        finish();
        Intent intent = new Intent(Main0Activity.this, MainActivity.class);
        startActivity(intent);
    }

    public void logon(View view){
        EditText editText3=(EditText)findViewById(R.id.editText1);
        EditText editText4=(EditText)findViewById(R.id.editText2);
        EditText editText5=(EditText)findViewById(R.id.editText5);
        String newname =editText3.getText().toString().trim();
        String password =editText4.getText().toString().trim();
        String password1 =editText5.getText().toString().trim();
        if (CheckIsDataAlreadyInDBorNot(newname)) {
            Toast.makeText(this,"该账号已被注册，请重新输入",Toast.LENGTH_SHORT).show();
        }
        else if(password.equals(password1)){
            if (register(newname, password)) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent0 = new Intent(Main0Activity.this,MainActivity.class);
                startActivity(intent0);
            }
        }else {
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
        }
    }
    //向数据库插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        ((SQLiteDatabase) db).insert("userData",null,values);
        db.close();
        return true;
    }
    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

}
