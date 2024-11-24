package domain.observer;

public class OrderLogger implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Logging Order Status Update: " + message);
    }
}