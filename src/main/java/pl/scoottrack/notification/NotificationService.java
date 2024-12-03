package pl.scoottrack.notification;

public interface NotificationService {
    void sendNotification(String userId, String message);
}