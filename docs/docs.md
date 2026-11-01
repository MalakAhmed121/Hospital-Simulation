 ## Assignment 1. This file is for class names, method names, and rules

## 1. Ground rules

1. **Use only what the labs taught.** `synchronized`, `wait()`, `notify()/notifyAll()`, `Thread`, `start()`, `join()`, `sleep()`, `interrupt()`, and our own `Semaphore`. No `java.util.concurrent` (no `java.util.concurrent.Semaphore`, `BlockingQueue`, `ReentrantLock`, `ExecutorService`). The assignment says to implement the Semaphore ourselves and use it.
2. **Lab naming for semaphore operations:** `P()` (wait/acquire) and `V()` (signal/release). The class is called `Semaphore` (the assignment's name). The custom timed method is `tryAcquire(long timeoutMillis)`.
3. **No package.** All files are in `src/` in the default package, and each file name equals its class name exactly (`Semaphore.java`, capital S). This keeps the ZIP and compile simple.
4. **All printing goes through `Logger.log(...)`.** Nobody calls `System.out.println` directly except `Logger` (and `Hospital` for the input prompts).
5. **Never `sleep()` inside `synchronized` or while holding any semaphore that others need.** Treatment `sleep` happens *after* the room is acquired, outside any `synchronized` block.
6. **Every `wait()` is inside a `while`**, never an `if`.
7. **No nested locks.** No code takes lock A and then lock B. (This is how we guarantee no deadlock.)
8. **The waiting room and the treatment rooms never share a lock or semaphore.** They are synchronized independently (explicit assignment requirement).
9. **Only release what you acquired.** A doctor calls `releaseRoom()` only if `acquireRoom(...)` returned `true`.
10. Everybody must be able to explain **every** class (assignment rule 6).

---
## 2. Lifecycle and shutdown

```
Patient:  arrived -> enter (maybe waits outside) -> [doctor treats] -> left -> done.V()
Doctor:   take -> try room -> (treat -> release -> available -> p.leave()) or (send back -> available) -> repeat
Hospital: start doctors, start patients -> done.P() x patients -> "All patients have been treated."
          -> interrupt doctors -> join doctors and patients -> "Simulation completed."
```

A patient can only leave after being treated, so `done` reaches the patient count exactly when every patient has been treated.

---

## 3. Who owns what

| Member | Files | Notes |
|---|---|---|
| **M2** | `Semaphore`, `TreatmentRoomManager` | **First.** Everything else depends on `Semaphore`. Finish within 2 days. |
| **M3** | `Patient`, `WaitingRoom` | `Patient` can start immediately. `WaitingRoom` needs M2's `Semaphore`. |
| **M1** | `Logger`, `Hospital` | `Logger` and input reading on day 1, then wire in the real constructors. |
| **M4** | `Doctor` | Skeleton on day 1, completes when M2, M3 and M1 are ready. |
| **M5** | repo, this file, stubs, `run_tests.sh`, reviews, integration | Reviews every PR, integrates on day 5. |
