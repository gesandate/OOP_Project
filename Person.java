/**
 * The type Person.
 */
public abstract class Person {
    private String FName;
    private String LName;
    private int Age;
    private String gender;

    /**
     * Instantiates a new Person.
     */
    public Person() {
        this.FName = "NULL";
        this.LName = "NULL";
        this.Age = 0;
        this.gender = "NULL";
    }

    /**
     * Instantiates a new Person.
     *
     * @param FName the f name
     * @param LName the l name
     */
    public Person(String FName, String LName) {
        this.FName = FName;
        this.LName = LName;
    }

    /**
     * Instantiates a new Person.
     *
     * @param FName  the f name
     * @param LName  the l name
     * @param Age    the age
     * @param gender the gender
     */
    public Person(String FName, String LName, int Age, String gender) {
        this.FName = FName;
        this.LName = LName;
        this.Age = Age;
        this.gender = gender;
    }

    /**
     * Gets f name.
     *
     * @return the f name
     */
// Getters and setters for the attributes
    public String getFName() {
        return FName;
    }

    /**
     * Sets f name.
     *
     * @param FName the f name
     */
    public void setFName(String FName) {
        this.FName = FName;
    }

    /**
     * Gets l name.
     *
     * @return the l name
     */
    public String getLName() {
        return LName;
    }

    /**
     * Sets l name.
     *
     * @param LName the l name
     */
    public void setLName(String LName) {
        this.LName = LName;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return Age;
    }

    /**
     * Sets age.
     *
     * @param Age the age
     */
    public void setAge(int Age) {
        this.Age = Age;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}











// -_-
