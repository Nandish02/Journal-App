# A4-JournalApp
- Developed the App as a part of the Software Development course CS F314

## Version of Android Studio Used
Android Studio Giraffe|2022.3.1 Patch 1

## App Description
JournalApp is an Android app that allows the user to record daily activities like that in a journal. The user can add new entries by clicking on the 'plus' button, which opens a details journal entry view where the user can enter the title of the app and select the date, start time, and end time. These entries are saved in a room database and can be viewed in a scrollable list view. The user can also edit, delete, or share entries via the app.

## App Screenshots
<table>
  <tr>
    <td><img src="https://github.com/Nandish02/Journal-App/assets/94218870/0aba4450-1405-4e97-a1be-f2d4a7ac5250"></td>
    <td><img src="https://github.com/Nandish02/Journal-App/assets/94218870/c1c96d76-373c-4508-8f12-b99baeea4df5"></td>
    <td><img src="https://github.com/Nandish02/Journal-App/assets/94218870/884fb704-d036-45cf-a361-5081d74584ec"></td>
    <td><img src="https://github.com/Nandish02/Journal-App/assets/94218870/f0c1b074-16bf-446d-a91a-13484737f9c"></td>
    <td><img src="https://github.com/Nandish02/Journal-App/assets/94218870/351603cd-361c-45ef-a204-b030a63b86c1"></td>
  </tr>
</table>

---
## Task-1
One change was to be made for the info button in the menu bar which redirected to the info fragment created by the user. The given test case failed since the location for the click was not properly written, hence it pressed the wrong button which resulted in an error. The test case was corrected by changing the location of the click

## Task-2
Delete functionality was added in the journal entry dao interface which was further overridden when the user opted to delete the entry. Further changes related to delete query were also made to JournalEntry, JournalEntryDao, and JournalRepository classes. The database included columns like UID, title, date, start time and end time for the particular entry.

## Task-3
Delete button has been added to the menu bar of EntryDetailsFragment which then prompts a dialog to the user whether he/she wants to delete that particular entry and on pressing yes, the entry gets deleted from the database. It also shows a toast confirming that the entry has been successfully deleted. This method was implemented in onOptionsItemSelected function for the menu bar.

## Task-4
On the EntryDetailsFragment view menu bar, a SHARE button has been added. It prompts the user to share the task details through any text sharing app on the phone like whatsapp, email, normal text messages etc. This thing was implemented using intents, and intent extras which is used to share the data used in the app. After finishing this, the user return backs to the app.

## Task-5
The INFO button on the EntryList fragment menu bar will prompt a new screen to the user which shows the information related to the app. This is done by adding a new fragment to the package, and loading that fragment to the screen when info is pressed in the menu bar.

## Task-6
- Ensured that the app is accessible to visually impaired users by using Android's TalkBack feature, which converts the UI into audio. When navigating the app, the device reads out loud wherever is touched or selected.
- TalkBack provides spoken feedback while swiping through settings or navigating through the app, making the UI accessible for visually impaired individuals.
- Applied recommendations from the Accessibility Scanner, such as adjusting text scaling and enhancing text contrast to meet suggested contrast ratios. These changes were implemented in the app's UI.
---
## Time Taken to develop the app
- Approximately 40-45 hours were spent on developing the app.
---
