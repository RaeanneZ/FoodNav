package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

public class FoodItemClass {
    private int id;
    private String name;
    private double calories;
    private double serving_size_g;
    private double fat_total_g;
    private double fat_saturated_g;
    private double protein_g;
    private double sodium_mg;
    private double potassium_mg;
    private double cholesterol_mg;
    private double carbohydrates_total_g;
    private double fiber_g;
    private double sugar_g;

    public FoodItemClass(int id, String name, double calories, double serving_size_g, double fat_total_g, double protein_g, double carbohydrates_total_g) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.serving_size_g = serving_size_g;
        this.fat_total_g = fat_total_g;
        this.protein_g = protein_g;
        this.carbohydrates_total_g = carbohydrates_total_g;
    }

    public FoodItemClass(String name, double calories, double serving_size_g, double fat_total_g, double protein_g, double carbohydrates_total_g) {
        this.name = name;
        this.calories = calories;
        this.serving_size_g = serving_size_g;
        this.fat_total_g = fat_total_g;
        this.protein_g = protein_g;
        this.carbohydrates_total_g = carbohydrates_total_g;
    }

    public FoodItemClass(String name, double calories, double serving_size_g, double fat_total_g, double fat_saturated_g, double protein_g, double sodium_mg, double potassium_mg, double cholesterol_mg, double carbohydrates_total_g, double fiber_g, double sugar_g) {
        this.name = name;
        this.calories = calories;
        this.serving_size_g = serving_size_g;
        this.fat_total_g = fat_total_g;
        this.fat_saturated_g = fat_saturated_g;
        this.protein_g = protein_g;
        this.sodium_mg = sodium_mg;
        this.potassium_mg = potassium_mg;
        this.cholesterol_mg = cholesterol_mg;
        this.carbohydrates_total_g = carbohydrates_total_g;
        this.fiber_g = fiber_g;
        this.sugar_g = sugar_g;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getServing_size_g() {
        return serving_size_g;
    }
    public void setServing_size_g(double serving_size_g) {
        this.serving_size_g = serving_size_g;
    }

    public double getFat_total_g() {
        return fat_total_g;
    }
    public void setFat_total_g(double fat_total_g) {
        this.fat_total_g = fat_total_g;
    }

    public double getFat_saturated_g() {
        return fat_saturated_g;
    }
    public void setFat_saturated_g(double fat_saturated_g) {
        this.fat_saturated_g = fat_saturated_g;
    }

    public double getProtein_g() {
        return protein_g;
    }
    public void setProtein_g(double protein_g) {
        this.protein_g = protein_g;
    }

    public double getSodium_mg() {
        return sodium_mg;
    }
    public void setSodium_mg(double sodium_mg) {
        this.sodium_mg = sodium_mg;
    }

    public double getPotassium_mg() {
        return potassium_mg;
    }
    public void setPotassium_mg(double potassium_mg) {
        this.potassium_mg = potassium_mg;
    }

    public double getCholesterol_mg() {
        return cholesterol_mg;
    }
    public void setCholesterol_mg(double cholesterol_mg) {
        this.cholesterol_mg = cholesterol_mg;
    }

    public double getCarbohydrates_total_g() {
        return carbohydrates_total_g;
    }
    public void setCarbohydrates_total_g(double carbohydrates_total_g) {
        this.carbohydrates_total_g = carbohydrates_total_g;
    }

    public double getFiber_g() {
        return fiber_g;
    }
    public void setFiber_g(double fiber_g) {
        this.fiber_g = fiber_g;
    }

    public double getSugar_g() {
        return sugar_g;
    }
    public void setSugar_g(double sugar_g) {
        this.sugar_g = sugar_g;
    }
}
