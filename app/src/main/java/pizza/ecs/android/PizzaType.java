package pizza.ecs.android;

public enum PizzaType {
    PEPPERONI       ("Pepperoni"),
    MIGHTY_MEATY    ("Mighty Meaty"),
    HAWAIIAN        ("Hawaiian"),
    HOT_SPICY       ("Hot and Spicy");
    
    private String humanName;
    
    PizzaType(String value) {
        this.humanName = value;
    }
    
    public String getHumanName() {
        return humanName;
    }
}
