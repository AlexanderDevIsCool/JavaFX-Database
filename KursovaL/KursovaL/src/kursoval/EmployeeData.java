package kursoval;
public class EmployeeData {
    
    // employee fields
    private String id;
    private String name;
    private String surname;
    private String age;
    private String address;
    private String telefon;
    private String salary;
    private String position;
    private String foto;
    
    // goods fields
    private String animalID;
    private String animalType;
    private String animalName;
    private String animalPrice;
    private String animalCountry;
    private String animalAvgLife;
    private String animalEat;
    private String animalProvider;
    private String animalAge;
    private String animalPhoto;
    private String animalCount;

    public String getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(String animalCount) {
        this.animalCount = animalCount;
    }

    public EmployeeData(String animalID, String animalType, String animalName, 
            String animalPrice, String animalCountry, String animalAvgLife, String animalEat, 
            String animalProvider, String animalAge, String animalPhoto, String animalCount) {
        this.animalID = animalID;
        this.animalType = animalType;
        this.animalName = animalName;
        this.animalPrice = animalPrice;
        this.animalCountry = animalCountry;
        this.animalAvgLife = animalAvgLife;
        this.animalEat = animalEat;
        this.animalProvider = animalProvider;
        this.animalAge = animalAge;
        this.animalPhoto = animalPhoto;
        this.animalCount = animalCount;
    }

    public String getAnimalID() {
        return animalID;
    }

    public void setAnimalID(String animalID) {
        this.animalID = animalID;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalPrice() {
        return animalPrice;
    }

    public void setAnimalPrice(String animalPrice) {
        this.animalPrice = animalPrice;
    }

    public String getAnimalCountry() {
        return animalCountry;
    }

    public void setAnimalCountry(String animalCountry) {
        this.animalCountry = animalCountry;
    }

    public String getAnimalAvgLife() {
        return animalAvgLife;
    }

    public void setAnimalAvgLife(String animalAvgLife) {
        this.animalAvgLife = animalAvgLife;
    }

    public String getAnimalEat() {
        return animalEat;
    }

    public void setAnimalEat(String animalEat) {
        this.animalEat = animalEat;
    }

    public String getAnimalProvider() {
        return animalProvider;
    }

    public void setAnimalProvider(String animalProvider) {
        this.animalProvider = animalProvider;
    }

    public String getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(String animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalPhoto() {
        return animalPhoto;
    }

    public void setAnimalPhoto(String animalPhoto) {
        this.animalPhoto = animalPhoto;
    }

    public EmployeeData(String id, String name, String surname, String age, String address, String telefon, String salary, String position, String foto) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.telefon = telefon;
        this.salary = salary;
        this.position = position;
        this.foto = foto;
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon the telefon to set
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * @return the salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param id  the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
