public class Hospital {

    private WaitingRoom waitingRoom;
    private TreatmentRoomManager treatmentRoomManager;

    private int totalPatients;
    private int treatedPatients;
    private int totalDoctors;
    private int waitingRoomCapacity;

    private Doctor[] doctors;
    private Patient[] patients;

    public Hospital(int capacity, int doctorCount, int patientCount) {

        if (capacity <= 0 || doctorCount <= 0 || patientCount <= 0) {
            throw new IllegalArgumentException("Invalid input values.");
        }

        this.totalPatients = patientCount;
        this.totalDoctors = doctorCount;
        this.waitingRoomCapacity = capacity;

        initializeResources();
        initializeDoctors();
        initializePatients();
    }

    private void initializeResources() {
       waitingRoom = new WaitingRoom(waitingRoomCapacity);
       treatmentRoomManager = new TreatmentRoomManager(2);
    }

   private void initializeDoctors(){};

   private void initializePatients(){
    patients=new Patient[totalPatients];
    for(int i=0;i<totalPatients;i++){
        patients[i]=new Patient(i+1,waitingRoom);
    }

   };

   public void startSimulation() { }

}