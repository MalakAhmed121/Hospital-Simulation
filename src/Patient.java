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
            System.out.println("P" + patientId + " arrived.");

            boolean entered = waitingRoom.tryEnterWaitingRoom(this);

            if (!entered) {
                System.out.println("The waiting room is full.");
                System.out.println("P" + patientId +
                        " is waiting outside for an available seat.");

                waitingRoom.enterWaitingRoom(this);
            }

            System.out.println("P" + patientId +
                    " entered the waiting room.");

            synchronized (this) {
                while (!treated) {
                    wait();
                }
            }

            System.out.println("P" + patientId + " left.");

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