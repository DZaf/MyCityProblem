package gkjcompany.cityproblems_v11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReportsList extends BaseActivity {

    private TextView mText;
    List<Event> eventList;
    ListView listview;
    Context myContext;
    String myResponse;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user_email = intent.getExtras().getString("user_email");

        //Log.e("Intent String", user_email);

        listview = (ListView)findViewById(R.id.listview);
        myContext = ReportsList.this;
        Log.e("event", "mpa mpa 1");

        OkHttpClient client= new OkHttpClient();

        String url="https://icsdmobileprojectapi.000webhostapp.com/display.php?displayType=displayAll";

        Request request=new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myResponse=response.body().string();

                ReportsList.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eventList = new ArrayList();
                        try {

                            JSONObject reader = new JSONObject(myResponse);

                            JSONObject event = reader.getJSONObject("event");
                            for (int i = 0; i < event.length(); i++) {
                                JSONObject thisevent = event.getJSONObject(String.valueOf(i));
                                Event tempEvent;
                                if (thisevent.getJSONObject("votes").getJSONObject("downVotes").get("email") instanceof String && thisevent.getJSONObject("votes").getJSONObject("upVotes").get("email") instanceof String) {
                                    tempEvent = new Event(user_email,thisevent.getJSONObject("@attributes").getInt("id"), thisevent.getString("fotoPath"), thisevent.getString("category"),
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("day") + "/", thisevent.getJSONObject("sendTime").getJSONObject("date").getString("month") + "/",
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("year"), thisevent.getJSONObject("sendTime").getString("hour"), thisevent.getJSONObject("location").getDouble("longtitude"),
                                            thisevent.getJSONObject("location").getDouble("latitude"), thisevent.getJSONObject("votes").getJSONObject("upVotes").length(),
                                            thisevent.getJSONObject("votes").getJSONObject("downVotes").length(),thisevent.getString("description"));
                                } else if (thisevent.getJSONObject("votes").getJSONObject("downVotes").get("email") instanceof String && thisevent.getJSONObject("votes").getJSONObject("upVotes").get("email") instanceof JSONObject) {

                                    tempEvent = new Event(user_email,thisevent.getJSONObject("@attributes").getInt("id"), thisevent.getString("fotoPath"), thisevent.getString("category"),
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("day") + "/", thisevent.getJSONObject("sendTime").getJSONObject("date").getString("month") + "/",
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("year"), thisevent.getJSONObject("sendTime").getString("hour"), thisevent.getJSONObject("location").getDouble("longtitude"),
                                            thisevent.getJSONObject("location").getDouble("latitude"), thisevent.getJSONObject("votes").getJSONObject("upVotes").getJSONObject("email").length(),
                                            thisevent.getJSONObject("votes").getJSONObject("downVotes").length(),thisevent.getString("description"));
                                } else if (thisevent.getJSONObject("votes").getJSONObject("downVotes").get("email") instanceof JSONObject && thisevent.getJSONObject("votes").getJSONObject("upVotes").get("email") instanceof JSONObject) {
                                    tempEvent = new Event(user_email,thisevent.getJSONObject("@attributes").getInt("id"), thisevent.getString("fotoPath"), thisevent.getString("category"),
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("day") + "/", thisevent.getJSONObject("sendTime").getJSONObject("date").getString("month") + "/",
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("year"), thisevent.getJSONObject("sendTime").getString("hour"), thisevent.getJSONObject("location").getDouble("longtitude"),
                                            thisevent.getJSONObject("location").getDouble("latitude"), thisevent.getJSONObject("votes").getJSONObject("upVotes").getJSONObject("email").length(),
                                            thisevent.getJSONObject("votes").getJSONObject("downVotes").getJSONObject("email").length(),thisevent.getString("description"));
                                } else {
                                    tempEvent = new Event(user_email,thisevent.getJSONObject("@attributes").getInt("id"), thisevent.getString("fotoPath"), thisevent.getString("category"),
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("day") + "/", thisevent.getJSONObject("sendTime").getJSONObject("date").getString("month") + "/",
                                            thisevent.getJSONObject("sendTime").getJSONObject("date").getString("year"), thisevent.getJSONObject("sendTime").getString("hour"), thisevent.getJSONObject("location").getDouble("longtitude"),
                                            thisevent.getJSONObject("location").getDouble("latitude"), thisevent.getJSONObject("votes").getJSONObject("downVotes").getJSONObject("email").length(),
                                            thisevent.getJSONObject("votes").getJSONObject("upVotes").length(),thisevent.getString("description"));
                                }

                                Log.e("event", tempEvent.toString());
                                eventList.add(tempEvent);
                            }


                            MyCustomListAdapter adapter = new MyCustomListAdapter(myContext, R.layout.my_list_item, eventList);

                            listview.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.e("problem", e.getMessage());
                        }
                    }
                });
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_reports_list;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.list_Object;
    }
}
