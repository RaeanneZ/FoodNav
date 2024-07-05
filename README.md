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

### 2. **Existing App user - A non-tech retired teacher**

   As an existing FoodNav member, I want to be able to count my daily calories so that I can lose weight.
   
   **Acceptable criteria**
   
    - To be able to log food easily
    - To have clear and simple display to show food macronutrients
    - To be able to browse through list of food options and their calories

### 3. **Existing App user - A working adult who always dine out but want to quickly get food of healthier choice**

   As an existing FoodNav member, I want to be able to check what are the healthier choice of food  so that I can be health conscious despite having to dine out often. 
   
   **Acceptable criteria**
   
    - To be able to log food easily
    - To have clear and simple display to show food macronutrients
    - Easier way to lookup list of food calories 


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
   
	UI includes:
    - Display of macros of each food added
    - Tracking of users’ sugar level after each meal (Diabetic Friendly Diet Plan only)

2. **Customize your Goals / Diet plan**
   
Choose your desired diet plan and allow us to support you in making the change.
Other than those popular diet plans, we have identified High Cholesterol Friendly Diet,  High Blood Pressure Friendly Diet and Diabetic Friendly Diet. These are the most common long term conditions that 	Singaporeans suffer from. 

This prototype will primarily focus on Diabetic Friendly Diet which includes:

    - Proposed target of daily intake of carbs,  proteins and fats based on gender. Diabetic Friendly Diet is low carbs(<=130g/2000kcal), low sugar(<=50g/2000kcal) and limited processed food.
    - Proposed target total calories intake based on gender
    - Additional field to track users’ sugar level after each meal under food log


3. **Track your progress**
   
   Users can track and analyze their daily nutrition and calories intake through:
   - Dashboard, it provides a quick summary at a glance on Today progress as well as a summary view of each meal
   - Daily Reminders to ensure users keep to their desired diet plan


4. **Dark Mode**

   Users are able to switch between light and dark mode

### Stage 2

**Raeanne**

**Food2Nom**: To quickly choose food of lower calories, simply upload a picture of the menu. Users can either choose an existing photo from the gallery or take a photo to view food or similar food listing available in FoodNav Database, highlighting which are the healthier choices recommended based on Diet plan selected. This allows the users to get a quick peek and makes a better choice for each meal. 

**Add New Food**: If user cannot find food items in the Search Food Page, they can choose to to add new food to the database. They can either manually input the values or scan the Nutrition Labels via the camera.
Text recognition will auto input the values if detection is successful.

Considerations for Diabetic Friendly Food includes:

	- Amount of saturated Fats, Sodium and added sugar should be <10% per serving
	- Other nutrients per servings should be in the range of 6% - 18%
	- Fruits over fruit juice, 2 servings of fruits per day but no more than 1 servings each time
	- High fiber food helps to manage blood glucose (Wholegrains, vegetables, beans and nuts)
	- Opt for drinks with <= 25% sugar



**Sian Kim**

Feature in Search Bar that suggest words from the dictionary that match the text entered in the search field before the user finishes entering their query. These suggestions are valuable because they can effectively predict what the user wants and provide instant access to it.

**Jovan**

Feature to allow items in food log to be scrolled horizontally, in addition to vertically, which would reveal icons that have functions such as delete or favourite. The favourites page would be a separate page that shows all the "favourite" items. These improve UX as it makes adding and deleting food more convenient.

**Hong Rong**

Notifications are essential features that help keep users informed and engaged by sending them timely updates or reminders. These notifications serve as gentle nudges to keep users connected to relevant information or actions within an application or platform. By delivering messages directly to users, notifications enhance user experience and increase user engagement by ensuring they stay informed and up-to-date with the latest developments, events, or tasks.

## Feature Test 

1. First time user can sign up as member
2. After signing up as member successfully, user can login with email and password
3. Able to add food to meal via typing food name in search bar
5. Able to remove food from meal
6. Update of food details (macros, calories) on Food log page as well as dashboard - meal summary card
   
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
    - Login Page (Jovan)
    - MSSQL Database Setup (Raeanne) 
    - Componentized application bottom navigation bar (Hong Rong)

**Week 4**
1. Start Development work on:
    - Select Diet Plan and related panel (Sian Kim)
    - Sample Code to link Database to Application (Raeanne)
    - Backend functions to access LoginInfo Table and Account Table (Raeanne)
    - Meal Log Page (Hong Rong)
2. Others continue with Week 3 Work


**Week 5**
1. Checked in and merge codes from Week 3 and 4
2.  Start Development work on:
    - SignUp and profile Page (Jovan)
    - Recommended Macros & Calories after diet plan selection, Search Food Page (Sian Kim)
    - Dashboard and Add Food Page (Hong Rong)
    - Backend functions to access Food table, DietPlan table (Raeanne)
    - Migrate Local Database to MS Azure (Raeanne)

**Week 6**
1. Check in and merge all codes from Week 5
2. Start Development work on:
    - Account Page (Jovan)
    - Food Search Page (Sian Kim)
    - Dashboard and Add Food Page (Hong Rong)
    - Backend functions to access Meals table (Raeanne)

**Week 7**
1. Backend functions to access Food table (Raeanne)
2. Frontend integration to backend
3. Perform full application testing and troubleshooting
4. Stage 1 Submission

**Week 8**
1. Prepare Slides for Week 11 Presentations (Raeanne)
2. Modify and refine frontend (UI) to dynamically during runtime to reflect model change (Raeanne) 
   
