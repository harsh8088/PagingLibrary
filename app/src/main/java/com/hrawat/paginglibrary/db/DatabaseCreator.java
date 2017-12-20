package com.hrawat.paginglibrary.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseCreator {

    private String[] firstName = new String[]{"Noah", "Emma", "Liam", "Olivia", "William", "Ava", "John", "Sophia", "James",
            "Isabella", "Suresh", "Mia", "Jacob", "Shaheem", "Charlotte", "Michael", "Abigail", "Elijah", "Emily", "Mahesh", "Harper", "Harsh"};
    private String[] street = new String[]{
            "High Street", "Station Road", "Badi Mukhani", "Main Street", "Park Road", "Church Road", "Church Street", "London Road", "Victoria Road", "Green Lane",
            "The Avenue", "Railway Bazaar", "Queens Road", "New Road", "Grange Road", "Kings Road", "Kingsway", "Windsor Road", "Highfield Road", "Mill Lane",
            "Alexander Road", "York Road", "St. John’s Road", "Choti Mukhani", "Church Lane", "Park Avenue", "Pilikothi"};
    private String[] city = new String[]{
            "Ashland", "Aspen", "Atascadero", "Kathgodam", "Athens", "Atlanta", "Auburn", "Austin", "Ayer", "Babylon", "Bainbridge", "Haldwani", "Naintal"
    };
    private String[] state = new String[]{
            "New Hampshire", "UK", "New Jersey", "New Mexico", "New York", "Uttrakhand", "Himanchal Pradesh"};

    public List<User> getRandomUserList() {
        List<User> users = new ArrayList<>();
        User tempUser;
        int cityRange = city.length;
        int stateRange = state.length;
        int nameRange = firstName.length;
        int streetRange = street.length;
        String address;
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            address = state[random.nextInt(stateRange)] + "," + city[random.nextInt(cityRange)] + "," +
                    String.valueOf(random.nextInt(99999)) + "," + street[random.nextInt(streetRange)];
            tempUser = new User();
            tempUser.address = address;
            tempUser.firstName = firstName[random.nextInt(nameRange)];
            tempUser.age=random.nextInt(99);
            users.add(tempUser);
        }
        return users;
    }

    public List<User> getFiniteUserList() {
        List<User> users = new ArrayList<>();
        User tempUser;
        int cityRange = city.length;
        int stateRange = state.length;
        int nameRange = firstName.length;
        int streetRange = street.length;
        String address;
        Random random = new Random();
        for (int i = 1; i <= 2000; i++) {
            address = state[random.nextInt(stateRange)] + "," + city[random.nextInt(cityRange)] + "," +
                    String.valueOf(random.nextInt(99999)) + "," + street[random.nextInt(streetRange)];
            tempUser = new User();
            tempUser.address = address;
            tempUser.firstName = firstName[random.nextInt(nameRange)];
            users.add(tempUser);
        }
        return users;
    }
}
