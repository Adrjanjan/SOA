package pl.edu.agh.soa.connector;

import pl.edu.agh.soa.generated.*;

import javax.swing.*;
import javax.xml.ws.BindingProvider;
import java.awt.*;
import java.util.Base64;

public class ApiConsumer {

    // klasy zostały wygenerowane przy pomocy pluginu zawartego w pom'ie
    public static void main(String[] args) {
        TrainService trainService = new TrainServiceService().getTrainServicePort();

        authorizeUser((BindingProvider) trainService);
        trainService.mockData();

        // dodawanie obiektu
        addTrain(trainService);

        // metoda listująca i pobierająca obiekty
        final var train = getTrain(1L, trainService);
        System.out.println("Train id: " + train.getId());
        System.out.println("Train has carriages with following ids: ");
        train.getCarriages()
                .getCarriage()
                .stream()
                .map(RailwayCarriage::getId)
                .forEach(System.out::println);

        // wywołanie i odkodowanie metody zwracajacej dane binarne
        displayTrainIcon(1L, trainService);
    }

    private static void authorizeUser(BindingProvider trainService) {
        trainService.getRequestContext()
                .put(BindingProvider.USERNAME_PROPERTY, "soa1");
        trainService.getRequestContext()
                .put(BindingProvider.PASSWORD_PROPERTY, "soa1");
    }

    private static void displayTrainIcon(long l, TrainService trainService) {
        byte[] icon = Base64.getDecoder()
                .decode(trainService.getTrainIconBase64(l));
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

    private static void addTrain(TrainService trainService) {
        var c4 = new RailwayCarriage();
        c4.setId(4L);
        c4.setIsAvailable(false);
        c4.setRegistrationNumber("RC4");

        var c5 = new RailwayCarriage();
        c5.setId(5L);
        c5.setIsAvailable(false);
        c5.setRegistrationNumber("RC5");

        var c6 = new RailwayCarriage();
        c5.setId(6L);
        c5.setIsAvailable(false);
        c5.setRegistrationNumber("RC6");

        Train t1 = new Train();
        t1.setId(5L);
        t1.setLogoPath("logo.png");
        t1.setCarriages(new Train.Carriages());
        t1.getCarriages()
                .getCarriage()
                .add(c4);
        t1.getCarriages()
                .getCarriage()
                .add(c5);
        t1.getCarriages()
                .getCarriage()
                .add(c6);
        trainService.addTrain(5L, t1);
    }

    private static Train getTrain(Long id, TrainService trainService) {
        return trainService.getTrain(id);
    }

}
