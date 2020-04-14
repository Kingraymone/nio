public class Threadtest11 implements Runnable {
    public static volatile int i = 0;
    private Object tt1;
    private Object tt2;

    public Threadtest11(Object tt1, Object tt2) {
        this.tt1 = tt1;
        this.tt2 = tt2;
    }

    @Override
    public void run() {
        synchronized (tt1) {
            i = i + 1;
            System.out.println(i + Thread.currentThread().getName());
            try {
                tt1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tt2.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object t1 = new Object();
        Object t2 = new Object();
        int t = 0;
        while (t < 100) {
            Thread threadtest1 = new Thread(new Threadtest11(t1, t2), "奇数线程1");
            threadtest1.start();
            Thread threadtest2 = new Thread(new Threadtest11(t2, t1), "偶数线程2");
            threadtest2.start();
            t++;
        }
    }
}

