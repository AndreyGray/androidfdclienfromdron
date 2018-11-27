As example of my code.
	This is small facebook client for watching user photos. Client shows user information after connecting. After clicking a button "Display all albums", it offers to view the user's albums. Thereafter selecting an album, you can view its content. And then we can see the full-screen photo with some details.
	That clien has been created as example of my code. This app correct work at Api level 19 and higher. App has tested at Samsung J5 Android Nougat 7.1.1, Samsung SM-G355H Android 4.4.2 and before  facebook linked in app at Galaxy Nexus API 26 Android Oreo 8.0 virtual device.
Used technologys:
1. For authorization and receipt AccessToken  I use a loginbutton(facebook client mast instaled on your smartphone).
2. For downloading data from facebook uses Android Api facebook(request - respons Api Graph) in addition class DownloadDataHelper.
3. I created extensible model for users, albums and photos.
4. Recicler View and GridLayout Manager for views to list table albums and photos.
5. ViewPager used for showed fullscreen photos.
6. Downloading images implemented with addition Picasso library version2.71828.
7. I used singelton class as a data store which exists while the application is running.
8. Saving images implemented using library Picasso and SaveImageHelper class.


