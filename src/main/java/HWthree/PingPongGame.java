package HWthree;


public class PingPongGame {
    private String message = "Pong";

    public synchronized void play(String text) throws InterruptedException {
        while (text.equals(message)) {
            this.wait();
        }
        System.out.println(text);
        Thread.sleep(300);
        message = text;
        this.notifyAll();
    }
}
