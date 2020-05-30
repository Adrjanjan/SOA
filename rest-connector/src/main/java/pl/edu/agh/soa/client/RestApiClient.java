package pl.edu.agh.soa.client;

import javax.swing.*;
import java.awt.*;

import pl.edu.agh.soa.model.RailwayCarriage;
import pl.edu.agh.soa.model.Train;
import pl.edu.agh.soa.protobuf.CarriagesMessageWriter;
import pl.edu.agh.soa.protobuf.CarriagesProto;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.List;

public class RestApiClient {

    private static final WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8080/rest-api/api/");

    private static String token;

    private static final String AUTHORIZATION = "Authorization";

    // klasy do ProtoBuf zostały wygenerowane przy pomocy pluginu zawartego w pom'ie
    public static void main(String[] args) {
        mockData();
        createUser("user", "password");
        token = authorizeUser("user", "password");

        // dodawanie obiektu
        addTrain();

        // metoda listująca i pobierająca obiekty
        final var train = getTrain();
        System.out.println("Train id: " + train.getId());
        System.out.println("Train has carriages with following ids: ");
        train.getCarriages()
                .stream()
                .map(RailwayCarriage::getId)
                .forEach(System.out::println);

        // wywołanie i odkodowanie metody zwracajacej dane binarne
        displayTrainIcon(1L);

        // ProtoBuf
        displayCarriagesFromProtoBuf(1L);
    }

    private static void mockData() {
        var client = ClientBuilder.newClient();
        client.target("http://localhost:8080/rest-api/api/mock");
    }

    private static void createUser(String login, String password) {
        var target = RestApiClient.target.path("signup");
        var form = new Form();
        form.param("login", login);
        form.param("password", password);
        target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    }

    private static String authorizeUser(String login, String password) {
        var target = RestApiClient.target.path("login");
        var form = new Form();
        form.param("login", login);
        form.param("password", password);
        var response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        var headers = response.getStringHeaders();
        return headers.getFirst("Authorization");
    }

    private static void addTrain() {
        final var train = Train.builder()
                .logoPath("logo.png")
                .carriages(List.of(RailwayCarriage.builder()
                        .available(false)
                        .registrationNumber("asdf")
                        .build(),
                        RailwayCarriage.builder()
                                .available(false)
                                .registrationNumber("ethw")
                                .build()))
                .build();

        System.out.println("Adding train");

        var target = RestApiClient.target.path("trains");
        Response response = target.request()
                .header(AUTHORIZATION, token)
                .post(Entity.entity(train, MediaType.APPLICATION_JSON_TYPE));
        var responseStatus = response.getStatus();
        if(responseStatus == 409){
            System.out.println("Train already added, try changing it to new!");
        }
        System.out.println("Train added successful");
    }

    private static Train getTrain() {
        var target = RestApiClient.target.path("trains/1");
        return target.request()
                .header(AUTHORIZATION, token)
                .get(Train.class);
    }

    private static void displayTrainIcon(long l) {
        var target = RestApiClient.target.path("trains/1/logo");

        byte[] icon = target.request()
                .header(AUTHORIZATION, token)
                .get()
                .readEntity(byte[].class);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 640);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(icon));
        panel.add(label);
        jFrame.getContentPane()
                .add(panel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private static void displayCarriagesFromProtoBuf(long l) {
        var target = RestApiClient.target.path("trains")
                .register(CarriagesMessageWriter.class)
                .path("1/carriages");
        System.out.println("Printing Ids of carriages from protobuf");
        final var carriages = target.request()
                .header(AUTHORIZATION, token)
                .get(CarriagesProto.Carriages.class);
        for (var carriage : carriages.getCarriagesList()) {
            System.out.println(carriage.getId());
        }
    }
}