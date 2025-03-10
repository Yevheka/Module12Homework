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

    public void fizz() throws InterruptedException {
        while (true) {
            semFizz.acquire();
            if (current > n) return;
            System.out.print("fizz, ");
            next();
        }
    }

    public void buzz() throws InterruptedException {
        while (true) {
            semBuzz.acquire();
            if (current > n) return;
            System.out.print("buzz, ");
            next();
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (true) {
            semFizzBuzz.acquire();
            if (current > n) return;
            System.out.print("fizzbuzz, ");
            next();
        }
    }

    public void number() throws InterruptedException {
        while (true) {
            semNumber.acquire();
            if (current > n) return;
            System.out.print(current + ", ");
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
        Thread t1 = new Thread(() -> {
            try {
                fizz();
            } catch (InterruptedException ignored) {}
        });
        Thread t2 = new Thread(() -> {
            try {
                buzz();
            } catch (InterruptedException ignored) {}
        });
        Thread t3 = new Thread(() -> {
            try {
                fizzbuzz();
            } catch (InterruptedException ignored) {}
        });
        Thread t4 = new Thread(() -> {
            try {
                number();
            } catch (InterruptedException ignored) {}
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int n = 3;
        FizzBuzz fizzbuzz = new FizzBuzz(n);
        fizzbuzz.run();
    }
}
