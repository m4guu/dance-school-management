package services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import models.Course;

public class ScheduleService {

  private List<Course> scheduledCourses;

  public ScheduleService() {
    this.scheduledCourses = new ArrayList<>();
  }

  public void addCourse(Course newCourse) {
    // Calculate end time of new course
    LocalDateTime newCourseEndTime = newCourse
      .getSchedule()
      .plus(newCourse.getDuration(), ChronoUnit.MINUTES);

    // Check for time and room collisions
    for (Course course : scheduledCourses) {
      LocalDateTime existingCourseEndTime = course
        .getSchedule()
        .plus(course.getDuration(), ChronoUnit.MINUTES);

      if (
        newCourse.getSchedule().isBefore(existingCourseEndTime) &&
        newCourseEndTime.isAfter(course.getSchedule()) &&
        course.getRoom().equals(newCourse.getRoom())
      ) {
        System.out.println(
          "Cannot add course. Time and room collision detected."
        );
        return;
      }
    }

    // No collision, add the course
    scheduledCourses.add(newCourse);
    System.out.println("Course scheduled successfully.");
  }

  // Method to get the list of scheduled courses
  public List<Course> getScheduledCourses() {
    return scheduledCourses;
  }
}
