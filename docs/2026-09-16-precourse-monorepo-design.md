# 우테코 프리코스 fork 모노레포 통합 설계

작성일: 2026-09-16
대상 계정: github.com/dfdfg42

## 목표

계정에 흩어진 우아한테크코스 프리코스 fork 11개를 `dfdfg42/woowacourse-precourse` 모노레포 하나로 합치고, 원본 fork는 삭제한다. 본인이 작성한 커밋 히스토리는 전부 보존하고, 폴더 단위로 히스토리를 볼 수 있어야 한다.

## 범위

### 본인 작업이 있는 fork 7개 → 경로 재작성 후 머지 (히스토리째 이관)

| 원본 fork | 작업 브랜치 | 본인 커밋 수 | 우테코 원본 PR |
|---|---|---|---|
| java-calculator-8 | dfdfg42 | 10 | woowacourse-precourse/java-calculator-8#788 |
| java-racingcar-8 | dfdfg42 | 12 | woowacourse-precourse/java-racingcar-8#252 |
| java-lotto-8 | dfdfg42 | 14 | woowacourse-precourse/java-lotto-8#105 |
| java-planetlotto-8 | dfdfg42 | 32 | woowacourse-precourse/java-planetlotto-8#82 |
| java-pairmatching-precourse | dfdfg42 | 1 | 없음 |
| java-oncall-6 | main | 1 | 없음 |
| java-attendance-7 | main | 1 | 없음 |

본인 커밋 수는 fork 브랜치가 우테코 원본 main보다 앞선 커밋 수. 이관 시 원본 템플릿 커밋(1~4개)도 함께 들어온다.

### 본인 작업이 없는 fork 2개 → 우테코 원본 템플릿 파일만 복사 (히스토리 없음)

| 원본 fork | 템플릿 출처 |
|---|---|
| java-christmas-6 | woowacourse-precourse/java-christmas-6 |
| java-subway-path-precourse | woowacourse/java-subway-path-precourse |

### 모노레포에 넣지 않고 삭제만 하는 fork 2개

| 원본 fork | 제외 이유 |
|---|---|
| java-attendance-7-abc5259 | abc5259(LeeJaeHoon)의 풀이 리포. 남의 솔루션이며 attendance-7 템플릿은 java-attendance-7에 이미 포함 |
| java-menu-precourse | haebyun의 풀이 리포. 우테코 원본 menu 리포는 현재 존재하지 않음 |

## 결과물 구조

```
woowacourse-precourse/            (public)
├── README.md                     미션 목록 표: 폴더, 기수/유형, 커밋 수, 원본 PR 링크
├── .gitignore                    루트용 (.idea/ 등 IDE 파일만)
├── docs/2026-09-16-precourse-monorepo-design.md   이 문서
├── java-calculator-8/
├── java-racingcar-8/
├── java-lotto-8/
├── java-planetlotto-8/
├── java-oncall-6/
├── java-attendance-7/
├── java-pairmatching-precourse/
├── java-christmas-6/             템플릿만
└── java-subway-path-precourse/   템플릿만
```

- 폴더 이름은 원본 리포 이름과 동일.
- 평평한 구조. 기수·유형 구분은 README 표로만.
- 각 폴더는 자체 gradlew, settings.gradle, .gitignore를 유지. 루트 빌드 설정은 두지 않는다. IntelliJ에서는 폴더 단위로 연다.
- 로컬 클론 위치: `C:\Users\dfdfg\source\woowacourse-precourse`
- 임시 작업 폴더: 세션 scratchpad 아래 `work/`. 작업 끝나면 삭제.

## 이관 방식: 경로 재작성 후 머지

`git subtree add`는 가져온 커밋의 파일이 루트에 있던 상태 그대로라 `git log -- <폴더>`와 GitHub 폴더 History에 머지 커밋 1개만 보인다 (2026-09-16 임시 리포로 확인). 대신 각 fork의 모든 커밋을 "처음부터 `<폴더>/` 아래에 파일이 있었던 것처럼" 재작성한 뒤 머지한다.

프로젝트 하나당:

