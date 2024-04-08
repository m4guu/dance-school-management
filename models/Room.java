package models;

import java.util.ArrayList;
import java.util.List;

public class Room {

  private final String roomNumber;

  // Constructor
  public Room(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  // Getter
  public String getRoomNumber() {
    return roomNumber;
  }

  // Static method to retrieve predefined rooms
  public static List<Room> getPredefinedRooms() {
    List<Room> rooms = new ArrayList<>();
    rooms.add(new Room("101"));
    rooms.add(new Room("102"));
    rooms.add(new Room("103"));
    // Add more predefined rooms as needed
    return rooms;
  }
}
