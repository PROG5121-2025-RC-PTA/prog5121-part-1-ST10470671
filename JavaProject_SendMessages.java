/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaproject_sendmessages;

/**
 *
 * @author RC_Student_lab
 */

import javax.swing.*;
import java.util.*;
import java.io.*;
import org.json.simple.JSONObject;

public class JavaProject_SendMessages {

    static int totalMessagesSent = 0;
    static ArrayList<String> messageList = new ArrayList<>();
    static JSONArray storedMessages = new JSONArray();
    static Random rand = new Random();

    public static void main(String[] args) {
        boolean loggedIn = true; // Assume login was successful for now

        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Log in to use Quickchat.");
            return;
        }

        JOptionPane.showMessageDialog(null, " Welcome to QuickChat.");

        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("Enter a number of messages to send:"));
        int sentCount = 0;

        while (true) {
            String menu = """
                Choose an option:
                1) Send Message
                2) Show recently sent messages
                3) Quit
            """;
            int choice = Integer.parseInt(JOptionPane.showInputDialog(menu));

            if (choice == 1 && sentCount < numMessages) {
                String recipient = JOptionPane.showInputDialog("Enter the recipient number (+27000000000):");
                if (!checkRecipientCell(recipient)) continue;

                String message = JOptionPane.showInputDialog("Enter your message (max 300 chars):");
                if (message.length() > 300) {
                    JOptionPane.showMessageDialog(null, "Message exceeds 300 characters by " + (message.length() - 300));
                    continue;
                } else {
                    JOptionPane.showMessageDialog(null, "Message ready to send.");
                }

                String messageID = generateMessageID();
                if (!checkMessageID(messageID)) continue;

                String messageHash = createMessageHash(messageID, totalMessagesSent, message);
                String[] options = {"Send", "Ignore", "Save"};
                int option = JOptionPane.showOptionDialog(null, "Choose action:", "Message Options", 0, 1, null, options, options[0]);

                switch (option) {
                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "Message is successfully sent.");
                        messageList.add(printMessage(messageID, messageHash, recipient, message));
                        sentCount++;
                        totalMessagesSent++;
                    }
                    case 1 -> {
                        JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                    }
                    case 2 -> {
                        JOptionPane.showMessageDialog(null, "Message is successfully stored.");
                        storeMessage(messageID, messageHash, recipient, message);
                    }
                }
            } else if (choice == 2) {
                JOptionPane.showMessageDialog(null, "Coming Soon.");
            } else if (choice == 3) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option or limit reached.");
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
    }

    public static boolean checkMessageID(String messageID) {
        return messageID.length() <= 10;
    }

    public static boolean checkRecipientCell(String recipient) {
        if (recipient.length() <= 10 || !recipient.startsWith("+")) {
            JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code.");
            return false;
        }
        JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
        return true;
    }

    public static String createMessageHash(String messageID, int messageNumber, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public static String printMessage(String id, String hash, String recipient, String message) {
        String result = "MessageID: " + id + "\nMessageHash: " + hash + "\nRecipient: " + recipient + "\nMessage: " + message;
        JOptionPane.showMessageDialog(null, result);
        return result;
    }

    public static String generateMessageID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }

    public static void storeMessage(String id, String hash, String recipient, String message) {
        JSONObject msgObj = new JSONObject();
        msgObj.put("MessageID", id);
        msgObj.put("MessageHash", hash);
        msgObj.put("Recipient", recipient);
        msgObj.put("Message", message);
        storedMessages.add();

        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(storedMessages.toJSONString());
            file.flush();
        } catch (IOException e) {
        }
    }

    private static class JSONArray {

        public JSONArray() {
        }


        private char[] toJSONString() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void add() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}