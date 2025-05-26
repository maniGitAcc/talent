package org.talent;

import org.talent.bean.Associate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResourceReader {
    private static final Logger logger = Logger.getLogger(ResourceReader.class.getName());

    public static void main(String[] args) {
        ResourceReader reader = new ResourceReader();
        try {
            List<Associate> associates = getAssociatesFromAvailableFile(reader);

            //List of all unlock associates
            List<Associate> unlockAssociates = getAssociates(associates, "unlock");

            //method to lock a given associate from unlock list
            lockAnAssociate(unlockAssociates);

            //list of all locked associates
            List<Associate> lockAssociates = getAssociates(associates, "lock");

            //assign associate to project
            TalentService talentService = new TalentService();
            talentService.assignAssociateToProject(lockAssociates);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Associate> getAssociates(List<Associate> associates, String status) {
        List<Associate> unlockAssociates = associates.stream().filter(n -> status.equalsIgnoreCase(n.getLockStatus())).collect(Collectors.toList());
        System.out.println("Unlock Associates(" + unlockAssociates.size() + ") are below:");
        for (Associate associate : unlockAssociates) {
            System.out.println(associate.getId() + "\t" + associate.getName() + "\t" + associate.getAvailableDate() + "\t" + associate.getLockStatus());
        }
        return unlockAssociates;
    }

    private static List<Associate> getAssociatesFromAvailableFile(ResourceReader reader) throws IOException {
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
        System.out.println("Total associates available are : " + associates.size());
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
    private static void lockAnAssociate(List<Associate> unlockAssociates) {
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