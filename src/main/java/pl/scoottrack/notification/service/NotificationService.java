package pl.scoottrack.notification.service;

public interface NotificationService {
    void sendNotification(String userId, String message);
}