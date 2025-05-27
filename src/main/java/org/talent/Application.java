package org.talent;

import org.talent.bean.Associate;
import org.talent.reader.ResourceReader;
import org.talent.service.TalentService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        System.out.println("Talent Management Application");
        ResourceReader resourceReader = new ResourceReader();
        List<Associate> associates = resourceReader.getAssociatesFromAvailableFile();
        List<Associate> unlockAssociates = resourceReader.getAssociates(associates, "unlock");
        List<Associate> lockAssociates = resourceReader.getAssociates(associates, "lock");

        int input;
        do {
            System.out.println("1.Available Associates(lock+unlock)\t\t2.Unlock Associates\t\t3.Lock an Associate\t\t" + "4.Allocate an Associate\t\t5.Project Associates");
            System.out.println("Choose from the above option :");
            input = new Scanner(System.in).nextInt();
            if (input == 1) {
                System.out.println("Total associates available are : " + associates.size());
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
                resourceReader.lockAnAssociate(unlockAssociates);
            }
            if (input == 4) {
                //List of all lock associates
                System.out.println("Lock Associates(" + lockAssociates.size() + ") are below:");
                for (Associate associate : lockAssociates) {
                    System.out.println(associate.getId() + "\t" + associate.getName() + "\t" + associate.getAvailableDate() + "\t" + associate.getLockStatus());
                }
                //assign associate to project
                TalentService talentService = new TalentService();
                talentService.assignAssociateToProject(lockAssociates);
            }
        } while (input < 5);
    }
}
