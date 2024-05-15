package sg.edu.np.mad.mad24p03team2.Abstract_Interfaces;

public interface IDBProcessListener {
    // Function to be implemented
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd);
    public void afterProcess(Boolean executeStatus);
}
