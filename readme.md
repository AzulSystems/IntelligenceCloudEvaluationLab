# Azul Systems Evaluation Lab for Azul Intelligence Cloud

## Intro

Welcome to the Azul Systems Evaluation Lab for the Azul Intelligence Cloud (AIC). This repo will help you quickly get started with AIC and two of its key features: Azul Vulnerability Detection (AVD) and Code Inventory (CI).

The lab uses a version of the Petclinic application as the target. Azul has just added additional packages and classes to the application for demonstration purposes. All of the source code is included in the repository.

## Goals

- Onboard our version of Petclinic into the AIC and verify that its running
- Generate an AVD report to see the Used vs Present CVEs and understand how this can help prioritize vulnerability triage and remediation
- If desired, generate a spreadsheet from the AVD report (your Azul Sales Engineer will assist)
- Generate a graphical Code Inventory report showing you which Petclinic code has never run
- Experiment with a few sample API calls via cURL
- Make sure you can accomplish these labs using:
    1. This README (please follow it end to end and don't skip any steps :))
    2. Our Public Docs at https://docs.azul.com/intelligence-cloud/

## Prerequisites

- Mac or Linux VM (or Windows with WSL) with Internet access
- Installed git, maven, recent Zulu v17+, recent non-Azul Java v17+ (optional)
- Azul Intelligence Cloud access

## Getting Started

1. Clone this repo (% git clone https://github.com/rstatsinger/AzulSystemsEvalLabforAIC)
2. In your cloned repo, edit the file called **settings**. Log into AIC and confirm you have access, then go to Explorer -> API and generate an API Key. Copy the key and paste it in the file for the value of the APIKEY environment variable. From the same screen, copy the API URL and paste it into the **settings** file for the **IC_API_URL** variable. Also set the **APPENV** variable to something like **AzulPetclinicLabV1** and the **APPNAME** to something like **AzulPetclinic**.
2. In the AIC UI, go to **Settings -> Forwarders** and create a Forwarder. Give it a name such as **MyTestForwarder1** and set the **Domain names** to **localhost**. Download the settings (click the blue **Download as file** button) and save the file into the **Forwarder** subdirectory of your cloned repo. The file will be called **forwarder-settings.properties**. Edit the file and set the keystore path to **./forwarder.jks** and set a keystore password of your choosing.
4. In your cloned repo, cd into the **Forwarder** subdirectory and type **source ./runForwarder**. Verify that your forwarder starts up - you should shortly see a message that looks like this:

[INFO] c.a.c.f.Forwarder - Start forwarding *:443 to (your instance name).api.crs-prod.azul.com:443

Note: for ease of setup, this lab runs the Forwarder on the same machine as the Petclinic workload. In general deployments, the Forwarder will usually run on a different machine in the same network as the Java workloads. You can have multiple Forwarders for different geographies or network segments, and multiple JVMs can talk to a single Forwarder.

## Lab 1 - Onboard Petclinic into AIC using Zulu/Core and Create an AVD Report

0. Make sure Zulu is your active JDK
1. Build Petclinic - **% source build** 
2. Run Petclinic and onboard it into AVD: run the **runWithAICZulu** script - verify that Petclinic comes up at localhost:8080 and that your Petclinic instance is there in the AVD UI - it might take a minute or two to show up.
3. It may take another few minutes for initial CVE information to show up. Once you see it, make sure you are in the **Vulnerability Detection** area in the left nav of the AIC UI. Create an **AVD report** against Petclinic - Give the report a descriptive name.
4. Wait a few minutes for the report to create. There is a graphical, sortable table for the report in the AVD UI: click on the Report ID link in the Vulnerability Detection UI and examine the CVEs. Try sorting on the columns.
5. Download the report as a JSON file. To share the value of AVD with other stakeholders, you can import the report into a spreadsheet - your Azul Sales Engineer can assist you with this.

## Optional Lab 2 - Onboard Petclinic into AIC using non-Azul JVM and Create an AVD Report

Do this lab if you want to onboard into AIC and you are not using an Azul JDK.

1. Shut down Petclinic
2. Switch from Zulu to your non-Azul JDK so that java --version shows the new JDK
3. Rebuild Petclinic - **% source build** 
5. Run Petclinic and onboard it into AIC using your non-Azul JDK: run the **runWithAICNonAzul** script and verify that Petclinic comes up at localhost:8080 and that your second Petclinic instance is there in the AVD UI
6. Run an AVD report as you did for Lab 1

## Lab 3 - Generate a Graphical Code Inventory Report Showing Unused Code

Make sure you have completed Lab 1 before attempting this lab.

1. If you recently did Lab 1, you should have a running instance, and you're ready to analyze your Code Inventory at the class level to see which code has never run.
2. You have already set the **AppEnv** tag in your **settings** file. AppEnv is a special tag that links running instances in AIC with client side processing of application jarfiles - it's the "glue" between the code that AIC has seen run, and all of the code that could run. It will be used by the Intelligence Cloud Client to generate an HTML report on the code that has not run. 
3. An instance might need to run in CI "for a while" in order for you to feel confident that all of the code that might run has actually run. How long is "a while"? It depends:
    - A few minutes to hours - will tell you what's used when the app starts. This also tells you when you come back later you may have more. For this lab, this is sufficient.
    - A few days to a week - will tell you what's used generally. The application will have seen some traffic, so an engineer who has suspicions about unneeded code can validate or refute. Do this for a pilot.
    - A week or more will provide a more complete picture of things. One week of data should be sufficient for a pilot -- in a production deployment you could wait even longer. A good practice might be to run the application across month-end or periods of special time/peak usage before generating a report on which to take action.
4. Run the **runICClient** script. It will take a few minutes. The result will be a new subdirectory in your project - the name of the subdirectory will be the value of your **APPENV** tag with the word **CI_Report** appended. In the subdirectory, there should be an index.html file in there - open it in your favorite browser. Read through the contents. You will also find a json file in the subdirectory called **all-code.json** - in the left nav of the index.html file there is a link to **Load JSON**. Use it to import all-code.json into the browser. The result will be a graphical analysis of the used and unused code for Petclinic. Click into the report and examine the details.

## Lab 4 - Method Level Code Inventory

If you are interested in method-level Code Inventory, please ask your Azul Sales Engineer to assist you. You will need to be running a recent Azul JDK (either Zulu or Zing).

## Lab 5 - API Examples

The **curlExamples** folder contains some sample curl scripts for you to play with to exercise a few of the API calls.

