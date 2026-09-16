package pairmatching;

import pairmatching.domain.*;
import pairmatching.service.Datainitializer;
import pairmatching.service.MissionInitializer;
import pairmatching.service.PairMatch;

import java.util.List;
import java.util.Scanner;

public class Application {
    private static Datainitializer datainitializer;
    private static MissionRepository missionRepository;

    public static void main(String[] args) {
        try {
            // 크루 데이터 로드
            datainitializer = new Datainitializer();
            datainitializer.loadCrews();

            // 미션 데이터 초기화
            MissionRepository missionRepository = new MissionRepository();
            MissionInitializer missionInitializer = new MissionInitializer(missionRepository);
            missionInitializer.initializeMissions();

            PairMatch pairMatch = new PairMatch(missionRepository);
            // 메인 루프
            while (true) {
                viewChoice();
                String str;
                Scanner scanner = new Scanner(System.in);
                str = scanner.nextLine();

                if (str.equals("Q")) {
                    break;
                }
                if (str.equals("1")) {
                    viewMissionChoice();
                    str = scanner.nextLine();

                    // 입력 파싱
                    String[] parts = parseInput(str);
                    Course targetCourse = Course.fromString(parts[0]);
                    Level targetLevel = Level.fromString(parts[1]);
                    String missionName = parts[2];

                    // 크루 목록 가져오기
                    List<Crew> targetCrews = datainitializer.getCrewRepository().findByCourse(targetCourse);

                    // 미션 찾기
                    Mission targetMission = missionRepository.findByMissionName(missionName);

                    // 기존 매칭 이력 확인
                    if (targetMission.getPairList() != null && !targetMission.getPairList().isEmpty()) {
                        System.out.println("매칭 정보가 있습니다. 다시 매칭하시겠습니까?\n" +
                                "네 | 아니오");
                        str = scanner.nextLine();
                        if (str.equals("아니오")) {
                            continue;
                        }
                        // "네"면 아래 매칭 진행
                    }

                    // 페어 매칭
                    List<Pair> result = pairMatch.matchPairs(targetCrews, targetLevel, targetMission);

                    // 매칭 결과 저장
                    targetMission.setPairList(result);

                    // 매칭 결과 출력
                    System.out.println("페어 매칭 결과입니다.");
                    for (Pair pair : result) {
                        System.out.println(pair.toString());
                    }

                } else if (str.equals("2")) {
                    // 페어 조회
                    viewMissionChoice();
                    str = scanner.nextLine();

                    // 입력 파싱
                    String[] parts = parseInput(str);
                    String missionName = parts[2];

                    // 미션 찾기
                    Mission targetMission = missionRepository.findByMissionName(missionName);

                    // 매칭 이력 확인
                    if (targetMission.getPairList() == null || targetMission.getPairList().isEmpty()) {
                        System.out.println("[ERROR] 매칭 이력이 없습니다.");
                        continue;
                    }

                    // 매칭 결과 출력
                    System.out.println("페어 매칭 결과입니다.");
                    for (Pair pair : targetMission.getPairList()) {
                        System.out.println(pair.toString());
                    }

                } else if (str.equals("3")) {
                    pairMatch.deletePair();
                    System.out.println("초기화 되었습니다.");
                }
            }

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // "백엔드, 레벨1, 자동차경주" 파싱
    private static String[] parseInput(String input) {
        String[] parts = input.split(",");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    public static void viewChoice() {
        System.out.println("기능을 선택하세요.\n" +
                "1. 페어 매칭\n" +
                "2. 페어 조회\n" +
                "3. 페어 초기화\n" +
                "Q. 종료");
    }

    public static void viewMissionChoice() {
        System.out.println("#############################################\n" +
                "과정: 백엔드 | 프론트엔드\n" +
                "미션:\n" +
                "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n" +
                "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n" +
                "  - 레벨3: \n" +
                "  - 레벨4: 성능개선 | 배포\n" +
                "  - 레벨5: \n" +
                "############################################\n" +
                "과정, 레벨, 미션을 선택하세요.\n" +
                "ex) 백엔드, 레벨1, 자동차경주");
    }
}
