package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

public class FoodItemClass {
    private int foodID;
    private String name;
    private int carb;
    private int calories;
    private int protein;
    private int fat;
    private String servingSize;

    public FoodItemClass(int foodID, String name, int calories, int carb, int protein, int fat, String servingSize) {
        this.foodID = foodID;
        this.name = name;
        this.calories = calories;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.servingSize = servingSize;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarb() {
        return carb;
    }
    public void setCarb(int carb) {
        this.carb = carb;
    }

    public int getProtein() {
        return protein;
    }
    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }

    public String getServingSize() {
        return servingSize;
    }
    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
}
