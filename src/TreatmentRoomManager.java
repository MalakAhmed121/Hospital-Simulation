public class TreatmentRoomManager {
    private final Semaphore roomSemaphore;

    public TreatmentRoomManager(int totalRooms) {
        this.roomSemaphore = new Semaphore(totalRooms);
    }

    public boolean acquireRoom(long timeOut)
            throws InterruptedException {
        return roomSemaphore.tryP(timeOut);
    }

    // Release the treatment room after treatment is done
    public void releaseRoom() {
        roomSemaphore.V();
    }
}