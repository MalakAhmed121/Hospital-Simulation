import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // get simulation information from the user.
        System.out.print("Enter waiting room capacity: ");
        int capacity = input.nextInt();

        System.out.print("Enter number of doctors: ");
        int doctorsCount = input.nextInt();

        System.out.print("Enter number of patients: ");
        int patientsCount = input.nextInt();

        // create the shared resources.
        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        TreatmentRoomManager treatmentRoomManager = new TreatmentRoomManager();

        Logger.log("Patients arriving:");
        String list = "";
        for (int i = 1; i <= patientsCount; i++) {
            list += "P" + i + " ";
        }
        Logger.log(list.trim());
        // create and start doctors.
        Doctor[] doctors = new Doctor[doctorsCount];
        for (int i = 0; i < doctorsCount; i++) {
            doctors[i] = new Doctor(
                    i + 1,
                    waitingRoom,
                    treatmentRoomManager
            );
            doctors[i].start();
        }

        // create and start patients.
        Patient[] patients = new Patient[patientsCount];
        for (int i = 0; i < patientsCount; i++) {
            patients[i] = new Patient(
                    i + 1,
                    waitingRoom
            );
            patients[i].start();
        }

        try {
            // wait until all patients finish.
            for (int i = 0; i < patientsCount; i++) {
                patients[i].join();
            }
            Logger.log("All patients have been treated.");

            // stop all doctors.
            for (int i = 0; i < doctorsCount; i++) {
                doctors[i].interrupt();
            }
            // wait until all doctors stop.
            for (int i = 0; i < doctorsCount; i++) {
                doctors[i].join();
            }

            Logger.log("Simulation completed.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log("Simulation interrupted.");
        }
        input.close();
    }
}