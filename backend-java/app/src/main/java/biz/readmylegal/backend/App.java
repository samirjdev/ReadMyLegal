/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package biz.readmylegal.backend;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private int port;
    private HttpBackend http;

    public App(int port) throws IOException {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void start() throws IOException {
        http = new HttpBackend(port);
        http.start();
    }

    public void stop() {
        http.stop();
    }

    public static void main(String[] args) throws IOException {
        App app = new App(Integer.parseInt(args[0]));
        app.start();
        System.out.println("Listening on 127.0.0.1:" + app.getPort());
        System.out.println("Type `exit` to close the server.");
        awaitExit();
        System.out.println("Closing server, please wait...");
        app.stop();
        System.out.println("Have a nice day!");
    }

    private static void awaitExit() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equals("exit"));
        scanner.close();
    }

}
