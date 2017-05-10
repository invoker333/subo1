package com.mingli.toms;

public class RoomClient {
	public RoomClient(String roomId, String roomSize) {
		// TODO Auto-generated constructor stub
		this.roomId=Integer.parseInt(roomId);
		this.roomSize=Integer.parseInt(roomSize);
	}
	int roomId;
	int roomSize;
	int roomMax=2;
}