1. `work/<폴더>`에 fork를 `git clone -b <브랜치> --single-branch` 로 클론.
2. 그 클론 안에서 `git filter-branch --index-filter`로 모든 커밋의 인덱스 경로 앞에 `<폴더>/`를 붙인다 (git 공식 문서의 subdirectory 이동 레시피). 추가 도구 설치 없음.
3. 모노레포에서 `work/<폴더>`를 remote로 추가해 fetch, `git merge --allow-unrelated-histories --no-ff -m "merge: <폴더> 히스토리 이관"`. 머지 커밋 1개가 생기고 재작성된 원본 커밋들이 그 아래로 들어온다.
4. remote 제거.

커밋 SHA는 새로 발급된다. 우테코 원본 PR은 fork의 원래 SHA를 참조하므로 영향 없다.

## 절차

1. `gh repo create dfdfg42/woowacourse-precourse --public` 후 `source/`에 클론. README(빈 표), .gitignore, docs/ 로 첫 커밋.
2. 본인 작업 7개를 위 "이관 방식"대로 순서대로 처리.
3. 템플릿 2개는 우테코 원본을 `--depth 1`로 `work/`에 임시 클론 후 `git archive HEAD | tar -x`로 파일만 폴더에 풀고 "템플릿 추가" 커밋 1개씩.
4. README 표를 채우고 커밋, `git push -u origin main`.
5. `work/` 삭제.

1~5 단계 동안 fork에는 아무 변경도 가하지 않는다.

## 검증 (삭제 전 필수)

모두 통과해야 삭제 단계로 넘어간다. 하나라도 실패하면 멈추고 보고한다.

1. 트리 해시 일치: 7개 각각, fork 브랜치 최신 커밋의 트리(`git ls-remote` 로 SHA 확인 후 `work/` 클론에서 재작성 전 `^{tree}`)와 모노레포 `git rev-parse HEAD:<폴더>` 가 동일. 경로 재작성은 트리 위에 폴더 한 겹을 씌울 뿐이므로 이 값이 같으면 파일 내용이 완전히 같다.
2. 폴더 히스토리: `git rev-list --count HEAD -- <폴더>` 가 fork의 (본인 커밋 + 템플릿 커밋) 수와 같거나 그보다 1 큼 (머지 커밋).
3. 원격 동기화: `git ls-remote origin main` 의 SHA가 로컬 `HEAD`와 동일.
4. 템플릿 2개: 폴더 안에 gradlew, build.gradle, src/ 가 존재.

## 삭제

- 대상 11개: java-calculator-8, java-racingcar-8, java-lotto-8, java-planetlotto-8, java-pairmatching-precourse, java-oncall-6, java-attendance-7, java-christmas-6, java-subway-path-precourse, java-attendance-7-abc5259, java-menu-precourse
- 명령: `gh repo delete dfdfg42/<이름> --yes`
- 사전 조건: gh 토큰에 `delete_repo` 권한 (2026-09-16 확인 완료).
- 실행 직전에 11개 이름을 다시 보여주고 사용자의 명시적 확인을 받은 뒤에만 실행한다. 삭제된 fork는 GitHub에서 복구할 수 없다.
- 우테코 원본에 올린 PR 4건은 fork 삭제 후에도 원본 리포에 커밋과 함께 남는다.

## 실패 대응

- 경로 재작성은 `work/` 임시 클론에서만 일어난다. 실패하면 그 클론만 지우고 다시 클론.
- 머지 실패: 모노레포에서 `git merge --abort` 또는 `git reset --hard <직전 커밋>` 후 해당 프로젝트만 재시도.
- push 전 실패: 로컬 폴더 삭제 후 처음부터 재시작. 원격에는 아무것도 없음.
- push 후 삭제 전 실패: fork가 그대로 있으므로 손실 없음.
- 검증 실패: 삭제 단계로 넘어가지 않는다.

## 결정 기록

- 전체 히스토리 이관 (`--squash` 미사용): 프리코스 이력을 커밋 단위로 남기는 것이 통합의 목적이므로.
- `git subtree add` 대신 경로 재작성: 폴더 History가 GitHub에서 보여야 하므로. SHA 보존보다 폴더별 열람이 더 중요.
- 빈 fork 2개는 템플릿만 복사: 사용자가 나중에 풀어볼 가능성을 남기되, 남의 커밋 히스토리는 가져오지 않기 위해.
- abc5259, menu 제외: 둘 다 우테코 템플릿이 아니라 타인의 풀이이므로.
- 루트 빌드 설정 없음: 각 미션이 독립 Gradle 프로젝트이고 통합 빌드가 필요 없으므로 (YAGNI).
