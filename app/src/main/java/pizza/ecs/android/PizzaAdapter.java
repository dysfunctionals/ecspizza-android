package pizza.ecs.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.*;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {
    
    private List<Pizza> pizzas;
    private int sortDirection = 0;
    
    public PizzaAdapter() {
        pizzas = new ArrayList<>();
    }
    
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        sort(sortDirection);
        notifyDataSetChanged();
    }
    
    public void removeAllPizzas() {
        pizzas.clear();
    }
    
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
        notifyDataSetChanged();
    }
    
    public void sort(int direction) {
        sortDirection = direction;
        Collections.sort(pizzas, new Comparator<Pizza>() {
            @Override
            public int compare(Pizza o1, Pizza o2) {
                switch(sortDirection) {
                    case 0:
                        return o2.getDate().compareTo(o1.getDate());
                    
                    default:
                        return o1.getDate().compareTo(o2.getDate());
                }
            }
        });
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_item, null);
    
        view.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(parent.getContext(), view.findViewById(R.id.action));
                menu.inflate(R.menu.menu_pizza_item);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getTitle().toString()) {
                            case "Details":
                                // TODO: details activity?
                                return false;
                        
                            case "Remove":
                                // TODO: remove pizza
                                return false;
                        
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        
        return new ViewHolder(view, parent.getContext());
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        //holder.image.setImageBitmap();
        Glide.with(holder.parentContext).load(pizza.getPhoto()).into(holder.image);
        holder.title.setText(pizza.getType().getHumanName() + " pizza");
        
        Date date = pizza.getDate();
        
        holder.datetime.setText(new SimpleDateFormat("dd/MM/y").format(date) + " at " +
                                new SimpleDateFormat("HH:mm").format(date));
    }
    
    @Override
    public int getItemCount() {
        return pizzas.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
    
        public ImageView image, action;
        public TextView title, datetime;
        
        public Context parentContext;
    
        public ViewHolder(View itemView, Context parentContext) {
            super(itemView);
            
            this.parentContext = parentContext;
            
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            datetime = itemView.findViewById(R.id.datetime);
            action = itemView.findViewById(R.id.action);
        }
    }
}
