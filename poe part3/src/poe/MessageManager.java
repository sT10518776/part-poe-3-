package poe;

import java.util.Random;
import java.util.Scanner;

public class MessageManager {

    private Message[] sentMessages = new Message[100];
    private Message[] disregardedMessages = new Message[100]
    private Message[] storedMessages = new Message[100]

    private String[] messageIDs = new String[100];
    private String[] messageHashes = new String[100];

    private int sentCount = 0;
    private int disregardCount = 0;
    private int storedCount = 0;
    private int totalMessages = 0;

    private final Scanner scanner;

    public MessageManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startMessageSession() {

        int option = 0;

        while (option != 4) {

            System.out.println("\n===== QUICKCHAT =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    sendMultipleMessages();
                    break;

                case 2:
                    showSentMessages();
                    break;

                case 3:
                    storedMessagesMenu();
                    break;

                case 4:
                    System.out.println("Goodbye");
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void sendMultipleMessages() {

        System.out.print("How many messages would you like to send? ");

        int numberMessages = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberMessages; i++) {

            System.out.println("\nMessage " + (i + 1));

            sendMessage(i);
        }

        System.out.println("Total Messages Sent: " + totalMessages);
    }

    private void sendMessage(int messageNumber) {

        System.out.print("Recipient (+27xxxxxxxxx): ");
        String recipient = scanner.nextLine();

        if (!checkRecipientCell(recipient)) {

            System.out.println(
                    "Cell phone number is incorrectly formatted or does not contain an international code.");

            return;
        }

        System.out.println("Cell phone number successfully captured.");

        System.out.print("Enter Message: ");
        String messageText = scanner.nextLine();

        if (messageText.length() > 250) {

            int extra = messageText.length() - 250;

            System.out.println(
                    "Message exceeds 250 characters by "
                    + extra
                    + ", please reduce size.");

            return;
        }

        System.out.println("Message ready to send.");

        String messageID = generateMessageID();

        String hash = createCustomHash(
                messageID,
                messageNumber,
                messageText);

        Message msg =
                new Message(
                        messageID,
                        recipient,
                        messageText,
                        hash);

        System.out.println("\n1. Send");
        System.out.println("2. Disregard");
        System.out.println("3. Store");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:

                sentMessages[sentCount] = msg;

                messageIDs[sentCount] = messageID;
                messageHashes[sentCount] = hash;

                sentCount++;
                totalMessages++;

                System.out.println("Message successfully sent.");
                break;

            case 2:

                disregardedMessages[disregardCount++] = msg;

                System.out.println("Press 0 to delete message.");
                break;

            case 3:

                storedMessages[storedCount++] = msg;

                System.out.println("Message successfully stored.");
                break;
        }
    }

    public boolean checkRecipientCell(String number) {

        return number.matches("^\\+27\\d{9}$");
    }

    public String generateMessageID() {

        Random random = new Random();

        long num =
                1000000000L
                + (long) (random.nextDouble()
                * 9000000000L);

        return String.valueOf(num);
    }

    private String createCustomHash(
            String id,
            int num,
            String message) {

        String[] words = message.split(" ");

        String first =
                words[0].toUpperCase();

        String last =
                words[words.length - 1]
                        .toUpperCase();

        return id.substring(0, 2)
                + ":"
                + num
                + ":"
                + first
                + last;
    }

    public void showSentMessages() {

        if (sentCount == 0) {

            System.out.println("No sent messages.");
            return;
        }

        for (int i = 0; i < sentCount; i++) {

            System.out.println(sentMessages[i]);

            System.out.println("----------------");
        }
    }

    private void storedMessagesMenu() {

        int choice = 0;

        while (choice != 7) {

            System.out.println("\n===== STORED MESSAGES =====");

            System.out.println("1. Display Sender & Recipient");
            System.out.println("2. Display Longest Message");
            System.out.println("3. Search Message ID");
            System.out.println("4. Search Recipient");
            System.out.println("5. Delete By Hash");
            System.out.println("6. Display Report");
            System.out.println("7. Back");

            System.out.print("Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayStoredMessages();
                    break;

                case 2:
                    displayLongestMessage();
                    break;

                case 3:
                    searchByMessageID();
                    break;

                case 4:
                    searchByRecipient();
                    break;

                case 5:
                    deleteByHash();
                    break;

                case 6:
                    displayReport();
                    break;
            }
        }
    }

    private void displayStoredMessages() {

        for (int i = 0; i < storedCount; i++) {

            System.out.println(
                    "Recipient: "
                    + storedMessages[i].getRecipient());

            System.out.println(
                    "Message: "
                    + storedMessages[i].getMessageText());

            System.out.println();
        }
    }

    private void displayLongestMessage() {

        if (storedCount == 0) {

            System.out.println("No stored messages.");
            return;
        }

        Message longest = storedMessages[0];

        for (int i = 1; i < storedCount; i++) {

            if (storedMessages[i]
                    .getMessageText()
                    .length()
                    >
                    longest.getMessageText()
                            .length()) {

                longest = storedMessages[i];
            }
        }

        System.out.println(
                "Longest Message:\n"
                + longest.getMessageText());
    }

    private void searchByMessageID() {

        System.out.print("Enter Message ID: ");

        String id = scanner.nextLine();

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i]
                    .getMessageID()
                    .equals(id)) {

                System.out.println(
                        storedMessages[i]);

                return;
            }
        }

        System.out.println("Message not found.");
    }

    private void searchByRecipient() {

        System.out.print("Enter Recipient: ");

        String recipient =
                scanner.nextLine();

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i]
                    .getRecipient()
                    .equals(recipient)) {

                System.out.println(
                        storedMessages[i]
                                .getMessageText());
            }
        }
    }

    private void deleteByHash() {

        System.out.print("Enter Message Hash: ");

        String hash = scanner.nextLine();

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i]
                    .getMessageHash()
                    .equals(hash)) {

                System.out.println(
                        "Message \""
                        + storedMessages[i]
                                .getMessageText()
                        + "\" successfully deleted.");

                storedMessages[i] = null;

                return;
            }
        }

        System.out.println("Hash not found.");
    }

    private void displayReport() {

        System.out.println("\n===== REPORT =====");

        for (int i = 0; i < sentCount; i++) {

            System.out.println(
                    "Hash: "
                    + sentMessages[i]
                            .getMessageHash());

            System.out.println(
                    "Recipient: "
                    + sentMessages[i]
                            .getRecipient());

            System.out.println(
                    "Message: "
                    + sentMessages[i]
                            .getMessageText());

            System.out.println("----------------");
        }
    }
}
