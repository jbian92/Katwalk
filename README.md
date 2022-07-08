# Katwalk

## App Proposal: https://docs.google.com/presentation/d/1K2V8Zn5YH0Clox_hg9f0G1c_vhf-RHoRGbP7ljlmJ_E/edit?usp=sharing 

## Sprint 1
### Completed Features
- Bottom Tab Navigation: The user can visit each of the four main pages by clicking on the corresponding tab on the bottom tab navigation.
- Closet Page: The closet page displays all of the user's clothing items in a grid format. 
- Add Clothing Item: The user can add a clothing item by clicking on the FAB on the Closet page. 
- View Clothing Item Details: The user can view the details of a clothing item by clicking on its picture on the Closet page.
- Edit Clothing Item: The user can edit a clothing item by clicking on the Edit button on the Item Details page.
- Delete Clothing Item: The user can delete a clothing item by clicking on the Delete button on the Edit Item page.
- Donation Information Links: The user can click on one of the donation information links on the Donate page and be taken to a website with more information. 

### General State of App
- Users can view their clothing items in the Closet page. They can also add, edit, and delete clothing items and see the changes reflected on the Closet page (changes are also reflected in the Firebase database). The users can also navigate to the Donation page via the bottom tab navigation and click on the arrow next to a location to donate to and get redirected to the corresponding website for more information.
- The Closet and Donate pages are implemented as described by our tasks for the first sprint while the Outfits and Favorites pages have placeholder fragments.
- The Firebase database is currently set up for one user only - everyone who uses our app are currently grouped together as one user in the database. 
- The search icon is present below the action bar on the Outfits page. This feature is not fully implemented (as planned for the first sprint). 
- On the Donate page, the number of clothing items donated and least worn clothing items are hardcoded in as placeholders; the implementation of this is planned for the second sprint. 
- The settings drawer is also shown as a placeholder to be implemented, as planned, in the second sprint. 
- For sprint 1, we mainly focused on the user experience and the functionality of the app, there are a few format compatiblity issues across devices which we will address in sprint 2. 

## Sprint 2
### Completed Features
- Outfit Generation: The user can generate 3 outfits at a time. These 3 outfits are dependent on the style of outfit that the user chooses as well as the current weather that is obtained from the weather API we used.
- Donate Button: The user can click on the Donate button on the Item Details screen for a clothing item, which will delete the item from their closet and increment the donation counter shown on the Donate screen.
- Least Worn Clothing Items: The user can see the 3 least worn clothing items from their closet on the Donate screen.
- Favorite Outfits: The user can favorite, and thus save, a generated outfit or create their own favorite outfit. 
- Filter: The user can filter through their closet as well as their favorite outfits by weather and/or style.
- Search: The user can search for a clothing item by name. 

### General State of App
- The main features of the app listed below are implemented as described by our tasks for sprint 1 and 2.
  - Users can generate outfits based on the current weather and chosen style.
  - Users can add, edit, delete, and view their clothing items on the Closet page.
  - Users can favorite outfits and see their saved, favorite outfits on the Favorites page.
  - Users can create their own favorite outfits.
  - Users can see how many clothing items they have donated, their 3 least worn clothing items, and click on donation information links to learn more about how to donate their clothes.
- The app is locked in portrait mode. 
- The settings drawer is currently implemented as a placeholder for the future. This feature is considered to be a nice-to-have. 

