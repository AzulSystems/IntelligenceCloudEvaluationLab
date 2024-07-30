# Azul Partner Lab for Azul Intelligence Cloud

## Intro

Welcome to the Azul Partner Lab for the Azul Intelligence Cloud (AIC). This repo will help you quickly get started with AIC and two of its key features: Azul Vulnerability Detection (AVD) and Azul Code Inventory (ACI).

## Goals

- Make sure you can onboard Petclinic into the AIC.
- Make sure you can accomplish these labs using these 3 items:
    1. this README (please follow it end to end and don't skip any steps :))
    2. our Public Docs at https://docs.azul.com/intelligence-cloud/
    3. the instructions for importing an AVD report into a spreadsheet - they are at the end of this README.
- End up with a running instance in AIC for both AVD and CI. Class-level CI is enabled by default.
- Ensure that you can generate an AVD report in the product UI and that if needed you can convert it to a spreadsheet (your Azul SE will assist you with this)
- Ensure that you can generate an ACI report using the Intelligence Cloud Client

## Prerequisites

- Mac or Linux VM (or Windows with WSL) with Internet access
- Installed git, maven, recent Zulu v17+, recent non-Azul Java v17+
- Azul Intelligence Cloud access

## Getting Started

1. Clone this repo (% git clone [https://gitlab.azulsystems.com/salesengineering/se-avd-lab.git](https://github.com/rstatsinger/PetclinicAVD.git))
2. Log in to AIC and set up a Forwarder on your machine using the directions in our public docs - Forwarder setup is in the Settings portion of the UI. I suggest you download the configuration you've created into forwarder-settings.properties - you will want to edit that file and set forwarder.keystore.password to some value. Start the forwarder and verify that it’s running - you can use the **runForwarder** script from this repo as an example startup script

## Lab 1 - Onboard Petclinic into AIC using Zulu/Core and Run AVD Report

0. Make sure Zulu is your active JDK
1. Build and run Petclinic using Zulu/Core - **% source build** then **% source run** -  verify that you see the Petclinic UI at http://localhost:8080
2. Shut down Petclinic, then onboard it into AVD: **edit the **runWithAVDZulu-SETME** script to set tags, including AppEnv**, then run the script - verify your Petclinic instance is there in the AVD UI - might take 5 minutes to show up
3. Create an **AVD report** against Petclinic - make sure you are in the **Vulnerability Detection** area in the left nav of the UI - make sure the report name includes your initials
4. Wait a few minutes for the report to create, then download it
5. You can now see a sortable table for the report in the AVD UI: click on the Report ID link in the Vulnerability Detection UI. This is a **goal state** for a pilot or demo for stakeholders who will be using the product directly.
5. To share the value of AVD with other stakeholders: import the report into a spreadsheet - this spreadsheet would be a deliverable for a Pilot for other customer stakeholders - your Azul SE will assist you with this.

## Lab 2 - Onboard Petclinic into AIC using non-Azul JVM and Run AVD Report

1. Shut down Petclinic
2. Switch from Zulu to your non-Azul JDK so that java --version shows the new JDK
3. Rebuild Petclinic then re-run it  - **% source build** then **% source run** - verify that you see the Petclinic UI at http://localhost:8080
4. Install the IC Agent from the instructions in our public docs - remember to configure the security manager - you might need it during Pilots
5. Shut down Petclinic, then onboard it into AIC using your non-Azul JDK: **edit the **runWithNonAzul-SETME** script to set the Agent Directory (the security manager file will also be expected there) as well as some tags, including AppEnv**. Run the script and verify your second Petclinic instance is there in the AVD UI
6. Run an AVD report as you did for Lab 1

## API Examples

The **curlExamples** folder contains some sample curl scripts for you to play with to exercise a few of the API calls.

## Lab 3 - Creating a Spreadsheet from an AVD Report
TBD







