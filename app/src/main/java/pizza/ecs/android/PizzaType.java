package pizza.ecs.android;

public enum PizzaType {
    cheeseandtomato("Margherita", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/cheese-and-tomato-20170704.jpg?v=80b57f8df6e8fcfca8cff4d4a2c158b4"), pepperoni("Pepperoni", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/pepperonipassion-20170704.jpg"), pepperonihot("Hot Pepperoni", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/americanhot-20170704.jpg?v=93806a6a507dab240c4d2ccc8614a63e"), veggi("Veggi Supreme", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/veggie-supreme-20170704.jpg?v=e461308b60473794cfa0a66a7aca07b9"), meatfeast("Meat feast", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/mighty-meaty-20170704.jpg"), hawaiian("Hawaiian", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/hawaiian-20170704.jpg"), other("Unknown", "https://www.dominos.co.uk/Content/images/Products/GB/Pizza/256x256/hotspicey-20170704.jpg");
    
    private String humanName;
    private String tempURI;
    
    PizzaType(String value, String tempURI) {
        this.humanName = value;
        this.tempURI = tempURI;
    }
    
    public String getHumanName() {
        return humanName;
    }
    
    public String getTempURI() {
        return tempURI;
    }
}
