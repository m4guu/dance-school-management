package models;

public class Student extends Person {

  private static int nextId = 1;
  private final int studentId;

  public Student(String name, int age) {
    super(name, age);
    this.studentId = nextId++;
  }

  public int getStudentId() {
    return studentId;
  }

  public static void resetNextId() {
    nextId = 1;
  }
}
