package org.talent;

import org.talent.bean.Associate;
import org.talent.bean.ProjectAssociate;
import org.talent.reader.ResourceReader;
import org.talent.service.TalentService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.talent.contants.Constants.LOCK;
import static org.talent.contants.Constants.UNLOCK;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) throws IOException {
        System.out.println("TALENT Marketplace");
        ResourceReader resourceReader = new ResourceReader();
        List<Associate> associates = resourceReader.getAssociatesFromAvailableFile();
        List<ProjectAssociate> projAssociates = resourceReader.getAssociatesFromProjectFile();
        List<Associate> unlockAssociates = resourceReader.getAssociates(associates, UNLOCK);
        List<Associate> lockAssociates = resourceReader.getAssociates(associates, LOCK);

        int input;
        do {
            System.out.println("1.Display Available Associates(lock+unlock)\n2.Display Unlock Associates\n3.Lock an Associate\n" +
                    "4.Display Lock Associates\n5.Allocate an Associate\n6.Display Project Associates");
            System.out.println("Choose from the above option :");
            input = new Scanner(System.in).nextInt();
            if (input == 1) {
                System.out.println("Associates to get project assignment : " + associates.size());
            }
            if (input == 2) {
                //List of all unlock associates
                System.out.println("Unlock Associates(" + unlockAssociates.size() + ") are below:");
                for (Associate associate : unlockAssociates) {
                    System.out.println(associate.getId() + "\t" + associate.getName() + "\t" + associate.getAvailableDate() + "\t" + associate.getLockStatus());
                }
            }
            if (input == 3) {
                //method to lock a given associate from unlock list
                Associate associate = resourceReader.lockAnAssociate(unlockAssociates);
                if (null != associate) {
                    lockAssociates.add(associate);
                    unlockAssociates = unlockAssociates.stream().filter(n -> n.getId() != associate.getId()).collect(Collectors.toList());
                }
            }
            if (input == 4) {
                System.out.println("Lock Associates(" + lockAssociates.size() + ") are below:");
                for (Associate associate : lockAssociates) {
                    System.out.println(associate.getId() + "\t" + associate.getName() + "\t" + associate.getAvailableDate() + "\t" + associate.getLockStatus());
                }
            }
            if (input == 5) {
                //List of all lock associates
                System.out.println("Allocate an Associate from below Lock List(" + lockAssociates.size() + "):");
                for (Associate associate : lockAssociates) {
                    System.out.println(associate.getId() + "\t" + associate.getName());
                }
                //assign associate to project
                TalentService talentService = new TalentService();
                projAssociates = talentService.assignAssociateToProject(lockAssociates);
            }
            if (input == 6) {
                System.out.println("Associates with project assignment : " + projAssociates.size());
            }
        } while (input < 7);

        logger.info("Application End.");
    }
}
