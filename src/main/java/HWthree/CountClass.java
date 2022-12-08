package HWthree;

import java.util.concurrent.locks.ReentrantLock;

public class CountClass {
        private ReentrantLock lock = new ReentrantLock();
        private int count = 0;

        public int getCount() {
            return count;
        }

        private void increment() {
            try {
                lock.lock();
                count++;
            } finally {
                lock.unlock();
            }

        }

        private void decrement() {
            try {
                lock.lock();
                count--;
            } finally {
                lock.unlock();
            }
        }
}