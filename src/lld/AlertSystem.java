package lld;

public class AlertSystem {
}

/**
 * Low-Level Design: Alert System
 * Designing an alert system requires careful consideration of its core responsibilities: consuming data, evaluating rules, and dispatching notifications. This system is a perfect complement to the centralized logging system we discussed, as it would likely consume processed logs from the LogAggregator to trigger alerts.
 * Here is a low-level design focusing on the core Java classes and their interactions.
 * Core Components and Classes
 * 1. The Alert Rule (AlertRule.java)
 * This class defines the structure of a single alert rule. It's a simple Plain Old Java Object (POJO) that a user or administrator would configure.
 * Attributes:
 * ruleId: A unique identifier for the rule (e.g., a UUID).
 * condition: A string or object representing the logic to be evaluated (e.g., "log.level == ERROR").
 * threshold: The value that, when exceeded, triggers the alert (e.g., 100 errors in 5 minutes).
 * notificationChannel: The channel to use for the notification (e.g., "email", "slack").
 * messageTemplate: The template for the alert message (e.g., "High error rate detected in ${appName}").
 * 2. The Rule Engine (RuleEngine.java)
 * This is the brain of the alerting system. It's responsible for evaluating incoming data against the set of defined rules.
 * It should be scalable and performant.
 * Attributes:
 * rules: A List or Map to hold AlertRule objects.
 * notificationService: An instance of a class that handles sending notifications.
 * Methods:
 * addRule(AlertRule rule): Adds a new rule to the engine.
 * process(LogMessage log): The main method that takes a log and evaluates it against all rules. If a rule's condition is met, it triggers an alert.
 * evaluateCondition(LogMessage log, AlertRule rule): A private helper method to parse the rule's condition and check if it's true for the given log message.
 * 3. The Notification Service (NotificationService.java)
 * This is an interface that defines the contract for sending notifications. Using an interface allows you to easily
 * add new notification channels without modifying the RuleEngine. This is an application of the Strategy design pattern.
 * Interface:
 * sendAlert(Alert alert): A method that takes an Alert object and sends it to the appropriate channel.
 * Concrete Implementations:
 * EmailNotificationService: Implements the NotificationService interface to send alerts via email.
 * SlackNotificationService: Implements the NotificationService interface to send alerts to a Slack channel.
 * SmsNotificationService: Implements the NotificationService interface to send alerts via SMS.
 * 4. The Alert (Alert.java)
 * This class represents a single, triggered alert event. It's the object that is passed to the NotificationService.
 * Attributes:
 * alertId: A unique identifier for the alert.
 * ruleId: The ID of the rule that triggered the alert.
 * timestamp: The time the alert was triggered.
 * message: The final formatted message to be sent.
 * payload: The raw data that triggered the alert (e.g., the LogMessage object).
 * End-to-End Class-Level Flow
 * The flow of a log message through this system to a final alert is a clear sequence of object interactions.
 * A LogMessage is received by the Data Consumer (not shown in the code, but conceptually a part of the system that would read from the LogAggregator).
 * The Data Consumer passes the LogMessage to the RuleEngine's process() method.
 * The RuleEngine iterates through its list of AlertRule objects. For each rule, it calls a helper method to evaluate the condition against the LogMessage.
 * If a condition is met, the RuleEngine creates a new Alert object, populating it with details from the LogMessage and the triggered AlertRule.
 * The RuleEngine then uses its notificationService instance to send the Alert object. The specific implementation (e.g., EmailNotificationService or SlackNotificationService) is determined by the notificationChannel in the AlertRule.
 */
