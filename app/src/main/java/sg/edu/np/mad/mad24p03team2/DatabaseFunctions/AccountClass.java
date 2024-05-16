package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;


// By Rui Ning
// Database User Account Object Class
public class AccountClass {
    private int id;
    private String name;
    private String email;
    private int dietPlanOpt;
    private char gender;
    private int height;
    private int weight;

    public AccountClass(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public AccountClass(int id, String name, String email, int dietPlanOpt){
        this(id, email, name);
        this.dietPlanOpt = dietPlanOpt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getDietPlanOpt() {
        return dietPlanOpt;
    }
    public void setDietPlanOpt(int dietPlanOpt) {
        this.dietPlanOpt = dietPlanOpt;
    }

    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }


}
