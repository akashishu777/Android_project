package com.task.sony.tree;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper myDb;
    EditText name , lname , marks, up_id;
    Button button;
    Button buttons;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        name = (EditText)findViewById(R.id.name);
        lname = (EditText)findViewById(R.id.lname);
        marks = (EditText)findViewById(R.id.marks);
        button = (Button)findViewById(R.id.btn);
        buttons = (Button)findViewById(R.id.buttons);
        update = (Button)findViewById(R.id.update);
        up_id = (EditText)findViewById(R.id.up_id);
        delete = (Button)findViewById(R.id.del);
        addData();
        viewAll();
        updateData();
        DeleteData();
    }


    public void addData()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean test =  myDb.insertData(name.getText().toString(), lname.getText().toString(), marks.getText().toString());
                if (test == true)
                {
                    Toast.makeText(MainActivity.this , "Contact Created", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error creating contact" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll()
    {
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Cursor res = myDb.getAlldata();
                if (res.getCount()==0)
                {
                   showMessage("Error","No record Found");
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Id : "+ res.getString(0)+"\n");
                    buffer.append("Name : "+ res.getString(1)+"\n");
                    buffer.append("Last Name : "+ res.getString(2)+"\n");
                    buffer.append("Phone number : "+ res.getString(3)+"\n\n");
                }
                showMessage("Contacts",buffer.toString());
            }
        });
    }
    public void showMessage(String title , String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData()
    {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(up_id.getText().toString(),name.getText().toString(),lname.getText().toString(),marks.getText().toString());
                if(isUpdated==true)
                {
                    Toast.makeText(MainActivity.this , "Contact Updated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Contact not updated" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData()
    {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteData(up_id.getText().toString());
                if (deleteRows > 0)
                {
                    Toast.makeText(MainActivity.this , "Contact Deleted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Contact not deleted" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
