package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;


import java.util.Date;

// By Rui Ning
// Database User Account Object Class
public class AccountClass {
    private int id;
    private String name;
    private String email;
    private Date birthDate;
    private String dietPlanOpt;
    private String gender;
    private float height;
    private float weight;
    private boolean trackBloodSugar;

    public AccountClass(){
        dietPlanOpt = "Diabetic Friendly";
    }

    public AccountClass(int id, String name, String email){
        this();
        this.id = id;
        this.name = name;
        this.email = email;
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

    public String getDietPlanOpt() {
        return dietPlanOpt;
    }
    public void setDietPlanOpt(String dietPlanOpt) {
        this.dietPlanOpt = dietPlanOpt;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isTrackBloodSugar() {
        return trackBloodSugar;
    }
    public void setTrackBloodSugar(boolean trackBloodSugar) {
        this.trackBloodSugar = trackBloodSugar;
    }
}
