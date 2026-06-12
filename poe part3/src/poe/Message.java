package poe;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;

    public Message(String messageID, String recipient, String messageText, String messageHash) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = messageHash;
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; 
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    @Override
    public String toString() {
        return "Message ID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText;
    }
}
