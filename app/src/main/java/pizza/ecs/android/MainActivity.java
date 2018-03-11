package pizza.ecs.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    
    private PizzaAdapter pizzaAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PhotoMethodDialog().show(getFragmentManager(), "choiceDialog");
            }
            
            
        });
    
        APIHelper.getInstance(this).addToRequestQueue(new StringRequest(APIHelper.URL + "user/" + APIHelper.uuid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    setTitle(new JSONObject(response).getString("display_name").split(" ")[0] + "'s pizzas");
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null));
    
        final SwipeRefreshLayout swipe = findViewById(R.id.refreshLayout);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APIHelper.updatePizzaList(MainActivity.this, pizzaAdapter, swipe);
                swipe.setRefreshing(false);
            }
        });
    
        pizzaAdapter = new PizzaAdapter();
        
        RecyclerView recyclerView = findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CustomSpacing());
        recyclerView.setAdapter(pizzaAdapter);
    
        APIHelper.updatePizzaList(this, pizzaAdapter, null);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String localFileURI = data.getData().toString();
            // TODO: upload picture
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    
        switch(id) {
            case R.id.action_sort:
                PopupMenu menu = new PopupMenu(this, findViewById(R.id.action_sort));
                menu.inflate(R.menu.menu_sort);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem subItem) {
                        pizzaAdapter.sort(subItem.toString().equals("Newest first")? 0 : 1);
                        return true;
                    }
                });
                menu.show();
                return true;
        
            case R.id.action_logout:
                finish();
                return true;
        }
    
        return super.onOptionsItemSelected(item);
    }
    
    private class CustomSpacing extends RecyclerView.ItemDecoration {
        
        private static final int OFFSETS = 20;
    
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int pos = parent.getChildAdapterPosition(view);
            outRect.set(pos % 2 == 0? OFFSETS : OFFSETS/2,
                        pos > 1? OFFSETS/2 : OFFSETS,
                        pos % 2 == 1? OFFSETS : OFFSETS/2,
                        pos < parent.getAdapter().getItemCount() - 2? OFFSETS/2 : OFFSETS);
        }
    }
}
