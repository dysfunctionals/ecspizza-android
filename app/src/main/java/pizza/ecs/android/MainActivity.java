package pizza.ecs.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    
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
        
        // TODO: get username
        //setTitle("Joshua's pizzas");
        // TODO: profile pictures?
        setTitle("   Joshua's pizzas");
        getSupportActionBar().setIcon(R.drawable.ic_action_name);
    
        PizzaAdapter pizzaAdapter = new PizzaAdapter();
        
        RecyclerView recyclerView = findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CustomSpacing());
        recyclerView.setAdapter(pizzaAdapter);
    
        // TODO: remove this placeholder data
        String pepperoni = "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/pepperonipassion-20170704.jpg";
        String meaty = "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/mighty-meaty-20170704.jpg";
        String haw = "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/hawaiian-20170704.jpg";
        String spicy = "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/hotspicey-20170704.jpg";
        
        Uri pepperoniURI = Uri.parse(pepperoni);
        Uri meatyURI = Uri.parse(meaty);
        Uri hawURI = Uri.parse(haw);
        Uri spicyURI = Uri.parse(spicy);
    
        Date[] dates = new Date[8];
        for(int i = 0; i < 8; i++) {
            dates[i] = new Date();
            dates[i].setTime(new Date().getTime() - (long) (Math.random() * 1000000000) - (long) (Math.random() * 1000000));
        }
        
        pizzaAdapter.addPizza(new Pizza(PizzaType.PEPPERONI, pepperoniURI, dates[1]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.MIGHTY_MEATY, meatyURI, dates[2]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.HAWAIIAN, hawURI, dates[3]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.HOT_SPICY, spicyURI, dates[4]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.PEPPERONI, pepperoniURI, dates[5]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.MIGHTY_MEATY, meatyURI, dates[6]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.HAWAIIAN, hawURI, dates[7]));
        pizzaAdapter.addPizza(new Pizza(PizzaType.HOT_SPICY, spicyURI, dates[0]));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            System.out.println(data.getData().toString());
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
        // Handle action bar item clicks here.
        int id = item.getItemId();
    
        switch(id) {
            case R.id.action_sort:
                // TODO: sort?
                return true;
                
            case R.id.action_filter:
                // TODO: filter?
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
