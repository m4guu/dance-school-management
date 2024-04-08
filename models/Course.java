package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import shared.Dance;

public class Course {

  private final String courseName;
  private final Instructor instructor;
  private final LocalDateTime schedule;
  private final Dance danceType;
  private final int maxStudents;
  private final int duration;
  private final Room room;
  private final List<Student> enrolledStudents;

  public Course(
    String courseName,
    Instructor instructor,
    LocalDateTime schedule,
    Dance danceType,
    int maxStudents,
    int duration,
    Room room
  ) {
    this.courseName = courseName;
    this.instructor = instructor;
    this.schedule = schedule;
    this.danceType = danceType;
    this.maxStudents = maxStudents;
    this.duration = duration;
    this.room = room;
    this.enrolledStudents = new ArrayList<>();
  }

  public String getCourseName() {
    return courseName;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public LocalDateTime getSchedule() {
    return schedule;
  }

  public Dance getDanceType() {
    return danceType;
  }

  public int getMaxStudents() {
    return maxStudents;
  }

  public int getDuration() {
    return duration;
  }

  public Room getRoom() {
    return room;
  }

  // Method to get predefined rooms
  public static List<Room> getPredefinedRooms() {
    return Room.getPredefinedRooms();
  }

  public List<Student> getEnrolledStudents() {
    return enrolledStudents;
  }

  public boolean addStudent(Student student) {
    if (enrolledStudents.size() < maxStudents) {
      enrolledStudents.add(student);
      return true;
    } else {
      System.out.println(
        "Cannot add student to the course. Maximum number of students reached."
      );
      return false;
    }
  }

  public boolean removeStudent(Student student) {
    return enrolledStudents.remove(student);
  }
}
