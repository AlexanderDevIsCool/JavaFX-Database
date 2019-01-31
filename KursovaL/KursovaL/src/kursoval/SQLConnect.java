package kursoval;
import java.sql.*;
public class SQLConnect {
    private final String URL = "jdbc:mysql://localhost:3306/zoomagazinedb";
    private final String URLDB = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASSWORD = "aligment%1945";
    private boolean DBExist = false;
    private final String dbname = "zoomagazinedb";
    private PreparedStatement prs = null;
    private Connection conn = null;
    private Statement st = null;
    private String tableEmployee = "CREATE TABLE employee(\n"
            + "EmployeeID int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
            + "EmployeeName varchar(40),\n"
            + "EmployeeSurName varchar(40),\n"
            + "EmployeeAddress varchar(100),\n"
            + "EmployeeTelefon varchar(30),\n"
            + "EmployeeSalary int(10),\n"
            + "EmployeePosition varchar(40),\n"
            + "EmployeeFoto varchar(100),\n"
            + "EmployeeAge varchar(100)\n"
            + ")";
    private String tableGoods = "CREATE TABLE goods(\n"
            + "AnimalID int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
            + "AnimalType varchar(40),\n"
            + "AnimalName varchar(40),\n"
            + "AnimalPrice int(10),\n"
            + "AnimalCountry varchar(50),\n"
            + "AnimalAvgLife int(10),\n"
            + "AnimalEat varchar(100),\n"
            + "AnimalProvider varchar(100),\n"
            + "AnimalAge float(5,2),\n"
            + "AnimalFoto varchar(100),\n"
            + "AnimalCount int(10)\n"
            + ");";
    SQLConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URLDB, USER, PASSWORD);
            ResultSet rs = conn.getMetaData().getCatalogs();
            while(rs.next()) {
                if(rs.getString(1).equals(dbname)) {
                    DBExist = true;
                }
            }
            
            if(!DBExist) {
                st = conn.createStatement();
                st.execute("CREATE DATABASE "+dbname);
                st.close();
                conn.close();
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                st = conn.createStatement();
                st.execute(tableEmployee);
                st.execute(tableGoods);
            } else {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            System.out.println("DB Connected");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            System.out.println("Driver Creation Errorq" + ex.getLocalizedMessage() + ex.getMessage());
        }
    }

    public ResultSet getResultSet(String Select) throws SQLException {
        st = conn.createStatement();
        return st.executeQuery(Select);
    }

    public boolean insertEmployee(String Name, String surName, String age, String address, String Telefon, Integer Salary, String Position, String foto)
            throws SQLException {
        String prepInsert = "INSERT INTO employee(employee.`EmployeeName`,employee.`EmployeeSurName`,employee.`EmployeeAge`,\n"
                + "employee.`EmployeeAddress`, employee.`EmployeeTelefon`, employee.`EmployeeSalary`, employee.`EmployeePosition`, employee.`EmployeeFoto`) \n"
                + "value(?,?,?,?,?,?,?,?)";
        prs = conn.prepareStatement(prepInsert);
        prs.setString(1, Name);
        prs.setString(2, surName);
        prs.setString(3, age);
        prs.setString(4, address);
        prs.setString(5, Telefon);
        prs.setInt(6, Salary);
        prs.setString(7, Position);
        prs.setString(8, foto);
        System.out.println(prs.toString());
        return prs.executeUpdate() == 0;
    }

    public boolean updateEmployee(Integer id, String Name, String surName, String age,
            String address, String Telefon, Integer Salary, String Position, String foto) throws SQLException {
        String stmUpdate = "UPDATE employee SET EmployeeName = '" + Name + "', EmployeeSurName = '" + surName + "', EmployeeAge = '" + age + "', "
                + "EmployeeAddress = '" + address + "', EmployeeTelefon = '" + Telefon + "', EmployeeSalary = " + Salary + ", "
                + "EmployeePosition = '" + Position + "', EmployeeFoto = '" + foto + "' WHERE EmployeeID = '" + id + "'";
        return st.executeUpdate(stmUpdate) == 0;
    }

    public boolean insertGoods(String animalType, String animalName, Integer animalPrice,
            String animalCountry, Integer animalAvgLife, String animalEat, String animalProvider, Float animalAge, String animalPhoto,
             Integer animalCount)
            throws SQLException {
        System.out.println("Inserttttttttt");
        String prepInsert = "INSERT INTO goods(goods.`AnimalType`, goods.`AnimalName`, goods.`AnimalPrice`, goods.`AnimalCountry`,\n"
                + "goods.`AnimalAvgLife`, goods.`AnimalEat`, goods.`AnimalProvider`, goods.`AnimalAge`, goods.`AnimalFoto`, goods.`AnimalCount`) \n"
                + "values(?,?,?,?,?,?,?,?,?,?);";
        prs = conn.prepareStatement(prepInsert);
        prs.setString(1, animalType);
        prs.setString(2, animalName);
        prs.setInt(3, animalPrice);
        prs.setString(4, animalCountry);
        prs.setInt(5, animalAvgLife);
        prs.setString(6, animalEat);
        prs.setString(7, animalProvider);
        prs.setFloat(8, animalAge);
        prs.setString(9, animalPhoto);
        prs.setInt(10, animalCount);

        return prs.executeUpdate() == 0;
    }

    public boolean updateGoods(Integer id, String animalType, String animalName, Integer animalPrice,
            String animalCountry, Integer animalAvgLife, String animalEat, String animalProvider,
            Float animalAge, String animalPhoto, String AnimalCount) throws SQLException {
        System.out.println("Updatw");
        System.out.println("UPDATE goods SET AnimalType = '" + animalType + "', AnimalName = '" + animalName + "', AnimalPrice = " + animalPrice + ", "
                + "AnimalCountry = '" + animalCountry + "', AnimalAvgLife = " + animalAvgLife + ", AnimalEat = '" + animalEat + "', "
                + "AnimalProvider = '" + animalProvider + "', AnimalAge = " + animalAge + ", AnimalFoto = '" + animalPhoto + "', "
                + "AnimalCount = " + Integer.parseInt(AnimalCount) + " WHERE AnimalID = " + id + "");
        String stmUpdate = "UPDATE goods SET AnimalType = '" + animalType + "', AnimalName = '" + animalName + "', AnimalPrice = " + animalPrice + ", "
                + "AnimalCountry = '" + animalCountry + "', AnimalAvgLife = " + animalAvgLife + ", AnimalEat = '" + animalEat + "', "
                + "AnimalProvider = '" + animalProvider + "', AnimalAge = " + animalAge + ", AnimalFoto = '" + animalPhoto + "', "
                + "AnimalCount = " + Integer.parseInt(AnimalCount) + " WHERE AnimalID = " + id + "";
        return st.executeUpdate(stmUpdate) == 0;
    }

    public boolean deleteRecord(String from, Integer ids) throws SQLException {
        String delete = "DELETE FROM " + from + " WHERE " + from + "ID = " + ids + "";
        if (from.equals("goods")) {
            delete = "DELETE FROM " + from + " WHERE AnimalID = " + ids + "";
        }

        prs = conn.prepareStatement(delete);
        return prs.execute();
    }
    
    public void commandMe(String sqlCommand) throws SQLException {
        st = conn.createStatement();
        st.execute(sqlCommand);
    }
//    public static void main(String args[]) {
//        new SQLConnect();
//    }
}
