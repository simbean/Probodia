### 이 프로젝트는 소프트웨어 마에스트로의 지원을 받아 제작되었습니다.

# Probodia - protect your body from diabetes
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 심재빈 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 박서진 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 이지상 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
|:---:|:---:|:---:|  
| - 팀장 <br> - AI  <br> - [GitHub](https://github.com/simbean) | - Back-end<br>- Infra <Br> - [GitHub](https://github.com/werad12) | - 기획자 <br>- Client <br> - [GitHub](https://github.com/jisang0706)

## Tech Stack
<img src="https://img.shields.io/badge/PyTorch-EE4C2C?style=for-the-badge&logo=PyTorch&logoColor=white"> <img src="https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=FastAPI&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> 

# Detail Role
심재빈
이지상
박서진

# 개요
![KakaoTalk_Photo_2022-12-14-18-23-44](https://user-images.githubusercontent.com/67853497/207557449-15c05daf-d888-4710-a63f-421bb96df054.jpeg)

<details>
<summary>프로바디아는...</summary>

사용자들이 능동적으로 건강 정보를 기록 할 수 있도록 동기부여를 제시하며 음식 성분을 통해 당뇨의 위험도를 판단하여 식단 관리에 도움을 주는 서비스를 제공하는 안드로이드 어플리케이션이다.
</details>

# Pain point

<details>
<summary>저희 팀이 생각한 당뇨병 환자들의 Pain Point는..</summary>
    
당뇨병은 30세 이상 성인 중 13.8%(494만명)가 앓고 있는 병입니다. 당뇨 일지를 기록한 사람들은 작성하지 않은 사람들에 비해 당화혈색소 감소율이 2배 이상의 차이를 보여주지만, 당뇨병 환자중 헬스 케어 어플리케이션을 사용할 의향이 있는 사람이 약 242만명, 헬스 케어 어플리케이션을 다운로드한 사람은 100만명으로, 어플리케이션을 사용하여 당뇨 관리를 하는 사람들이 많지 않습니다.
또한 기존 앱은 당뇨 일지 기록을 꾸준히 하기 어려웠고 환자가 자신의 생활습관을 능동적으로 조절하기 어려웠습니다. 
</details>
    
# Solution

<details>
<summary>프로바디아는...</summary> 
1. 이런 Pain point를 해결하기 위해 챌린지를 통해 사용자가 꾸준한 기록을 할 수 있도록 유도할 뿐만 아니라, 이를 잘 수행한 사용자에게 포인트를 지급함으로써 사용자의 보상심리를 자극하여 동기부여에 힘을 보태고자 만들어진 어플리케이션 입니다.
2. 또한 기록 및 건강 데이터의 시각화를 넘어서, 당뇨 관리에 가장 중요한 식단 관리를 돕고자 음식의 성분을 분석하여 당뇨 지수를 제공함으써 사용자가 식단이 당뇨에 어떤지 파악할 수 있도록 하고, 더 나아가 좋은 당뇨지수를 가진 비슷한 분류에 속하는 음식을 추천함으로써  사용자들이 직접 식단 관리를 구성하는 것에 도움을 주는 어플리케이션입니다.
</details>
    
# Structure

## System Architecture
![시스템아키텍처 drawio (1)](https://user-images.githubusercontent.com/67853497/207553980-5b123f56-e62f-4691-b637-0a02a436737e.png)

## Project Structure

```
.
├── AI
│   └── foodClassifier
├── Backend
│   ├── apigateway-service
│   ├── challenge-service
│   ├── config-service
│   ├── discovery-service
│   ├── food-service
│   ├── foodclassifier-service
│   ├── gl_predict-service
│   └── user-service
└── Client
    └── Probodia
    
```

# 결과물
시연영상 넣기

# ERD
ERD 넣기

# 왜 이 기술을 사용했는가?
각자 적기


# 성과 및 회고
Playstore 15등 사진 넣기


