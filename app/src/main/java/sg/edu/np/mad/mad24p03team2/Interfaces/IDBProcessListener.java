package sg.edu.np.mad.mad24p03team2.Interfaces;

public interface IDBProcessListener {
    // Function to be implemented
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd);
    public void afterProcess(Boolean executeStatus);
}
