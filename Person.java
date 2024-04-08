/**
 * The type Person.
 */
public abstract class Person {
    private String FName;
    private String LName;
    private int Age;
    private String gender;

    public Person() {
        this.FName = "NULL";
        this.LName = "NULL";
        this.Age = 0;
        this.gender = "NULL";
    }
    public Person(String FName, String LName) {
        this.FName = FName;
        this.LName = LName;
        // Set default values for the other attributes
        this.Age = 0;
        this.gender = "NULL";
    }

    public Person(String FName, String LName, int Age, String gender) {
        this.FName = FName;
        this.LName = LName;
        this.Age = Age;
        this.gender = gender;
    }

    // Getters and setters for the attributes
    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}











// -_-
