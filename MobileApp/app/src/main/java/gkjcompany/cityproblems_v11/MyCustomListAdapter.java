package gkjcompany.cityproblems_v11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyCustomListAdapter extends ArrayAdapter<Event> {
    Context mCtx;
    int recourse;
    List<Event> eventList;
    String useremail;
    TextView likes ;
    TextView dislikes;



    public MyCustomListAdapter(Context mCtx, int recourse, List<Event> eventList) {
        super(mCtx, recourse, eventList);
        this.mCtx = mCtx;
        this.recourse = recourse;
        this.eventList = eventList;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(recourse, null);

        TextView eventCategory = view.findViewById(R.id.txtProblem);
        TextView eventDay = view.findViewById(R.id.txtDay);
        TextView eventMonth = view.findViewById(R.id.txtMonth);
        TextView eventYear = view.findViewById(R.id.txtYear);
        TextView descrpt = view.findViewById(R.id.et_description);
        likes = view.findViewById(R.id.textLikes);
        dislikes = view.findViewById(R.id.textDislikes);
        ImageView eventImage = view.findViewById(R.id.imageViewProblem);
        //ImageView imageLikes = view.findViewById(R.id.imageLikes);
        //ImageView imageDislikes = view.findViewById(R.id.imageDisllikes);
        URL url;


        final Event event = eventList.get(position);
        useremail = event.getEmail();
        Log.e("email",useremail);
        eventCategory.setText(event.getCategory());
        eventDay.setText(event.getDay());
        eventMonth.setText(event.getMonth());
        eventYear.setText(event.getYear());
        descrpt.setText(event.getDescription());
        descrpt.setMovementMethod(new ScrollingMovementMethod());
        likes.setText("(" + event.getUpvotes() + ")");
        dislikes.setText("(" + event.getDownVotes() + ")");
        try {

            new DownloadImageTask((ImageView) view.findViewById(R.id.imageViewProblem))
                    .execute(event.getPath());

           // imageLikes.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.like));
           // imageDislikes.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.dislike));

        } catch (Exception e) {
            Log.e("problem", e.getMessage());
        }


        view.findViewById(R.id.btnLike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();

                String url = "https://icsdmobileprojectapi.000webhostapp.com/vote.php?voteType=upVote&email="+useremail+"&id=" + eventList.get(position).getId();

                Request request = new Request.Builder().url(url).build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("message from like: ", response.toString());
                        //if(response.toString().contains("200"))
                        //{

                        //}

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("remes from like: ", e.toString());
                    }
                });
            }
        });

        view.findViewById(R.id.btnDisslike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();

                String url = "https://icsdmobileprojectapi.000webhostapp.com/vote.php?voteType=downVote&email="+useremail+"&id=" + eventList.get(position).getId();

                Request request = new Request.Builder().url(url).build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("message from Disslike: ", response.toString());

                       // if(response.toString().equals("200"))
                       // {
                         //   int dislikescounter=Integer.parseInt(dislikes.getText().toString()) + 1;
                          //  dislikes.setText("(" + dislikescounter + ")");
                          //  dislikes.invalidate();
                        //}

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("ermesfrom Disslike: ", e.toString());
                    }


                });
            }
        });

        return view;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);


        }
    }
}
