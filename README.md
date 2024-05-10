# Mobile Application Development
### AY2024 Semester 1 Assignment
by Team 2, P03 

## Group Members
Zou Ruining, Raeanne (S10258772G)

Cing Sian Kim (S10257716F)

Jovan Tan Zhi Yao (S10259920E)

Tan Hong Rong (S10262513J)

## Application Overview
### Objective
FoodNav is an android mobile application designed to help you learn about your eating habits, to find motivations and give support to your decision to make smarter food choices based on the diet that best suits your health needs. 

### Target Audience
This app targets male and females of age 18 - 70 who are health conscious. They are interested in keeping a food journal as well as appreciate the different tools and support they can get in sticking to their desired diet plan. 

## User Stories
### 1. First Time User - Newly diagnoised Diabetic Patient

   As a newly diagnosed with diabetes, I want to sign up an account with FoodNav so that I can select the Diabetic Friendly Diet Plan and  start a food log to monitor my daily macronutrients intake.

   **Acceptable criteria**
   
    - To be able to log my sugar level after each meal
    - To be able to track my food intake and macros daily
    - To have suggestions on what are the healthier choice of food specifically for diabetic condition
    - To have diabetes related food/lifestyle articles

### 2. **Existing App user - A non-tech retired teacher**

   As an existing FoodNav member, I want to be able to count my daily calories so that I can lose weight.
   
   **Acceptable criteria**
   
    - To be able to log food easily
    - To have clear and simple display to show food macronutrients
    - To be able to browse through list of food options and their calories

## Design Considerations
1. **Simple and intuitive UI/UX**

   Easy to use tools will motivate users to use FoodNav frequently. Simple and clean layout allows ease of reading and input of information thus improving user experience. 
   Anchoring a bottom navigation bar not only minimizes finger movement across the phone, it allows one touch quick access to key functions. Whitespace is generously catered, and coupled with single touch options using pictorial instead of words creates a minimal look and feel. 

3. **Light but contrasting colors to the white background.**

   Considering the large age group of targeted audience, it is important that the color scheme not only attracts the younger group, it still ensures readability for the elder group of audiences. The colors       chosen here are a pleasant and refreshing combination of cool colors (purple). Two tones of purple are used to highlight Call-To-Action, namely, deep purple is used for the anchored navigation bar while lavender is used for buttons. Important information like  calories and macronutrients are uniquely color coded across all pages for ease of identification.

## Application Features
### Stage 1
1. **Log Food**
   
   Food logging allows users to understand and hold accountable to what they eat.
   It is a simple and intuitive tool to make food tracking fast.  
   To add food, user can:
    - Type in search bar
    - Scan food packaging  barcode
    - Take a picture
   
	UI includes:
    - Display of macros of each food added
    - Tracking of users’ sugar level after each meal (Diabetic Friendly Diet Plan only)
    - Suggestion of healthier alternative for food added based on diet plan chosen

3. **Customize your Goals / Diet plan**
   
   Choose your desired diet plan and allow us to support you in making the change.
   Other than those popular diet plans, we have identified High Cholesterol Friendly Diet,  High Blood Pressure Friendly Diet and Diabetic Friendly Diet. These are the most common long term conditions that   Singaporeans suffer from. This prototype will primarily focus on Diabetic Friendly Diet which includes:
   
    - Proposed target of daily intake of carbs,  proteins and fats
    - Proposed target total calories intake
    - Additional field to track users’ sugar level after each meal under food log


5. **Track your progress**
   
   Users can track and analyze their daily nutrition and calories intake through:
   - Dashboard, it provides a quick summary at a glance on Today progress as well as a summary view of each meal
   - Daily Missions achievement to motivate users’ in keeping to their desired diet plan

6. **Fooducate**
   
   Get health insights and useful tips so that users can make better food choices based on their diet plan. Users can type to search for the related food articles that they are interested in.

7. **Food2Nom**
    
   Easy to use tool to search on food categories when deciding which variant is a healthier choice. Users can type food keywords to search for the related list of variants so they can make a healthier choice with all the macros and calories information. Recommendations will be made based on users’ diet plan.  

