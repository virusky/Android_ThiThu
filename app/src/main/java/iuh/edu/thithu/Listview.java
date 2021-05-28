package iuh.edu.thithu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Listview extends AppCompatActivity {

    private final String URL = "https://60b095091f26610017ffe858.mockapi.io/api/android-thithu/user";
    private ArrayAdapter adapter;
    private TextView txtId;
    private TextView txtName;
    private TextView txtAge;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);

        findViewById(R.id.btnFind).setOnClickListener(v -> {
            getData(URL, txtId.getText().toString());
        });
        findViewById(R.id.btnUpdate).setOnClickListener(v -> {
            if(!txtId.getText().toString().isEmpty())
                putData(URL, txtId.getText().toString());
            getData(URL);
        });
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(Listview.this,AddUser.class);
                startActivity(intent);
                finish();}
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            deleteData(URL, txtId.getText().toString());
            getData(URL);
        });

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            JSONObject item = (JSONObject) adapter.getItem(i);

            try {
                txtId.setText(item.get("id").toString());
                txtName.setText(item.getString("name"));
                txtAge.setText(item.getString("age"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        getData(URL);
    }

    private void getData(String url) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    adapter.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            adapter.add(response.get(i));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getData(String url, String id) {
        if(id.isEmpty()){
            getData(url);
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "/" + id,
                null,
                response -> {
                    adapter.clear();
                    adapter.add(response);
                },
                error -> {
                    Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void putData(String url, String id) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", txtId.getText().toString());
            data.put("name", txtName.getText().toString());
            data.put("age", txtAge.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url + "/" + id,
                data,
                response -> {
                    Log.d("AAA", response.toString());
                    Toast.makeText(this, "PUT thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when put data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void postData(String url) {
        JSONObject data = new JSONObject();
        try {
            data.put("name", txtName.getText().toString());
            data.put("age", txtAge.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                data,
                response -> {
                    Log.d("AAA", response.toString());
                    Toast.makeText(this, "POST thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when POST data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void deleteData(String url, String id) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url + "/" + id,
                null,
                response -> {
                    Toast.makeText(this, "DELETE thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when DELETE data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}