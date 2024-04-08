import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.*;
import services.*;
import shared.*;

// public class CourseSchedulerGUI extends Application {

public class CourseSchedulerGUI extends Application {

  private ScheduleService scheduleService;

  @Override
  public void start(Stage primaryStage) {
    // Initialize ScheduleService
    scheduleService = new ScheduleService();

    // Create predefined rooms
    List<Room> predefinedRooms = Course.getPredefinedRooms();

    // Create UI components
    Label titleLabel = new Label("Course Scheduler");
    titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

    // Course details form
    Label courseLabel = new Label("Course Name:");
    TextField courseTextField = new TextField();
    Label instructorLabel = new Label("Instructor:");
    ComboBox<Instructor> instructorComboBox = new ComboBox<>();
    instructorComboBox
      .getItems()
      .addAll(new Instructor("John Doe", 30), new Instructor("Jane Smith", 35));
    Label scheduleLabel = new Label("Schedule:");
    DatePicker scheduleDatePicker = new DatePicker();
    Label timeLabel = new Label("Time:");
    Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 0);
    Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
    Label danceLabel = new Label("Dance Type:");
    ComboBox<Dance> danceComboBox = new ComboBox<>();
    danceComboBox.getItems().addAll(Dance.values());
    Label maxStudentsLabel = new Label("Max Students:");
    Spinner<Integer> maxStudentsSpinner = new Spinner<>(
      1,
      Integer.MAX_VALUE,
      1
    );
    Label durationLabel = new Label("Duration (minutes):");
    Spinner<Integer> durationSpinner = new Spinner<>(1, Integer.MAX_VALUE, 60);
    Label roomLabel = new Label("Room:");
    ComboBox<Room> roomComboBox = new ComboBox<>();
    roomComboBox.getItems().addAll(predefinedRooms);

    Button addButton = new Button("Add Course");
    Button refreshButton = new Button("Refresh");

    // Student details form
    Label studentNameLabel = new Label("Student Name:");
    TextField studentNameTextField = new TextField();
    Label studentAgeLabel = new Label("Student Age:");
    Spinner<Integer> studentAgeSpinner = new Spinner<>(1, 150, 18);
    Label selectCourseLabel = new Label("Select Course:");
    ComboBox<Course> courseComboBox = new ComboBox<>();
    courseComboBox.getItems().addAll(scheduleService.getScheduledCourses());
    Button addStudentButton = new Button("Add Student");

    // Course list
    TextArea courseListTextArea = new TextArea();
    courseListTextArea.setEditable(false);

    // Add event handler for Add Course button
    addButton.setOnAction(event -> {
      // Extract data from UI components
      String courseName = courseTextField.getText();
      Instructor instructor = instructorComboBox.getValue();
      LocalDate scheduleDate = scheduleDatePicker.getValue();
      int hour = hourSpinner.getValue();
      int minute = minuteSpinner.getValue();
      Dance danceType = danceComboBox.getValue();
      int maxStudents = maxStudentsSpinner.getValue();
      int duration = durationSpinner.getValue();
      Room room = roomComboBox.getValue();

      // Create scheduleDateTime
      LocalDateTime scheduleDateTime = LocalDateTime.of(
        scheduleDate,
        java.time.LocalTime.of(hour, minute)
      );

      // Create new course
      Course newCourse = new Course(
        courseName,
        instructor,
        scheduleDateTime,
        danceType,
        maxStudents,
        duration,
        room
      );

      // Add course using ScheduleService
      scheduleService.addCourse(newCourse);

      // Update course list display
      refreshCourseList(courseListTextArea);
    });

    // Add event handler for Add Student button
    addStudentButton.setOnAction(event -> {
      // Extract student data
      String studentName = studentNameTextField.getText();
      int studentAge = studentAgeSpinner.getValue();

      // Get selected course
      Course selectedCourse = courseComboBox.getValue();

      // Add student to course
      if (selectedCourse != null) {
        selectedCourse.addStudent(new Student(studentName, studentAge));
        refreshCourseList(courseListTextArea);
      } else {
        // Show error message if no course is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a course to add the student to.");
        alert.showAndWait();
      }
    });

    // Add event handler for Refresh button
    refreshButton.setOnAction(event -> refreshCourseList(courseListTextArea));

    // Create layout
    VBox root = new VBox(10);
    root.setPadding(new Insets(10));
    root
      .getChildren()
      .addAll(
        titleLabel,
        new Separator(),
        new HBox(10, courseLabel, courseTextField),
        new HBox(10, instructorLabel, instructorComboBox),
        new HBox(
          10,
          scheduleLabel,
          scheduleDatePicker,
          timeLabel,
          hourSpinner,
          new Label(":"),
          minuteSpinner
        ),
        new HBox(10, danceLabel, danceComboBox),
        new HBox(10, maxStudentsLabel, maxStudentsSpinner),
        new HBox(10, durationLabel, durationSpinner),
        new HBox(10, roomLabel, roomComboBox),
        addButton,
        refreshButton,
        new Separator(),
        new Label("Student Details:"),
        new HBox(10, studentNameLabel, studentNameTextField),
        new HBox(10, studentAgeLabel, studentAgeSpinner),
        new HBox(10, selectCourseLabel, courseComboBox),
        addStudentButton,
        new Separator(),
        new Label("Scheduled Courses:"),
        courseListTextArea
      );

    // Set up scene
    Scene scene = new Scene(root, 600, 600);

    // Set up stage
    primaryStage.setScene(scene);
    primaryStage.setTitle("Course Scheduler");
    primaryStage.show();
  }

  private void refreshCourseList(TextArea courseListTextArea) {
    // Clear existing text
    courseListTextArea.clear();

    // Append scheduled courses to the text area
    List<Course> scheduledCourses = scheduleService.getScheduledCourses();
    for (Course course : scheduledCourses) {
      courseListTextArea.appendText(course.getCourseName() + "\n");
      courseListTextArea.appendText(
        "Instructor: " + course.getInstructor().getName() + "\n"
      );
      courseListTextArea.appendText("Schedule: " + course.getSchedule() + "\n");
      courseListTextArea.appendText(
        "Dance Type: " + course.getDanceType() + "\n"
      );
      courseListTextArea.appendText(
        "Max Students: " + course.getMaxStudents() + "\n"
      );
      courseListTextArea.appendText(
        "Room: " + course.getRoom().getRoomNumber() + "\n"
      );
      courseListTextArea.appendText("Enrolled Students: " + "\n");
      for (Student student : course.getEnrolledStudents()) {
        courseListTextArea.appendText("- " + student.getName() + "\n");
      }
      courseListTextArea.appendText("\n");
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
