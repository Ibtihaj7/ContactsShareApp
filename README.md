# Contacts Sharing App: Android Project Document

## Introduction
The Contacts Sharing App is an Android application that provides a simple and intuitive interface for managing and sharing your contacts. This README document provides an overview of the app's features, functionality, and how to use it.

## Features
Main Features
1. Display Contacts: The app displays a list of contacts categorized into two tabs: "All Contacts" and "Favorite Contacts."
2. Search Contacts: Users can search for specific contacts using a search box, making it easy to find and access contacts quickly.
3. Add New Contacts: Users can add new contacts by providing their name and phone number in the "Add New Contact" screen.
4. Favorites: Users can mark contacts as favorites by tapping the heart icon next to a contact's name. Favorited contacts are displayed separately in the "Favorite Contacts" tab.

Additional Features
1. Contact Sharing: Users can share contact information with others through the app, making it convenient to exchange contact details.
2. Persistence: The app uses SharedPreferences to save contact information, ensuring that contacts are stored even after the app is closed.


## Screenshots
<img width="207" alt="Screen Shot 2024-05-24 at 5 20 30 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/fe078f64-0eca-4cc1-ab7f-ccf7dc2a37d9">
<img width="205" alt="Screen Shot 2024-05-24 at 5 20 49 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/ea051e11-dd1a-40c0-9e9d-384165e5a487">
<img width="206" alt="Screen Shot 2024-05-24 at 5 21 00 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/7f89a568-6828-41a1-8119-ebaab5088605">
<img width="207" alt="Screen Shot 2024-05-24 at 5 21 49 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/4b8c665e-77a0-4d67-8727-bb89fbfd323d">
<img width="211" alt="Screen Shot 2024-05-24 at 5 22 03 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/dbcc2f9b-8688-4b51-a49b-40f2b279e7e5">
<img width="207" alt="Screen Shot 2024-05-24 at 5 22 26 PM" src="https://github.com/Ibtihaj7/ContactsShareApp/assets/92644947/2bdea87b-1f54-4835-85fc-7c80d012ffa2">  

## Usage
1. View Contacts:
   - Launch the app, and you will see two tabs: "All Contacts" and "Favorite Contacts."
   - The "All Contacts" tab displays a list of all your contacts.
   - The "Favorite Contacts" tab displays only your favorite contacts.
2. Search Contacts:
   - Use the search box at the top to search for specific contacts by name.
3. Add New Contacts:
   - Tap the "Add" button (represented by a plus icon) in the top-right corner to add a new contact.
   - Fill in the contact's first name, last name, and phone number, and tap "Save" to add the contact.
   - The new contact will appear in the "All Contacts" tab.
4. Mark Favorites:
   - To mark a contact as a favorite, tap the heart icon next to their name in the "All Contacts" tab.
   - Favorited contacts will appear in the "Favorite Contacts" tab.
5. Share Contacts:
   - Tap the share icon next to a contact's name to share their contact information with others.
     
### View Contacts
#### - First Activity
- Display two tabs: 
  - The first tab for showing a list of all contacts.
  - The second for showing the favorite contacts only.
- User can search for contacts using a search box.
- Each contact item includes two actions:
  - The first action adds it to favorites.
  - The second is to share the contact.
- A plus menu item to open the second activity.

#### - Second Activity
- Display a form to input a contact's name and phone number.
- There should be a button to save the contact information.
- When saving a new contact, it should appear in the first activity.
- Save the contact information in device storage using shared preferences.

## Getting Started
1. Clone the repository to your local machine.
2. Open the project in Android Studio to access the codebase.
3. Build and run the application on an emulator or physical device to start organizing your tasks.  
