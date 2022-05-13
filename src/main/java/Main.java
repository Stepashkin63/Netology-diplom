import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        String pathName = "E:\\Java projects\\pcs-jd-diplom\\pdfs";
        BooleanSearchEngine engine = new BooleanSearchEngine(new File(pathName));
        System.out.println(engine.search("бизнес"));

        int port = 8989;


        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String word = in.readLine();

                List<PageEntry> list = engine.search(word);
                Collections.sort(list);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();

                for (PageEntry pageEntry : list) {
                    out.println(gson.toJson(pageEntry));
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}