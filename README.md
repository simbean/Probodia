# Probodia
## SWM13 project - protect your body from diabetes

![KakaoTalk_Photo_2022-12-14-18-23-44](https://user-images.githubusercontent.com/67853497/207557449-15c05daf-d888-4710-a63f-421bb96df054.jpeg)

기존 앱은 당뇨 일지 기록을 꾸준히 하기 어려웠고 환자가 자신의 생활습관을 능동적으로 조절하기 어려웠습니다. 프로바디아는 챌린지 기능을 통해 꾸준한 당뇨 일지 기록의 어려움을 해결하고자 하며, 음식에 따른 당뇨 지수를 산출하여 사용자들의 식단 관리를 돕고자 합니다.

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
## Tech Stack
<img src="https://img.shields.io/badge/PyTorch-EE4C2C?style=for-the-badge&logo=PyTorch&logoColor=white"> <img src="https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=FastAPI&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> 
