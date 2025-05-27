package org.talent.reader;

import org.talent.bean.Associate;
import org.talent.service.TalentService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResourceReader {
    private static final Logger logger = Logger.getLogger(ResourceReader.class.getName());

    public List<Associate> getAssociates(List<Associate> associates, String status) {
        return associates.stream().filter(n -> status.equalsIgnoreCase(n.getLockStatus())).collect(Collectors.toList());
    }

    public List<Associate> getAssociatesFromAvailableFile() throws IOException {
        ResourceReader reader = new ResourceReader();
        String fileContent = reader.readFileFromResources("available_associates.txt");
        List<String> records = Arrays.stream(fileContent.split("\r\n")).skip(1).collect(Collectors.toList());
        List<Associate> associates = new ArrayList<>();
        for (String s : records) {
            Associate associate = new Associate();
            String[] line = s.split("\\|");
            associate.setId(Integer.parseInt(line[0]));
            associate.setName(line[1]);
            Date date = new Date(line[2]);
            associate.setAvailableDate(date);
            associate.setLockStatus(line[3]);
            associates.add(associate);
        }

        return associates;
    }

    public String readFileFromResources(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                return scanner.useDelimiter("\\A").next();
            }
        }
    }

    public void lockAnAssociate(List<Associate> unlockAssociates) {
        //enter the associate id to lock, if status is unlock then change to lock
        System.out.println("Enter the associate Id to lock :");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        Optional<Associate> AssociateFound = Optional.ofNullable(unlockAssociates.stream().filter(n -> n.getId() == input).findFirst().orElse(null));
        if (AssociateFound.isPresent()) {
            AssociateFound.get().setLockStatus("lock");
            System.out.println("Associate with id " + input + " is locked..");
        } else {
            System.out.println("Associate with id " + input + " is not available to lock..");
        }
    }
}