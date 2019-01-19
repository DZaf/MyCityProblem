package gkjcompany.cityproblems_v11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        Intent intent = getIntent();
        user_email = intent.getExtras().getString("user_email");

        Log.e("Intent String ", user_email);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            Log.e("Itemid"," " + itemId);
            if (itemId == R.id.list_Object) {
                Intent intent = new Intent(BaseActivity.this, ReportsList.class);
                intent.putExtra("user_email",user_email);
                startActivity(intent);
            } else if (itemId == R.id.report_Object) {
                Intent intent = new Intent(BaseActivity.this, CameraReport.class);
                intent.putExtra("user_email",user_email);
                startActivity(intent);
            } else if (itemId == R.id.map_Object) {
                Intent intent = new Intent(BaseActivity.this, Map.class);
                intent.putExtra("user_email",user_email);
                startActivity(intent);
            } else if (itemId == R.id.disconect) {
                Intent intent = new Intent(BaseActivity.this, LogIn.class);
                intent.putExtra("user_email",user_email);
                startActivity(intent);
            }
            finish();
        }, 300);
       return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}
