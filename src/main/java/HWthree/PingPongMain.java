package HWthree;

public class PingPongMain {
    public static void main(String[] args) {
        PingPongGame ping = new PingPongGame();
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    ping.play("Ping");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    ping.play("Pong");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
    }
}

