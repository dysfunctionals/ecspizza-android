package pizza.ecs.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.*;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {
    
    private List<Pizza> pizzas;
    
    public PizzaAdapter() {
        pizzas = new ArrayList<>();
    }
    
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_item, null);
        
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
