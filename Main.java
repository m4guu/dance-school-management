import java.time.LocalDateTime;
import java.util.List;
import models.*;
import shared.*;

public class Main {

  public static void main(String[] args) {
    // Define instructors
    Instructor instructor1 = new Instructor("John Doe", 30);

    // Create a LocalDateTime object for schedule date and time
    LocalDateTime scheduleDateTime = LocalDateTime.of(2024, 4, 10, 16, 0); // Year, Month, Day, Hour, Minute
    // Get predefined rooms
    List<Room> predefinedRooms = Course.getPredefinedRooms();

    Course course1 = new Course(
      "Ballet Basics",
      instructor1,
      scheduleDateTime,
      Dance.BALLET,
      2,
      60,
      predefinedRooms.get(0)
    );

    // Create students
    Student student1 = new Student("Alice", 20);
    Student student2 = new Student("Bob", 25);
    Student student3 = new Student("Charlie", 22); // This student won't be added due to maximum size limit

    // Add students to the course
    course1.addStudent(student1);
    course1.addStudent(student2);
    course1.addStudent(student3); // This student won't be added due to maximum size limit

    // Print course details before removing student
    System.out.println("Course details before removing student:");
    printCourseDetails(course1);

    // Remove a student
    course1.removeStudent(student2);

    // Print course details after removing student
    System.out.println("\nCourse details after removing student:");
    printCourseDetails(course1);
  }

  private static void printCourseDetails(Course course) {
    System.out.println("Course Name: " + course.getCourseName());
    System.out.println("Instructor: " + course.getInstructor().getName());
    System.out.println("Schedule: " + course.getSchedule());
    System.out.println("Dance Type: " + course.getDanceType());
    System.out.println("Max Students: " + course.getMaxStudents());
    System.out.println("Max Students: " + course.getRoom().getRoomNumber());
    System.out.println("Enrolled Students: ");
    printEnrolledStudents(course.getEnrolledStudents());
  }

  private static void printEnrolledStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("No students enrolled.");
    } else {
      for (Student student : students) {
        System.out.println("- " + student.getName());
      }
    }
  }
}
