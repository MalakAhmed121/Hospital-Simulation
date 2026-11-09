import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {

    private Queue<Patient> patients;

    private Semaphore empty;
    private Semaphore full;

    public WaitingRoom(int capacity) {
        patients = new LinkedList<>();
        if (capacity <= 0) {
            throw new IllegalArgumentException("Waiting room capacity must be greater than zero.");
        }
        empty = new Semaphore(capacity);
        full = new Semaphore(0);
    }

    public boolean tryEnterWaitingRoom(Patient patient) throws InterruptedException {
        synchronized (patients) {
            if (!empty.tryP(0)) {
                Logger.log("The waiting room is full.");
                Logger.log("P" + patient.getPatientId()
                        + " is waiting outside for an available seat.");
                return false;
            }

            patients.add(patient);
            Logger.log("P" + patient.getPatientId() + " entered the waiting room.");
        }

        full.V();
        return true;
    }

    public void enterWaitingRoom(Patient patient) throws InterruptedException {
        empty.P();
        addPatient(patient);
    }

    private void addPatient(Patient patient) {
        synchronized (patients) {
            patients.add(patient);
            Logger.log("P" + patient.getPatientId() + " entered the waiting room.");
        }
        full.V();
    }

    public Patient takePatient(int doctorId) throws InterruptedException {
        full.P();

        Patient patient;

        synchronized (patients) {
            patient = patients.remove();
            Logger.log("Doctor" + doctorId + " is assigned to patient"
                    + patient.getPatientId() + ".");
            Logger.log("P" + patient.getPatientId() + " left the waiting room.");
        }

        empty.V();
        return patient;
    }

    public void returnPatient(Patient patient) throws InterruptedException {
        if (!tryEnterWaitingRoom(patient)) {
            enterWaitingRoom(patient);
        }
    }
}