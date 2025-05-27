package org.talent.service;

import org.talent.bean.Associate;
import org.talent.bean.ProjectAssociate;
import org.talent.reader.ResourceReader;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TalentService {
    private static final Logger logger = Logger.getLogger(TalentService.class.getName());

    public void assignAssociateToProject(List<Associate> lockAssociates) throws IOException {
        System.out.println("Enter the associate id from the lock list to allocate :");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        Optional<Associate> AssociateFound = Optional.ofNullable(lockAssociates.stream().filter(n -> n.getId() == input).findFirst().orElse(null));
        if (AssociateFound.isPresent()) {
            //Associate found in the lock list, good to confirm allocation
            logger.info("Associate found in the lock list, good to confirm allocation..");

            //build project associate and move to project_associates.txt
            ProjectAssociate projectAssociate = buildProjectAssociate(input);

            //add the associate to the project_associates.txt
            addAssociateToProject(projectAssociate);

            //remove the associate from available_associate.txt

        } else {
            logger.info("Associate NOT found in the lock list, unable to proceed, pls enter correct id..");
        }
    }

    private static ProjectAssociate buildProjectAssociate(int associateId) {
        ProjectAssociate projectAssociate = new ProjectAssociate();
        projectAssociate.setAssociateId(associateId);
        System.out.println("Hiring Manager Id (Mandatory) :");
        projectAssociate.setHiringManagerId(new Scanner(System.in).nextInt());
        System.out.println("Hiring Manager Name (Mandatory) :");
        projectAssociate.setHiringManagerName(new Scanner(System.in).next());
        System.out.println("Project Name (Mandatory) :");
        projectAssociate.setProjectName(new Scanner(System.in).next());
        System.out.println("Project Id (Mandatory) :");
        projectAssociate.setProjectId(new Scanner(System.in).nextInt());
        System.out.println("Project StartDate (Mandatory) :");
        projectAssociate.setStartDate(new Date(new Scanner(System.in).next()));
        System.out.println("Project StartDate (Mandatory) :");
        projectAssociate.setEndDate(new Date(new Scanner(System.in).next()));
        return projectAssociate;
    }

    private static void addAssociateToProject(ProjectAssociate newAssociate) throws IOException {
        ResourceReader reader = new ResourceReader();
        List<ProjectAssociate> projectAssociates = getAssociatesFromProjectFile(reader);
        projectAssociates.add(newAssociate);
        System.out.println("New Associate has been added to the project. No of Project Associates are : " + projectAssociates.size());
        projectAssociates.forEach(System.out::println);
    }

    private static List<ProjectAssociate> getAssociatesFromProjectFile(ResourceReader reader) throws IOException {
        String fileContent = reader.readFileFromResources("project_associates.txt");
        List<String> records = Arrays.stream(fileContent.split("\r\n")).skip(1).collect(Collectors.toList());
        List<ProjectAssociate> projectAssociates = new ArrayList<>();
        for (String s : records) {
            ProjectAssociate projectAssociate = getProjectAssociate(s);
            projectAssociates.add(projectAssociate);
        }
        System.out.println("No. of associates working in project is/are : " + projectAssociates.size());
        return projectAssociates;
    }

    private static ProjectAssociate getProjectAssociate(String s) {
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
