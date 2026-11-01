
# Hospital Emergency Room Simulation

A Java-based Hospital Emergency Room (ER) simulation developed
for the Operating Systems course at Cairo University.

This project demonstrates the Producer-Consumer problem using
multithreading, semaphores, and bounded buffer synchronization.


## Project Overview

This project simulates a busy hospital emergency department
where multiple patients arrive and receive treatment from
available doctors.

The simulation consists of:
- Patients arriving at the hospital.
- A bounded waiting room with limited capacity.
- Multiple doctors working concurrently.
- Two shared treatment rooms.
- Synchronization between patients, doctors, and shared resources.

The system uses Java threads and semaphores to coordinate
the activities of patients and doctors while preventing
race conditions and deadlocks.

## Repo Layout
os-hospital-er/
├── README.md              
├── .gitignore             
├── src/
│   ├── Hospital.java
│   ├── Semaphore.java
│   ├── TreatmentRoomManager.java
│   ├── WaitingRoom.java
│   ├── Patient.java
│   ├── Doctor.java
│   └── Logger.java
└── docs/
    └── docs.md    
    
## Features

- Multithreaded patient and doctor simulation.
- Bounded buffer implementation for the waiting room.
- Semaphore-based synchronization.
- Limited waiting room capacity.
- Two shared treatment rooms.
- Timed treatment room acquisition.
- Patients waiting outside when the waiting room is full.
- Patients returned to the waiting room when treatment
  rooms are unavailable.
- Centralized synchronized logging.
- Graceful shutdown after all patients are treated.

## Technologies Used

- Java
- Java Threads
- Semaphores
- Producer-Consumer Problem
- Bounded Buffer
- Synchronization


## System Components

### Hospital
Responsible for reading user input, validating parameters,
creating shared resources, starting threads, and managing
the simulation lifecycle.

### Semaphore
A custom semaphore implementation that supports acquiring,
releasing, and attempting to acquire a resource within
a specified timeout.

### Patient
Represents a patient arriving at the hospital, entering
the waiting room, waiting for treatment, and leaving
after treatment is completed.

### Doctor
Represents a doctor who waits for available patients,
acquires a treatment room, performs treatment, and
becomes available for another patient.

### WaitingRoom
A bounded buffer that manages waiting patients using
semaphores and producer-consumer synchronization.

### TreatmentRoomManager
Manages the two shared treatment rooms independently
from the waiting room.

## How the Simulation Works

1. The user enters the waiting room capacity, number
   of doctors, and number of patients.
2. Patients arrive and attempt to enter the waiting room.
3. If the waiting room is full, patients wait outside
   until a seat becomes available.
4. Doctors wait until patients become available.
5. Each doctor attempts to acquire a treatment room
   within three seconds.
6. If a treatment room is acquired, treatment begins.
7. If no treatment room becomes available in time,
   the patient is returned to the waiting room.
8. After treatment, the doctor releases the room and
   the patient leaves the hospital.
9. The simulation terminates after all patients have
   been treated.

## Sample Input

Waiting room capacity: 2

Number of doctors: 3

Number of patients: 5

## How to Run

1. Clone this repository.
2. Open the project in Java IDE.
3. Make sure a compatible Java JDK is installed.
4. Compile the Java source files.
5. Run the Main class.
6. Enter the required simulation parameters.

## Testing

The simulation is tested using different configurations,
including:

- Waiting room capacity: 2, doctors: 3, patients: 5
- Waiting room capacity: 1, doctors: 5, patients: 20
- Multiple repeated simulation runs

Testing focuses on synchronization correctness,
resource management, deadlock prevention, and
proper thread termination.

## Team Members

| Member | Responsibility |
|--------|----------------|
| Member 1 | Hospital, Main, and Logging |
| Member 2 | Semaphore and TreatmentRoomManager |
| Member 3 | Patient and WaitingRoom |
| Member 4 | Doctor |
| Member 5 | Integration, Synchronization Review, and Testing |

## Final Output

The simulation terminates after all patients have
received treatment.

Expected final messages:

All patients have been treated.

Simulation completed.

## Academic Integrity

This project was developed as part of the CS241 / SCS241
Operating Systems assignment at Cairo University.

All team members are expected to understand the complete
implementation and synchronization design.

