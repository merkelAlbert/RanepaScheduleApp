package com.assistant.albert.ranepaschedule;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private SwipeRefreshLayout scheduleSwipeRefreshLayout;
    private Button reloadButton;

    public void getSchedule(final String url) {
        final List<ScheduleItem> scheduleItems = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        spinner.setVisibility(View.GONE);
                        JSONArray schedule = response;
                        Log.i("f", schedule.toString());
                        for (int i = 0; i < schedule.length(); i++) {
                            try {
                                JSONObject object = schedule.getJSONObject(i);
                                ScheduleItem scheduleItem = new ScheduleItem();
                                scheduleItem.setDayOfWeek(object.getString("dayOfWeek"));

                                JSONArray times = object.getJSONArray("times");
                                JSONArray subjects = object.getJSONArray("subjects");
                                List<String> timesList = new ArrayList<>();
                                List<String> subjectsList = new ArrayList<>();
                                for (int j = 0; j < times.length(); j++) {
                                    timesList.add(times.getString(j));
                                    subjectsList.add(subjects.getString(j));
                                }
                                scheduleItem.setTimes(timesList);
                                scheduleItem.setSubjects(subjectsList);
                                scheduleItems.add(scheduleItem);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RecyclerView.Adapter adapter = new ScheduleRecyclerAdapter(scheduleItems);
                        recyclerView.setAdapter(adapter);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                spinner.setVisibility(View.GONE);
                reloadButton.setVisibility(View.VISIBLE);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reloadButton.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                        getSchedule(url);
                    }
                });
            }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.scheduleRecycler);
        spinner = findViewById(R.id.progressBar);
        reloadButton = findViewById(R.id.reloadButton);
        scheduleSwipeRefreshLayout = findViewById(R.id.scheduleSwipeRefreshLayout);

        reloadButton.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        scheduleSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        scheduleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);
                scheduleSwipeRefreshLayout.setRefreshing(true);
                getSchedule(Urls.Schedule);

                scheduleSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        scheduleSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getSchedule(Urls.Schedule);
    }
}
