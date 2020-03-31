package schutzengel.com.safelifemonitor.Communications.SMSClient;

import schutzengel.com.safelifemonitor.Core.DateTime.Converter;

public class Message {
    private String subscriberNumber = "";
    private String content = "";
    private long timestamp = 0;

    public enum Exception {
        Undefined,
        Success,
        SendFailure,
        ReceiveFailure,
        ParseFailure
    }
    private Exception exception = Exception.Undefined;

    public Message(String subscriberNumber, String content) {
        this.exception = Exception.Undefined;
        this.subscriberNumber = subscriberNumber;
        this.content = content;
        this.timestamp = Converter.getEpochTimestam();
    }

    public Message(Exception exception, String subscriberNumber, String content) {
        this.exception = exception;
        this.subscriberNumber = subscriberNumber;
        this.content = content;
        this.timestamp = Converter.getEpochTimestam();
    }

    public String getSubscriberNumber() {
        return this.subscriberNumber;
    }

    public String getContent() {
        return this.content;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Exception getException() {
        return this.exception;
    }
}