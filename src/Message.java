import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class Message {
    ArrayList<String> message;
    String name, mail, ip, host;
    ArrayList<String> otherTags;

    Message(ArrayList<String> message) throws UnsupportedEncodingException {
        this.message = message;
        otherTags = new ArrayList<String>();
        ArrayList<String> reversed = new ArrayList<String>(message);
        Collections.reverse(reversed);
        for (String line : message) {
            if (!line.startsWith("From") && !line.startsWith("To") && !line.startsWith("Subject") &&
                    !line.startsWith("Date") && !line.startsWith("Replay-To") && !line.startsWith("X-spam") &&
                    !line.startsWith("Received") && !line.startsWith("\t") && !line.startsWith(" ")) {
                String[] splitedTag = line.split(":");
                String tag = "";
                if (splitedTag.length > 1) {
                    int index = message.size() - message.indexOf(line) - 1;
                    tag += reversed.get(index);
                    index++;
                    while (index < reversed.size() && reversed.get(index).startsWith("\t")) {
                        tag +="\n"+reversed.get(index);
                        index++;
                    }
                    otherTags.add(tag);
                }
            }
            if (line.startsWith("From:") && name == null) {

                String[] words = line.split(" ");
                name = MimeUtility.decodeText(words[1]);
                mail = line.substring(line.indexOf("<") + 1, line.indexOf(">"));
            }
            if (line.startsWith("Received:") && ip == null) {
                int index = message.size() - message.indexOf(line) - 1;
                String sameTag = "\t" + reversed.get(index);
                while (sameTag.startsWith("\t")) {
                    if (sameTag.contains("[") && sameTag.contains("]")) {
                        ip = sameTag.substring(sameTag.indexOf('[') + 1, sameTag.indexOf(']'));
                    }
                    if (sameTag.contains("by")) {
                        host = "";
                        int start = sameTag.indexOf("by ") + 3;
                        while (sameTag.charAt(start) != ' ') {
                            host = host + sameTag.charAt(start);
                            start++;
                        }
                    }
                    index++;
                    sameTag = reversed.get(index);
                }
            }
        }
    }
}
