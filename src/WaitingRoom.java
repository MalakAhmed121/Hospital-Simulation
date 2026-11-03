import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {

    private Queue<Patient> patients;

    private Semaphore empty;
    private Semaphore full;

    public WaitingRoom(int capacity) {
        patients = new LinkedList<>();

        empty = new Semaphore(capacity);
        full = new Semaphore(0);
    }

    public boolean tryEnterWaitingRoom(Patient patient)
            throws InterruptedException {

        if (!empty.tryP(0)) {
            return false;
        }

        addPatient(patient);
        return true;
    }

    public void enterWaitingRoom(Patient patient)
            throws InterruptedException {

        empty.P();
        addPatient(patient);
    }

    private void addPatient(Patient patient) {

        synchronized (patients) {
            patients.add(patient);
        }

        full.V();
    }

    public Patient takePatient()
    throws InterruptedException {

        full.P();

        Patient patient;

        synchronized (patients) {
            patient = patients.remove();
        }

        empty.V();

        return patient;
    }

    public void returnPatient(Patient patient)
            throws InterruptedException {

        if (!tryEnterWaitingRoom(patient)) {
            Logger.log("The waiting room is full.");
            Logger.log("P" + patient.getPatientId()
                    + " is waiting outside for an available seat.");

            enterWaitingRoom(patient);
        }

        Logger.log("P" + patient.getPatientId()
                + " entered the waiting room.");
    }
}