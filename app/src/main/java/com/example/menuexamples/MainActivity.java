package com.example.menuexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_view_spinner);

        Spinner spinner = actionBar.getCustomView().findViewById(R.id.my_spinner);
        items = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            items.add("Item " + i);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);

        ListView listView = findViewById(R.id.list_vew);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setLongClickable(true);

        findViewById(R.id.btn_show_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.action_call) {
                            Log.v("TAG", "CALL action");
                        } else if (id == R.id.action_sms) {
                            Log.v("TAG", "SMS action");
                        } else if (id == R.id.action_email) {
                            Log.v("TAG", "EMAIL action");
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        findViewById(R.id.btn_show_alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                        .setMessage("Are you sure you want to quit?")
//                        .setIcon(R.drawable.ic_launcher_foreground)
//                        .setTitle("Demo app")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.v("TAG", "Save info");
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.v("TAG", "Don't save info");
//                            }
//                        })
//                        .setNeutralButton("Cancel", null)
//                        .create();
//
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();

                String[] items = {"Item 1", "Item 2", "Item 3"};

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Select an option")
                        .setItems(R.array.my_items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
            }
        });

        findViewById(R.id.btn_show_custom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);

                EditText editText = dialog.findViewById(R.id.edit_text);
                dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("TAG", "Hello " + editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        findViewById(R.id.btn_show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast toast = Toast.makeText(MainActivity.this, "Download complete!", Toast.LENGTH_LONG);
//                // toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
//                toast.setMargin(-50,50);
//                toast.show();

                View customToastView = getLayoutInflater().inflate(R.layout.custom_toast, null);
                TextView textView = customToastView.findViewById(R.id.text_view);
                textView.setText("Hello World");

                Toast toast = new Toast(MainActivity.this);
                toast.setView(customToastView);
                toast.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.add(0, 101, 0, "Call");
        menu.add(0, 102, 0, "SMS");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedItem = items.get(info.position);

        int id = item.getItemId();
        if (id == 101) {
            Log.v("TAG", "CALL action on item " + selectedItem);
        } else if (id == 102) {
            Log.v("TAG", "SMS action on item " + selectedItem);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("TAG", "Search with keyword: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.v("TAG", "Keyword: " + newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Log.v("TAG", "Search action");
        } else if (id == R.id.action_share) {
            Log.v("TAG", "Share action");
        } else if (id == R.id.action_download) {
            Log.v("TAG", "Download action");
        } else if (id == R.id.action_settings) {
            Log.v("TAG", "Settings action");
        } else if (id == R.id.action_about) {
            Log.v("TAG", "About action");
        }

        return super.onOptionsItemSelected(item);
    }
}