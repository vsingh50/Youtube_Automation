Youtube Automation, I have implemented 4 TestCases here.
testCase01: Go to YouTube.com and Assert you are on the correct URL. Click on "About" at the bottom of the sidebar, and print the message on the screen.
testCase02: Go to the "Films" or "Movies" tab and in the “Top Selling” section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked “A” for Mature or not. Apply a Soft assert on the movie category to check if it exists ex: "Comedy", "Animation", "Drama".
testCase03: Go to the "Music" tab and in the 1st section. Print the name of the playlist on the most right. Soft Assert on whether the number of tracks listed is less than or equal to 50.
testCase04: Go to the "News" tab and print the title and body of the 1st 3 “Latest News Posts” along with the sum of the number of likes on all 3 of them. No likes given means 0.

Wrappers.java is the wrapper method utility class.
testCases.java is where I have implemented all the above listed 4 test cases.
