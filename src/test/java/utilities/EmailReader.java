package utilities;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailReader {

    public static String getVerificationLink(String email, String appPassword) throws Exception {

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        Session session = Session.getInstance(props, null);
        Store store = session.getStore();
        store.connect("imap.gmail.com", email, appPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();

        // Read latest → older
        for (int i = messages.length - 1; i >= 0; i--) {
            Message msg = messages[i];

            // Only look for Firebase Reset / Verify emails (oobCode)
            String body = extractBody(msg);

            if (body.contains("oobCode=")) {
                String link = extractFirebaseLink(body);
                if (link != null) {
                    return link;
                }
            }
        }

        return null;
    }

    // Extract text from HTML, plain text, or multipart
    private static String extractBody(Message message) throws Exception {
        Object content = message.getContent();

        if (content instanceof String) {
            return (String) content;
        }

        if (content instanceof MimeMultipart) {
            return getTextFromMimeMultipart((MimeMultipart) content);
        }

        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                result.append(Jsoup.parse(bodyPart.getContent().toString()).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }

        return result.toString();
    }

    // Extract Firebase Reset / Verify Link using strict regex
    private static String extractFirebaseLink(String body) {

        Pattern p = Pattern.compile("(https?://[^\\s]+)");
        Matcher m = p.matcher(body);

        while (m.find()) {
            String found = m.group();

            // Only Firebase action URL must be returned
            if (found.contains("oobCode=")) {
                return found;
            }
        }

        return null;
    }
}
