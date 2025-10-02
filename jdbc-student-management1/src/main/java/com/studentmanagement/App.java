package com.studentmanagement;

import java.util.List;
import java.util.Scanner;
public class App {
	private static final Scanner sc = new Scanner(System.in);
  private static final StudentDAO dao = new StudentDAO();

  public static void main(String[] args) {
    System.out.println("=== JDBC Student Management ===");
    DBConnection.initializeDatabase();
    boolean run=true;
    while(run) {
      System.out.println("\n1:Add 2:List 3:Update 4:Delete 5:Exit");
      System.out.print("Choice: ");
      int c = sc.nextInt(); sc.nextLine();
      switch(c) {
        case 1 -> add();
        case 2 -> list();
        case 3 -> update();
        case 4 -> delete();
        case 5 -> run=false;
        default -> System.out.println("Invalid");
      }
    }
    sc.close();
  }

  private static void add() {
    System.out.print("Name: "); String n=sc.nextLine();
    System.out.print("Age: "); int a=sc.nextInt(); sc.nextLine();
    if(dao.addStudent(new Student(0,n,a))) System.out.println("Added");
  }

  private static void list() {
    List<Student> l=dao.getAllStudents();
    if(l.isEmpty()) System.out.println("No data");
    else l.forEach(System.out::println);
  }

  private static void update() {
    System.out.print("ID: "); int id=sc.nextInt(); sc.nextLine();
    System.out.print("Name: "); String n=sc.nextLine();
    System.out.print("Age: "); int a=sc.nextInt(); sc.nextLine();
    if(dao.updateStudent(new Student(id,n,a))) System.out.println("Updated");
  }

  private static void delete() {
    System.out.print("ID: "); int id=sc.nextInt(); sc.nextLine();
    if(dao.deleteStudent(id)) System.out.println("Deleted");
  }
}
