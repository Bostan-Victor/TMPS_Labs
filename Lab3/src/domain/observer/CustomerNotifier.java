package domain.observer;

public class CustomerNotifier implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notifying Customer: " + message);
    }
}