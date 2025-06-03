package org.talent.reader;

import org.talent.bean.Associate;
import org.talent.bean.ProjectAssociate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.talent.contants.Constants.LOCK;

public class ResourceReader {
    private static final Logger logger = Logger.getLogger(ResourceReader.class.getName());

    public List<Associate> getAssociates(List<Associate> associates, String status) {
        return associates.stream().filter(n -> status.equalsIgnoreCase(n.getLockStatus())).collect(Collectors.toList());
    }

    public List<Associate> getProjectAssociates(List<Associate> associates, String status) {
        return associates.stream().filter(n -> status.equalsIgnoreCase(n.getLockStatus())).collect(Collectors.toList());
    }

    public List<Associate> getAssociatesFromAvailableFile() throws IOException {
        String fileContent = readFileFromResources("available_associates.txt");
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
    public Associate lockAnAssociate(List<Associate> unlockAssociates) {
        //enter the associate id to lock, if status is unlock then change to lock
        System.out.println("Enter the associate Id to lock :");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        Optional<Associate> associateFound = Optional.ofNullable(unlockAssociates.stream().filter(n -> n.getId() == input).findFirst().orElse(null));
        if (associateFound.isPresent()) {
            associateFound.get().setLockStatus(LOCK);
            System.out.println("Associate with id " + input + " is locked..");
            return associateFound.get();
        } else {
            System.out.println("Associate with id " + input + " is not available to lock..");
            return null;
        }
    }
    public List<ProjectAssociate> getAssociatesFromProjectFile() throws IOException {
        String fileContent = readFileFromResources("project_associates.txt");
        List<String> records = Arrays.stream(fileContent.split("\r\n")).skip(1).collect(Collectors.toList());
        List<ProjectAssociate> projectAssociates = new ArrayList<>();
        for (String s : records) {
            ProjectAssociate projectAssociate = getProjectAssociate(s);
            projectAssociates.add(projectAssociate);
        }
        //System.out.println("No. of associates working in project is/are : " + projectAssociates.size());
        return projectAssociates;
    }
    private ProjectAssociate getProjectAssociate(String s) {
        ProjectAssociate projectAssociate = new ProjectAssociate();
        String[] line = s.split("\\|");
        projectAssociate.setProjectId(Integer.parseInt(line[0]));
        projectAssociate.setProjectName(line[1]);
        projectAssociate.setHiringManagerId(Integer.parseInt(line[2]));
        projectAssociate.setHiringManagerName(line[3]);
        projectAssociate.setStartDate(new Date(line[4]));
        projectAssociate.setEndDate(new Date(line[5]));
        projectAssociate.setAssociateId(Integer.parseInt(line[6]));
        return projectAssociate;
    }
}