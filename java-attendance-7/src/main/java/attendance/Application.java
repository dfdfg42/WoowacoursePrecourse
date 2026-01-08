package attendance;

import attendance.domain.CrewRepository;

public class Application {
    public static void main(String[] args) {
        // 1. 저장소 생성 (이제 static이 아니므로 매 실행마다 깨끗한 상태)
        CrewRepository crewRepository = new CrewRepository();

        // 2. 데이터 로더에 저장소 주입 (주소 전달)
        Datainitializer datainitializer = new Datainitializer(crewRepository);
        datainitializer.loadFile();

        // 3. 입력 뷰 생성
        Input inputView = new Input(crewRepository); // Input 생성자에서 Repository 받도록 수정 추천

        // 4. 컨트롤러 생성 (입력뷰 + 저장소 주입)
        AttendanceController controller = new AttendanceController(inputView, crewRepository);

        // 5. 실행
        controller.run();
    }
}