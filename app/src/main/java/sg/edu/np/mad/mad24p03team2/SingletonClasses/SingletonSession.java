package sg.edu.np.mad.mad24p03team2.SingletonClasses;

import java.util.Date;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.AccountClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetCurrentUserProfile;

public class SingletonSession {
    private AccountClass account = null;
    private static volatile SingletonSession INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private SingletonSession() {
        account = new AccountClass();
    }

    // public static method to retrieve the singleton instance
    public static SingletonSession getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (SingletonSession.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new SingletonSession();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public void SignUpAccount(String name, String email) {
        account.setName(name);
        account.setEmail(email);
    }

    public void UpdateProfile(String gender, Date birthDate, float height, float weight) {
        account.setBirthDate(birthDate);
        account.setGender(gender);
        account.setHeight(height);
        account.setWeight(weight);
        account.setDietPlanOpt("Diabetic Friendly");
    }

    public void SetBloodSugarTracking(Boolean toTrack) {
        account.setTrackBloodSugar(toTrack);
    }

    public void CurrentLoginUser(String email) {
        account = new AccountClass();
        account.setEmail(email);
    }

    public AccountClass GetAccount(){
            return account;
    }
}
