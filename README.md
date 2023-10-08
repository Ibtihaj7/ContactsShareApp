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
   
## Getting Started
To get started with the Contacts Sharing App, follow these steps:
1. Clone the Repository: Clone this GitHub repository to your local machine using the following command:
    ```shell
   git clone https://github.com/your-username/contact-sharing-app.git
2. Open in Android Studio: Open the project in Android Studio or your preferred Android development environment.
3. Run the App: Connect your Android device or use an emulator, then run the app on the device.
4. Explore the App: Once the app is running, you can explore its features, add new contacts, mark contacts as favorites, and search for contacts.
    
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
