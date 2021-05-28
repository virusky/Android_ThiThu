package iuh.edu.thithu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddUser extends AppCompatActivity {

    private final String URL = "https://60b095091f26610017ffe858.mockapi.io/api/android-thithu/user";
    private TextView txtAddName;
    private TextView txtAddAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        txtAddName = findViewById(R.id.txtAddName);
        txtAddAge = findViewById(R.id.txtAddAge);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            postData(URL);
            startActivity(new Intent(AddUser.this, Listview.class));
            finish();
        });

    }
    private void postData(String url) {
        JSONObject data = new JSONObject();
        try {
            data.put("name", txtAddName.getText().toString());
            data.put("age", txtAddAge.getText().toString());

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
}