public class Semaphore{
    private int value;

    public Semaphore(int initialValue){
        if(initialValue<0){
            throw new IllegalArgumentException("Initial value must be positive");
        }
        this.value = initialValue;
    }

    public synchronized void P()         //wait()
        throws  InterruptedException{
        while(value<=0) wait();
        value--;
}
    public synchronized void V(){        //release()
    value++;
    notifyAll();
    }

    public synchronized boolean tryP(long timeOut)
        throws  InterruptedException{
        long deadline = System.currentTimeMillis() + timeOut;

        while (value<=0){
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0){ return false;}

            wait(remaining);
        }
        value--;
        return true;
    }
}