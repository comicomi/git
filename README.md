1. About the project
====================

The objective of this project was to build a prediction model that is able to predict weather the currently updated profile picture of currently logged in Facebook user will be noticed by his friends or not. The project workflow consisted of the following phases:
  1. Collecting the data relevant for the project using Facebook Graph API; 
  3. Preprocessing the collected data and creation of the training dataset;
  4. Choosing classifiers for building the prediction model and analysing the change of their performance with the change of the model parameters;
  5. Selecting the classifier with the best performance (determined by the value of F-measure and accuracy for the given classifier and model parameters);
  6. Application of the selected classifier to the given instance containing prediction relevant user data about the currently logged in user.

Two Facebook applications were made during the realisation of this project:
  * Facebook application for collecting the data needed to create of the training dataset (in the first phase)
  * Facebook application that provides currently logged in user to check weather his currently updated profile picture will be noticed by his friends (in the sixth phase)

Two classifiers were considered during the realisation of the forth phase of the project:
  * Naive Bayes classifier
  * SVM classifier

2. Dataset
==========

The attributes considered for each user using this application include the following:

  1. friendsNumber - represents the number of friends of currently logged in Facebook user
  2. averageNumberOfLikes - the average number of likes on profile pictures of currently logged in Facebook user
  3. tagsNumberNumber - represents the number of tagged Facebook users on current profile picture of currently logged in
Facebook user
  4. isNew - represents weather a current profile picture of currently logged in Facebook user is new (meaning the user
has uploaded it just now) or it is reused (meaning the user decided to use one of his older profile pictures as a current
profile picture). This attribute has two possible values:
    * yes
    * no
  5. gender - represents the gender of currently logged in Facebook user. This attribute has two possible values:
    * male
    * female
  6. ageRange - represents the age range of the currently logged in Facebook user based on the information about user's
birthday. The following four possible values are defined for this attribute:
    * teenagers - users under the age of 19
    * youngAdults - users between the age of 19 and 25
    * adults - users between the age of 25 and 34
    * seriousAdults - users above the age of 34
  7. time - represents part of the day time when currently logged in Facebook user has updated the currently used profile picture no matter if it is new (just uploaded) or not (reused). This information is defined based on the information about
the time of updating current profile picture. Five possible values are defined for this attribute and those include the
following:
    * morning - user updated his profile picture between 5:00AM and 11:00AM
    * midday - user updated his profile picutre between 11:00AM and 4:00PM
    * afternoon - user updated his profile picture between 4:00PM and 9:00PM
    * night - user updated his profile picture between 9:00PM and 2:00AM
    * lateNight - user updated his profile picture between 2:00AM and 5:00AM
  8. noticed - represents weather the current profile picture of currently logged in Facebook user is noticed by his Facebook friends or not. This attribute has been calculated using the information provided by the first and the second
attribute considered and it has two possible values:
    * noticed
    * unnoticed
Looking at the considered attributes, one can notice that the first three attributes are numeric, while the rest of them
are nominal. Also, it is important to emphasize that the last attribute represent the class attribute in a dataset meaning that based of the infromation obtained by the rest of considered attributes, model should be able to predict the
value of the last attribute.

3. The Facebook application for creating the dataset for training
==================================================================

![Facebook application](/FBIS/images/image.png)


Training dataset
----------------

The following table provides the example of an instance in training set:

| friendsNumber | averageNumberOfLikes | tagsNumber | isNew | gender | ageRange   | time  | noticed |
|---------------|----------------------|------------|-------|--------|------------|-------|---------|
| 235           | 35.7                 | 0          | yes   | female | youngAdult | night | noticed |

4. The Facebook application performing the prediction
=====================================================


The following table provides the example of an instance given to the final classifier for the classification:

| friendsNumber | averageNumberOfLikes | tagsNumber | isNew | gender | ageRange   | time  |
|---------------|----------------------|------------|-------|--------|------------|-------|
| 235           | 35.7                 | 0          | yes   | female | youngAdult | night |

5. Technical realisation
========================

This application was written in programming language Java, in Eclipse IDE. The following libraries were used for the
implementation:
  * [facebook4j.jar](http://facebook4j.org) - Java library for the Facebook Graph API used for obtaining the data needed to create training dataset
  * [weka.jar](http://www.cs.waikato.ac.nz/ml/weka) - Java library with a collection of machine learning algorithms included in Weka used to create a predictive classification model

6. Acknowledgements 
===================
This application has been developed as a part of the project assignment for the course [Intelligent Systems](http://is.fon.rs) at the [Faculty of Organization Sciences](http://fon.rs), University of Belgrade, Serbia.
