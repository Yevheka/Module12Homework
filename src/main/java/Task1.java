public class Task1 {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
                while(true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Минуло 5 секунд");
                }
        });

        thread.start();
        stopwatch();
    }

    static void stopwatch() {
        int secondsAfterStarts = 0;
        while(true) {
            System.out.println(secondsAfterStarts);
            secondsAfterStarts++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
