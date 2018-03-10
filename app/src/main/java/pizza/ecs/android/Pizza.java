package pizza.ecs.android;

import android.net.Uri;

import java.util.Date;

public class Pizza {
    
    private Date date;
    private PizzaType type;
    private Uri photo;
    
    public Pizza(PizzaType type, Uri photo, Date dateTime) {
        this.type = type;
        this.photo = photo;
        this.date = dateTime;
    }

    public Pizza(PizzaType type, Uri photo) {
        this(type, photo, new Date());
    }
    
    public Date getDate() {
        return date;
    }
    
    public PizzaType getType() {
        return type;
    }
    
    public Uri getPhoto() {
        return photo;
    }
}
