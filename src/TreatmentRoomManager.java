public class TreatmentRoomManager {
    private static final int ROOM_COUNT = 2;
    private final Semaphore availableRooms = new Semaphore(ROOM_COUNT);

    public boolean acquireRoom(long timeOut)
            throws InterruptedException {
        return availableRooms.tryP(timeOut);
    }

    // Release the treatment room after treatment is done
    public void releaseRoom() {
        availableRooms.V();
    }
}