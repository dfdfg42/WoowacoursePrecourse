package pairmatching.service;

import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.CrewRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Datainitializer {

    CrewRepository crewRepository = new CrewRepository();

    public void loadCrews() throws FileNotFoundException {
        //백엔드
        Scanner scanner = new Scanner(new File("C:\\Users\\dfdfg\\IdeaProjects\\java-pairmatching-precourse\\src\\main\\resources\\backend-crew.md"));
        while (scanner.hasNext()) {
            String str = scanner.next();
            saveCrews(str, Course.BACKEND);
        }

        //프론트엔드
        Scanner scanner2 = new Scanner(new File("C:\\Users\\dfdfg\\IdeaProjects\\java-pairmatching-precourse\\src\\main\\resources\\frontend-crew.md"));
        while (scanner2.hasNext()) {
            String str2 = scanner2.next();
            saveCrews(str2, Course.FRONTEND);
        }

        scanner.close();
        scanner2.close();
    }

    public void saveCrews(String crewName, Course course) {
        Crew crew = new Crew(course, crewName);
        crewRepository.add(crew);
    }

    public CrewRepository getCrewRepository() {
        return crewRepository;
    }
}