**Week 9**
1. All Stage 1 functions testing and bug fix to ensure right behavior. (Raeanne)
2. Stage 2 Food2Nom implementation (Raeanne)
3. Design application logo and banner (Raeanne)

**Week 10**
1. Rehearse for Presentation 23 June 2024
2. Stage 2 function unit test (Raeanne)

**Week 11**
1. Rehearse for Presentation on 24th Jun 2024
2. Stage 2 Add New Food implementation (Raeanne)
3. Conducted application freeplay testing with invited personnel, including team members.
4. Compile bug list. (Raeanne)
5. Modify and resolve bugs. (Raeanne)
6. Submit application to Google for approval on 29th June 2024.

**Week 12**
1. Update Github with new baseline for fellow members to integrate Stage 2 features. (Raeanne)

**Week 13**
1. Update Google Play Store application on 12 July 2024

**Week 14**

**Week 15**

**Week 16**
1. Rehearsal for Stage 2 Presentation on 29th July 2024
2. Stage 2 Presentation on 30th July 2024


## Group Members Roles and Contributions
### Raeanne

**Project Lead**

I am responsible in overseeing the project which includes:

1. Defining the scope and deliverables of the project
2. Outlining the course of action and establishing a timeline for this  project
3. Delegate tasks and track progress of members to ensure tasks are completed on schedule
4. Development workspace (GitHub) setup
5. Manages continuous code integration of the team to ensure smooth collaborative workflow 
6. Create and continuous update of documentations, e.g GitHub Readme
7. Communicate effectively with team members


**UI/UX Design Lead**

As the UI/UX design lead, I was responsible for the overall product design. 
I work with my team to brainstorm and ideate on the application features. The team translated the initial discussed requirements via  a low fidelity figma prototype. I refined the design and functionality details as well as the color scheme for the high fidelity prototype to better demonstrate the end-product application. This gives the team a better reference when developing the application.

During the course of development, I regularly meetup with the team members to ensure that their UI stays true to the figma prototype and to offer Frontend technical support, like, Food Search implementation and others if they have difficulty.

I will also be responsible for the development of application logo icon and banner.

**Database Designer/Architect**

I am responsible for the design and setup of the database required for the mobile application, FoodNav. Applying knowledge from Year 1 - Database Module, I started with data modeling and created an ER Diagram (refer to Appendices below) for visual reference before the setup of MSSQL for this project. I am also responsible for the migration of local databases to MS Azure (cloud) for accessibility.

**Developer**
For stage 2 advanced feature, I implemented Food2Nom page. I explored and incorporated Google MLKit OCR API to recognise text from photos as a key to search our food database. Extended the use of Google OCR to recognise Food Nutrition Label and process text captured to autofill 'New Food' form so that user can easily add to FoodNav Database.

*Database*
I am responsible for the coding of all functionality that interacts with the database. Adopting OOP, custom Event Listener and MVC software design patterns, the codes are organized in ways such that all database handling is transparent to the Activity classes. I worked closely with the team members to help them understand with psuedo codes and ease into the process of integrating to the backend.

*Tester*
I created test cases 'Stage 1 Features - Test Cases' and 'Stage 2 Features - Food2Nom and Add New Food'. 
To officially baseline the STAGE 1 version, I compiled and resolved the list of bugs found (based on Stage 1 -Week 7 submission version), including identifying and refining incomplete features. 
To ensure application stability, I conducted freeplay testing with families and friends, including team members before sending the application for Google approval.


### Sian Kim

**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. 

**Frontend Developer**

I am responsible for the UI development of:
1. Diet selection page layout (currently only feature Diabetic Friendly Diet)
2. Sugar tracking option page layout
3. Display of suggested micronutrients and calories intake based on gender and diet plan layout

I was tasked  to assist other members in refining the UI layout of the following: Dashboard page, Log Food Product, Add Food Page. Other involvement includes debugging to ensure responsiveness of application.


### Jovan

**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. I updated my relevant parts in this GitHub documentation.

**Frontend Developer**

I am responsible for the UI development and implementation of frontend functionality of the following:
1. FoodNav startup page
2. Login Page
3. Sign Up Page
4. Profile Page
5. Account Page

I worked closely with the UI designer to ensure design consistency and I performed testing to ensure quality and responsiveness of the pages. More than often, I worked alongside other team members to resolve unexpected issues through troubleshootings without compromising my personal work schedule.


### Hong Rong

**Project Member**

As a project team member, I participated in all the project discussion and contributed my ideas during the design phase. I worked with other team members to build the figma prototype to test the feasibility of the concept we had ideated. I updated my relevant parts in this GitHub documentation.

**Frontend Developer**

I am responsible for the frontend UI and functionality development and testing of the following:
1. Dashboard page layout
2. Navigation bar (Fragments)
4. Add Food Page layout
5. Meal Log Page layout

I checked with the UI designer on the layout and color scheme to ensure consistency while working independently to resolve difficulties faced in the course of UI development like componentizing. I performed testing to ensure responsiveness of application. 


## Appendices
[Stage 1 Figma Prototype](https://www.figma.com/design/bbhzqmDpylFzYIMlHsy6BY/Untitled?node-id=0-1&t=qKwwhxSDWoxE48rR-0))

[Stage 2 Figma Prototype] *(To be updated)*
[Stage 2 Features Screenshots] *(To be updated)*

## Credits
### Raeanne

General:

**Run Apps on Hardware device**
https://www.digitaltrends.com/mobile/how-to-get-developer-options-on-android/
https://developer.android.com/studio/run/device
