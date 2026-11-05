public class Patient extends Thread {

    private int patientId;
    private WaitingRoom waitingRoom;
    private boolean treated = false;

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        try {
            Logger.log("P " + patientId + " arrived.");

            boolean entered = waitingRoom.tryEnterWaitingRoom(this);

            if (!entered) {
                waitingRoom.enterWaitingRoom(this);
            }

            synchronized (this) {
                while (!treated) {
                    wait();
                }
            }

            Logger.log("P" + patientId + " left.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void markTreated() {
        treated = true;
        notifyAll();
    }

    public int getPatientId() {
        return patientId;
    }
}