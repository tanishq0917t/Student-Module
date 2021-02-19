import com.tanishq.util.*;
import java.io.*;
import java.util.*;
import java.sql.*;
class MyConnection
{
private MyConnection(){}
public static Connection getConnection() throws Exception
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:****/****","**","**");
}catch(Exception e)
{
System.out.println("Connection Process Unsuccesful, please try again later");
}
return connection;
}
}
class StudentModule
{
Keyboard k=new Keyboard();
private String name;
private int rollNumber;
private int age;
private String mobileNumber;
private String fname;
private String mname;
private String aadharNo;
private int annualIncome;
private char gender;
private String standard;
private Connection connection;
private void viewOneByRoll() throws Exception
{
Keyboard k=new Keyboard();
int roll=k.getInt("Enter Student's Roll Number: ");
String standard=k.getString("Enter Student's Standard: ");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
connection=MyConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from student where roll=? and standard=?");
preparedStatement.setInt(1,roll);
preparedStatement.setString(2,standard);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new Exception("Invalid Roll Number: "+roll);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from student where roll=? and standard=?");
preparedStatement.setInt(1,roll);
preparedStatement.setString(2,standard);
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
System.out.println("\n------------------------------");
System.out.println("Name: "+resultSet.getString("name"));
gender=resultSet.getString("gender").charAt(0);
if(gender=='M')System.out.println("Gender: Male");
else System.out.println("Gender: Female");
System.out.println("Roll: "+resultSet.getInt("roll"));
System.out.println("Standard: "+resultSet.getString("standard"));
System.out.println("Mobile Number: "+resultSet.getString("m_no"));
System.out.println("Father's Name: "+resultSet.getString("fname"));
System.out.println("Mother's Name: "+resultSet.getString("mname"));
System.out.println("Age: "+resultSet.getInt("age"));
System.out.println("Annual Income: "+resultSet.getInt("annual_income"));
System.out.println("Aadhar Card Number: "+resultSet.getString("aadhar_card_number"));
System.out.println("------------------------------\n");
}
}
private void viewOneByMob() throws Exception
{
Keyboard k=new Keyboard();
String mob=k.getString("Enter Student's Mobile Number: ");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
connection=MyConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from student where m_no=?");
preparedStatement.setString(1,mob);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new Exception("Invalid Mobile Number: "+mob);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from student where m_no=?");
preparedStatement.setString(1,mob);
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
System.out.println("\n------------------------------");
System.out.println("Name: "+resultSet.getString("name"));
gender=resultSet.getString("gender").charAt(0);
if(gender=='M')System.out.println("Gender: Male");
else System.out.println("Gender: Female");
System.out.println("Roll: "+resultSet.getInt("roll"));
System.out.println("Standard: "+resultSet.getString("standard"));
System.out.println("Mobile Number: "+resultSet.getString("m_no"));
System.out.println("Father's Name: "+resultSet.getString("fname"));
System.out.println("Mother's Name: "+resultSet.getString("mname"));
System.out.println("Age: "+resultSet.getInt("age"));
System.out.println("Annual Income: "+resultSet.getInt("annual_income"));
System.out.println("Aadhar Card Number: "+resultSet.getString("aadhar_card_number"));
System.out.println("------------------------------\n");
}
}
private void deleteViaRoll() throws Exception
{
int roll;
String standard;
Keyboard k=new Keyboard();
roll=k.getInt("Enter Roll Number of Student to delete: ");
standard=k.getString("Enter Standard of Student to delete: ");
standard=standard.toUpperCase();
Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
try
{
connection=MyConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from student where roll=? and standard=?");
preparedStatement.setInt(1,roll);
preparedStatement.setString(2,standard);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new Exception("Invalid Roll Number "+roll+" or Invalid Standard: "+standard);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("delete from student where roll=? and standard=?");
preparedStatement.setInt(1,roll);
preparedStatement.setString(2,standard);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
}//
private void deleteViaMob() throws Exception
{
String mobileNumber;
Keyboard k=new Keyboard();
mobileNumber=k.getString("Enter Mobile Number of Student to delete: ");
Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
try
{
connection=MyConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from student where m_no=?");
preparedStatement.setString(1,mobileNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new Exception("Invalid Mobile Number "+mobileNumber);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("delete from student where m_no=?");
preparedStatement.setString(1,mobileNumber);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
}//
public String addStudent() throws Exception
{
connection = MyConnection.getConnection();
PreparedStatement preparedStatement;
name=k.getString("Enter name of student: ");
if(name==null || name.length()==0)
{
System.out.println("Invalid Name\n");
return "";
}
name=name.trim();
age=k.getInt("Enter age of student: ");
mobileNumber=k.getString("Enter mobile number of student: ");
mobileNumber=mobileNumber.trim();
preparedStatement=connection.prepareStatement("select m_no from student where m_no=?");
preparedStatement.setString(1,mobileNumber);
ResultSet rSet=preparedStatement.executeQuery();
if(rSet.next())
{
rSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Mobile Number: "+mobileNumber+" exist against another student.");
}
rSet.close();
preparedStatement.close();
fname=k.getString("Enter father's name: ");
fname=fname.trim();
mname=k.getString("Enter mother's name: ");
mname=mname.trim();
aadharNo=k.getString("Enter aadhar number of student: ");
aadharNo=aadharNo.trim();
preparedStatement=connection.prepareStatement("select aadhar_card_number from student where aadhar_card_number=?");
preparedStatement.setString(1,aadharNo);
rSet=preparedStatement.executeQuery();
if(rSet.next())
{
rSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Aadhar Number: "+aadharNo+" exist against another student.");
}
rSet.close();
preparedStatement.close();
annualIncome=k.getInt("Enter annual income of family: ");
gender=k.getChar("Enter gender(M/F): ");
if(gender!='M' && gender!='F' && gender!='m' && gender!='f')
{
connection.close();
throw new Exception("Invalid Gender");
}
standard=k.getString("Enter current standard of student: ");
preparedStatement=connection.prepareStatement("insert into student (name,fname,mname,standard,m_no,age,annual_income,gender,aadhar_card_number) values(?,?,?,?,?,?,?,?,?)");
preparedStatement.setString(1,name);
preparedStatement.setString(2,fname);
preparedStatement.setString(3,mname);
preparedStatement.setString(4,standard);
preparedStatement.setString(5,mobileNumber);
preparedStatement.setInt(6,age);
preparedStatement.setInt(7,annualIncome);
preparedStatement.setString(8,String.valueOf(gender));
preparedStatement.setString(9,aadharNo);
preparedStatement.executeUpdate();
System.out.println("Student Added Successfully\n");
return "";
}
public void deleteStudent()
{
Keyboard k=new Keyboard();
int choice;
while(true)
{
System.out.println("Choose way to delete");
System.out.println("1. Roll Number");
System.out.println("2. Mobile Number");
choice=k.getInt("Enter your choice (1/2): ");
if(choice==1)
{
try
{
deleteViaRoll();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
break;
}
else if(choice==2)
{
try
{
deleteViaMob();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
break;
}
else
{
System.out.println("Invalid Choice, try again...");
continue;
}
}
}//delete ends here
public void updateStudent() throws Exception
{
boolean mobExists;
Keyboard k=new Keyboard();
String mob=k.getString("Enter Student's Mobile Number: ").trim();
Connection connection=null;
ResultSet rSet;
PreparedStatement preparedStatement;
try
{
connection=MyConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from student where m_no=?");
preparedStatement.setString(1,mob);
rSet=preparedStatement.executeQuery();
if(rSet.next()==false)
{
rSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Invalid Mobile Number: "+mob);
}
System.out.println("\nEnter all details to update");
System.out.println("Enter previous details if it isn't required to update\n");
name=k.getString("Enter name of student: ");
if(name==null || name.length()==0)
{
System.out.println("Invalid Name\n");
return;
}
name=name.trim();
age=k.getInt("Enter age of student: ");
fname=k.getString("Enter father's name: ");
fname=fname.trim();
mname=k.getString("Enter mother's name: ");
mname=mname.trim();
annualIncome=k.getInt("Enter annual income of family: ");
gender=k.getChar("Enter gender(M/F): ");
if(gender!='M' && gender!='F' && gender!='m' && gender!='f')
{
connection.close();
throw new Exception("Invalid Gender");
}
standard=k.getString("Enter current standard of student: ");
preparedStatement=connection.prepareStatement("update student set name=?,age=?,fname=?,mname=?,annual_income=?,gender=?,standard=? where m_no=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,age);
preparedStatement.setString(3,fname);
preparedStatement.setString(4,mname);
preparedStatement.setInt(5,annualIncome);
preparedStatement.setString(6,String.valueOf(gender));
preparedStatement.setString(7,standard);
preparedStatement.setString(8,mob);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
System.out.println("Student Updated Successfully\n");
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
public void viewParticular() throws Exception
{
Keyboard k=new Keyboard();
int ch;
while(true)
{
System.out.println("Choose way to view details");
System.out.println("1. View by entering Mobile Number of Student");
System.out.println("2. View by entering Standard and Roll Number of Student\n");
ch=k.getInt("Enter your choice: ");
if(ch==1)
{
try
{
viewOneByMob();
break;
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else if(ch==2)
{
try
{
viewOneByRoll();
break;
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else
{
System.out.println("Invalid Choice, Try again....");
continue;
}
}
}
public void viewAll() throws Exception
{
Connection connection=null;
ResultSet resultSet;
try
{
connection=MyConnection.getConnection();
Statement statement=connection.createStatement();
resultSet=statement.executeQuery("select * from student");
while(resultSet.next())
{
System.out.println("\n------------------------------");
System.out.println("Name: "+resultSet.getString("name"));
gender=resultSet.getString("gender").charAt(0);
if(gender=='M')System.out.println("Gender: Male");
else System.out.println("Gender: Female");
System.out.println("Roll: "+resultSet.getInt("roll"));
System.out.println("Standard: "+resultSet.getString("standard"));
System.out.println("Mobile Number: "+resultSet.getString("m_no"));
System.out.println("Father's Name: "+resultSet.getString("fname"));
System.out.println("Mother's Name: "+resultSet.getString("mname"));
System.out.println("Age: "+resultSet.getInt("age"));
System.out.println("Annual Income: "+resultSet.getInt("annual_income"));
System.out.println("Aadhar Card Number: "+resultSet.getString("aadhar_card_number"));
System.out.println("------------------------------\n");
}
resultSet.close();
connection.close();
statement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}//class ends here
class Module
{
public static void main(String gg[])
{
StudentModule s=new StudentModule();
int choice=0;
Keyboard k=new Keyboard();
while(true)
{
System.out.println("1. Add a student");
System.out.println("2. Delete a student");
System.out.println("3. Update details of student");
System.out.println("4. View Details of particular student");
System.out.println("5. View Details of all student");
System.out.println("6. Exit");
choice=k.getInt("Enter your choice: ");
System.out.println();
if(choice==1)
{
try
{
String m=s.addStudent();
}catch(Exception e)
{
System.out.println(e.getMessage()+"\n\n");
}
}
else if(choice==2)
{
try
{
s.deleteStudent();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else if(choice==3)
{
try
{
s.updateStudent();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else if(choice==4)
{
try
{
s.viewParticular();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else if(choice==5)
{
try
{
s.viewAll();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
else if(choice==6)
{
System.out.println("Thanks for using application\nCredits-:Tanishq Rawat");
System.exit(0);
}
else
{
System.out.println("Invalid Choice, Try again");
continue;
}
}
}
}
