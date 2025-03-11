import java.util.concurrent.Semaphore;

class FizzBuzz {
    private final int n;
    private int current = 1;
    private final Semaphore semFizz = new Semaphore(0);
    private final Semaphore semBuzz = new Semaphore(0);
    private final Semaphore semFizzBuzz = new Semaphore(0);
    private final Semaphore semNumber = new Semaphore(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz() {
        while (true) {
            try {
                semFizz.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (current > n) {
                return;
            }
            System.out.print(", fizz");
            next();
        }
    }

    public void buzz() {
        while (true) {
            try {
                semBuzz.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (current > n) {
                return;
            }
            System.out.print(", buzz");
            next();
        }
    }

    public void fizzbuzz() {
        while (true) {
            try {
                semFizzBuzz.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (current > n) {
                return;
            }
            System.out.print(", fizzbuzz");
            next();
        }
    }

    public void number() {
        while (true) {
            try {
                semNumber.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (current > n) {
                return;
            }
            if(current == 1){
                System.out.print(current);
            } else {
                System.out.print(", " + current);
            }
            next();
        }
    }

    private synchronized void next() {
        current++;
        if (current > n) {
            semFizz.release();
            semBuzz.release();
            semFizzBuzz.release();
            semNumber.release();
        } else if (current % 3 == 0 && current % 5 == 0) {
            semFizzBuzz.release();
        } else if (current % 3 == 0) {
            semFizz.release();
        } else if (current % 5 == 0) {
            semBuzz.release();
        } else {
            semNumber.release();
        }
    }

    public void run() {
        Thread a = new Thread(this::fizz);
        Thread b = new Thread(this::buzz);
        Thread c = new Thread(this::fizzbuzz);
        Thread d = new Thread(this::number);

        a.start();
        b.start();
        c.start();
        d.start();

        try {
            a.join();
            b.join();
            c.join();
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzbuzz = new FizzBuzz(n);
        fizzbuzz.run();
    }
}
