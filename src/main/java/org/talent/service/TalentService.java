package org.talent.service;

import org.talent.bean.Associate;
import org.talent.bean.ProjectAssociate;
import org.talent.reader.ResourceReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Logger;

public class TalentService {
    private static final Logger logger = Logger.getLogger(TalentService.class.getName());

    public List<ProjectAssociate> assignAssociateToProject(List<Associate> lockAssociates) throws IOException {
        System.out.println("Enter the associate id from the lock list to allocate :");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        Optional<Associate> AssociateFound = Optional.ofNullable(lockAssociates.stream().filter(n -> n.getId() == input).findFirst().orElse(null));
        if (AssociateFound.isPresent()) {
            //Associate found in the lock list, good to confirm allocation
            System.out.println("Associate found, good to confirm allocation..");

            //build project associate and move to project_associates.txt
            ProjectAssociate projectAssociate = buildProjectAssociate(input);

            //add the associate to the project_associates.txt
            List<ProjectAssociate> projectAssociates = addAssociateToProject(projectAssociate);
            return projectAssociates;
        } else {
            logger.info("Associate NOT found in the lock list, pls enter correct id..");
            return null;
        }
    }

    public void writeToFile(List<ProjectAssociate> projectAssociates) {
        /*String filePath = "C:\\Users\\301112\\repositories\\javaprograms\\src\\main\\resources\\output.txt";
        //List<String> content = Arrays.asList("Line 1", "Lie 2", "Lne 3");

        try {
            Files.write(Paths.get(filePath), projectAssociates, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private ProjectAssociate buildProjectAssociate(int associateId) {
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

    private List<ProjectAssociate> addAssociateToProject(ProjectAssociate newAssociate) throws IOException {
        ResourceReader reader = new ResourceReader();
        List<ProjectAssociate> projectAssociates = reader.getAssociatesFromProjectFile();
        projectAssociates.add(newAssociate);
        System.out.println("New Associate has been added to the project. Project Associates are : " + projectAssociates.size());
        for (ProjectAssociate pa : projectAssociates) {
            System.out.println(pa);
        }
        return projectAssociates;
    }
}