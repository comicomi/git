1. About the project
====================

The objective of this project was to develop Facebook application that could predict weather the profile picture of a user 
will be noticed by his friends or not. 

2. Dataset
==========
The attributes considered for each user using this application include the following:

1.friendsNumber - represents the number of friends of currently logged in Facebook user
2. averageNumberOfLikes - the average number of likes on profile pictures of currently logged in Facebook user
3. tagsNumberNumber - represents the number of tagged Facebook users on current profile picture of currently logged in 
Facebook user
4. isNew - represents weather a current profile picture of currently logged in Facebook user is new (meaning the user
has uploaded it just now) or it is reused (meaning the user decided to use one of his older profile pictures as a current
profile picture). This attribute has two possible values:
*yes
*no
5. gender - represents the gender of currently logged in Facebook user. This attribute has two possible values:
* male
* female
6. ageRange - represents the age range of the currently logged in Facebook user based on the information about user's
birthday. The following four possible values are defined for this attribute:
*teenagers
*youngAdults
*adults
*seriousAdults
7. time - represents the time when currently logged in Facebook user has updated the currently used profile picture 
no matter if it is new (just uploaded) or not (reused)
8. noticed - represents weather the current profile picture of currently logged in Facebook user is noticed by his Facebook
friends or not. This attribute has two possible values:
*noticed
*unnoticed
