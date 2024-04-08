package models;

public class Instructor extends Person {

  private static int nextId = 1;
  private final int instructorId;

  public Instructor(String name, int age) {
    super(name, age);
    this.instructorId = nextId++;
  }

  public int getInstructorId() {
    return instructorId;
  }
}
