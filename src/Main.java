import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("C:\\Users\\neek\\Desktop\\to google from tb.txt"));
        ArrayList<String> mes = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                break;
            }
            mes.add(line);
        }
        Collections.reverse(mes);
        Message message = new Message(mes);
        System.out.println("Имя: " + message.name);
        System.out.println("E-mail: " + message.mail);
        System.out.println("ip: " + message.ip);
        System.out.println("host: " + message.host);
        if (message.ip != null) {
            System.out.println("Информация из WHOIS");
            Http.main(message.ip);
        }
        System.out.println("Другие теги");
        for (String s : message.otherTags) {
            System.out.println(s);
        }


    }
}
