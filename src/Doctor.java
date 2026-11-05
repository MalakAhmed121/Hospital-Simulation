public class Doctor extends Thread{
    private static final int ROOM_ACQUIRE_TIMEOUT = 3000; // Timeout in milliseconds for acquiring a treatment room
    private static final int MIN_TREATMENT_TIME = 1000; // Simulated treatment time in milliseconds
    private static final int MAX_TREATMENT_TIME = 5000; // Simulated treatment time in milliseconds
    private int doctorId;
    private WaitingRoom waitingRoom;
    private TreatmentRoomManager treatmentRoomManager;

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRoomManager treatmentRoomManager) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRoomManager = treatmentRoomManager;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Patient patient = waitingRoom.takePatient(doctorId);

                if (patient != null) {
                    Logger.log("Doctor " + doctorId + " is trying to acquire a treatment room.");

                    boolean roomAcquired = treatmentRoomManager.acquireRoom(ROOM_ACQUIRE_TIMEOUT);
                    if (!roomAcquired) {
                        Logger.log("Doctor " + doctorId + " could not acquire a treatment room.");
                        Logger.log("Doctor " + doctorId + " is sending " + patient.getPatientId() + " back to the waiting room.");
                        if (!waitingRoom.tryEnterWaitingRoom(patient)) {
                            waitingRoom.enterWaitingRoom(patient);
                        }
                        Logger.log("Doctor " + doctorId + " is now available.");
                        continue;
                    }

                    Logger.log("Doctor " + doctorId + " acquired a treatment room.");
                    Logger.log("Doctor " + doctorId + " begins treating " + patient.getPatientId() + ".");

                    boolean treatmentFinished = false;
                    try {
                        int treatmentTime = (int) (Math.random() * (MAX_TREATMENT_TIME - MIN_TREATMENT_TIME + 1)) + MIN_TREATMENT_TIME;
                        Thread.sleep(treatmentTime); // Simulate treatment time
                        treatmentFinished = true;
                        Logger.log("Doctor " + doctorId + " finished treating " + patient.getPatientId() + ".");
                    } finally {
                        if (!treatmentFinished) {
                            Logger.log("Doctor " + doctorId + " treatment for " + patient.getPatientId() + " was interrupted.");
                        }
                        treatmentRoomManager.releaseRoom();
                        Logger.log("Doctor " + doctorId + " released the treatment room.");
                    }

                    Logger.log("Doctor " + doctorId + " is now available.");
                    patient.markTreated();
                } else {
                    Logger.log("Doctor " + doctorId + " found no patients in the waiting room.");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
}