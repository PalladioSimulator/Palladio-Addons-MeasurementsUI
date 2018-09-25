# EnProPalladioCode
Code Repository for Palladio UI Extension

About the project structure:

All code is located within the "org.palladiosimulator.measurementsui"-project. All functional code is separated between different projects, which are all located in the "bundles"-folder.
Tests belong to the "tests" folder. The main maven-tycho pom.xml can be found in the releng-folder  in the project "org.palladiosimulator.measurementsui.configuration". When you pull and import the project, make sure that you enable the search for nested peojects from the import wizard, so you get all the projects. 

How to run this project: 
- Run a new runtime configuration on any of the sub-projects from the bundles folder (e.g. on org.palladiosimulator.measurementsui.data)
- Import a working Palladio project (some older projects may not work)
- Open the "Measurements Dashboard"-View (either via quick search or via the "Show View"-Window from the menu). You should see your imported project in the dashboard and you can work with it.

Some problems which may occur:
- If there are lots of errors of classes which can not be found, you may miss the Parsley-Plugin
- If there are a few errors stating that projects are missing required source folder: "emfparsley-gen", perform a project clean via Eclipse Bar -> Project -> Clean...




How to run a maven build:
- Run a new maven build configuration on the "org.palladiosimulator.measurementsui"-project with following settings:
Goals: clean verify
Profiles: sonar
 This may take a while, since there are several parsley-files (based on xtext) from which code needs to be generated. 
