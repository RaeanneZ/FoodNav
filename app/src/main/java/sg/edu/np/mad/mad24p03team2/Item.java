package sg.edu.np.mad.mad24p03team2;

public class Item {

    String name;
    String calNum;
    String servingSize;

    public Item(String name, String calNum, String servingSize) {
        this.name = name;
        this.calNum = calNum;
        this.servingSize = servingSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalNum() {
        return calNum;
    }

    public void setCalNum(String calNum) {
        this.calNum = calNum;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
}