### Stage 2

**Raeanne**

Feature to allow user to use camera function to capture texts on ingredients of food, then display nutritional information of the food. 

**Sian Kim**

Feature in Search Bar that suggest words from the dictionary that match the text entered in the search field before the user finishes entering their query. These suggestions are valuable because they can effectively predict what the user wants and provide instant access to it.

**Jovan**

Feature to allow items in food log to be scrolled horizontally, in addition to vertically, which would reveal icons that have functions such as delete or favourite. The favourites page would be a separate page that shows all the "favourite" items. These improve UX as it makes adding and deleting food more convenient.

**Hong Rong**

*Add feature here*

## Feature Test 

1. First time user can sign up as member
2. After signing up as member successfully, user can login with email and password
3. Able to add food to meal via:
    - Type food name in search bar
    - Scan food barcode
    - Take food photo
5. Able to remove food from meal
6. Update of food details (macros, calories) on Food log page as well as dashboard - meal summary card
7. Fooducate will return search result of typed in food name in search bar
8. Food2Nom will return search result of typed in food name in search bar or photos snapped
   
## Development Schedule
**Week 2** 
1. Select topic, brainstorming and Ideate.
2. Discuss and start work on Figma prototype
   
**Week 3**
1. Complete Figma High Fidelity Prototype
2. Cleared consult on Figma prototype for Stage 1 Features
3. Brainstorm on Stage 2 Features
4. Setup Github development environment and define services API reqiured.
5. Delegate work and start development on:
    - Login Page, Sign up Page and Account Page
    - Members Database
    - Componentized application bottom navigation bar 

**Week 4**
1. Check in and Merge codes
2. Perform Test Plan (1)
3. Start Development work on:
    - Select Diet Plan and related panel
    - Dashboard
    - Fooducate

**Week 5**
1. Food Log and related pages
2. Food2Nom
3. Complete unit testing of all features

**Week 6**
1. Check in and merge all codes
2. Perform full application testing

**Week 7**

**Week 8**
1. Prepare Slides for Week 11 Presentations
   
**Week 9**
1. Select topic, brainstorming and Ideate.
   
**Week 10**
1. Rehearse for Presentation
2. Submission of project to Google Play for approval

**Week 11**

**Week 12**
1. Prepare Slides on Stage 2 Presentation

**Week 13**

**Week 14**

**Week 15**

**Week 16**

*To be Updated*

## Group Members Roles and Contributions
### Raeanne
**Project Lead**

I am responsible in overseeing the project which includes:
  1. Plan project schedule
  2. Delegate task and track progress of members 
  3. Development workspace (GitHub) setup
  4. Documentations
  5. Communicate effectively with team members

**UI/UX Design Lead**

As the UI/UX design lead, I was responsible for the overall product design. 
I work with my team to brainstorm and ideate on the application features. The team translated the initial discussed requirements via  a low fidelity figma prototype.  I refined the design and functionality details as well as the color scheme for the high fidelity prototype to better demonstrate the end-product application. This gives the team a better reference when developing the application.

**Developer**
I am responsible for the development and testing of the following:
*To be Updated*

### Sian Kim
**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. I helped to update this GitHub documentations as I worked on this project.

**Developer**

I am responsible for the development and testing of the following:
*To be Updated*

### Jovan
**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. I updated my relevant parts in this GitHub documentation.

**Developer**

I am responsible for the development and testing of the following:
*To be Updated*

### Hong Rong
**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. I updated my relevant parts in this GitHub documentation.

**Developer**

I am responsible for the development and testing of the following:
*To be Updated*

## Appendices
[Stage 1 Figma Prototype](https://www.figma.com/file/pNx3FUUF3zOFvgexIocBwP/MAD-Wireframe?type=design&node-id=0-1&mode=design&t=WX9BKlbXHOVR2sC1-0)
[Stage 1 Features Screenshots] *(To be updated)*

[Stage 2 Figma Prototype] *(To be updated)*
[Stage 2 Features Screenshots] *(To be updated)*

### Credits/ References 
*(To be updated)*
