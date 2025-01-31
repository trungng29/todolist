package com.example.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var itemList = ArrayList<String>()

    var fileHelper: FileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        itemList = fileHelper.readData(this@MainActivity)

        var arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)

        mainBinding.listView.adapter = arrayAdapter

        mainBinding.button.setOnClickListener {
            var itemName: String = mainBinding.editText.text.toString()
            itemList.add(itemName)
            mainBinding.editText.setText("")
            fileHelper.writeData(itemList, applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        mainBinding.listView.setOnItemClickListener { parent, view, position, id ->
            var alert = AlertDialog.Builder(this@MainActivity)
            alert.setTitle("Delete")
                .setMessage("Do you want to delete this shi cuh ?")
                .setCancelable(false)
                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    itemList.removeAt(position)
                    arrayAdapter.notifyDataSetChanged()
                    fileHelper.writeData(itemList, applicationContext)
                })

            alert.create().show()
        }

    }
}