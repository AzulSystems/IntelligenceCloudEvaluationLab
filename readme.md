# Azul Systems Evaluation Lab for Azul Intelligence Cloud

## Intro

Welcome to the Azul Systems Evaluation Lab for the Azul Intelligence Cloud (AIC). This repo will help you quickly get started with AIC and two of its key features: Azul Vulnerability Detection (AVD) and Code Inventory (CI).

## Goals

- Onboard our version of Petclinic into the AIC and verify that its running
- Generate an AVD report to see the Used vs Present CVEs
- If needed, generate a spreadsheet from the AVD report (your Azul SE will assist)
- Generate a Code Inventory report showing you which Petclinic code has never run
- Make sure you can accomplish these labs using these 3 items:
    1. This README (please follow it end to end and don't skip any steps :))
    2. Our Public Docs at https://docs.azul.com/intelligence-cloud/
    3. The instructions for importing an AVD report into a spreadsheet - they will be provided by your Azul SE

## Prerequisites

- Mac or Linux VM (or Windows with WSL) with Internet access
- Installed git, maven, recent Zulu v17+, recent non-Azul Java v17+ (optional)
- Azul Intelligence Cloud access

## Getting Started

1. Clone this repo (% git clone https://github.com/rstatsinger/AzulSystemsEvalLabforAIC)
2. Log in to AIC and confirm you have access. Go to **Settings -> Forwarders** and create a Forwarder. Give it a name such as **MyTestForwarder1** and set the **Domain names** to **localhost**. Download the settings (click the blue **Download as file** button) and save the file into the **Forwarder** subdirectory of your cloned repo. The file will be called **forwarder-settings.properties**. Edit the file and set the keystore path to ./forwarder.jks and set a keystore password of your choosing.
3. cd into the **Forwarder** subdirectory and type **source ./runForwarder**. Verify that your forwarder starts up - you should shortly see a message that looks like this:

[INFO] c.a.c.f.Forwarder - Start forwarding *:443 to logicalis.api.crs-prod.azul.com:443

Note: for ease of setup, this lab runs the Forwarder on the same machine as the Petclinic workload. In general deployments, the Forwarder will usually run on a different machine in the same network as the Java workloads. You can have multiple Forwarders for different geographies or network segments - multiple JVMs can talk to a single Forwarder.

4. Edit the file called **settings**. Set the **APPENV** variable to something like **AzulPetclinicLabV1** and the **APPNAME** to something like **AzulPetclinic**. In the AIC UI, go to **Settings -> Profile** and generate an API KEY - copy it and set it as the value of the **APIKEY** variable. Set the IC_API_URL from **Explorer -> API - API URL**

## Lab 1 - Onboard Petclinic into AIC using Zulu/Core and Create an AVD Report

0. Make sure Zulu is your active JDK
1. Build and run Petclinic - **% source build** then **% source run** -  verify that you see the Petclinic UI at http://localhost:8080
2. Shut down Petclinic, then onboard it into AVD: edit the **runWithAVDZulu-SETME** script to set a CRS_TAG for your username, then run the script - verify your Petclinic instance is there in the AVD UI - it might take a minute or two to show up.
3. Create an **AVD report** against Petclinic - make sure you are in the **Vulnerability Detection** area in the left nav of the UI. Give the report a descriptive name.
4. Wait a few minutes for the report to create. There is a graphical, sortable table for the report in the AVD UI: click on the Report ID link in the Vulnerability Detection UI and examine the CVEs. Try sorting on the columns.
5. Download the report as a JSON file. To share the value of AVD with other stakeholders: import the report into a spreadsheet - this spreadsheet would be a deliverable for a Pilot for other customer stakeholders - your Azul SE will assist you with this.

## Lab 2 - Onboard Petclinic into AIC using non-Azul JVM and Create an AVD Report

1. Shut down Petclinic
2. Switch from Zulu to your non-Azul JDK so that java --version shows the new JDK
3. Rebuild Petclinic then re-run it  - **% source build** then **% source run** - verify that you see the Petclinic UI at http://localhost:8080
5. Shut down Petclinic, then onboard it into AIC using your non-Azul JDK: **edit the **runWithNonAzul-SETME** script to set some tags, including AppEnv**. Run the script and verify your second Petclinic instance is there in the AVD UI
6. Run an AVD report as you did for Lab 1

## Lab 3 - Generate a Graphical Code Inventory Report Showing Unused Code

Make sure you have completed Lab 1 before attempting this lab.

1. If you recently did Lab 1 and you set an AppEnv tag, you should have a runnable instance set up for both AVD and Class-level CI. Restart Petclinic with a different tag for the appname than you did before so that you can easily tell that your new instance is onboarded - make sure you've set a value for the AppEnv tag.
2. This lab explores class-level CI, which is the default. Method-level CI will be explored in a subsequent lab.
3. Make sure a tag value for **AppEnv** is included in your tags. **AppEnv** is a special tag that links running instances in AIC with client side processing of application jarfiles - it's the "glue" between the code that AIC has seen run, and all of the code that could run. It will be used by the IC Client to generate an HTML report on the code that has not run. An example for Petclinic might be AppEnv='Petclinic-YourInitials-CurrentDate' If you set AppEnv for the AVD lab, make sure you know its value.
4. Azul will provide to you the Intelligence Cloud Client jarfile. Note: this is a pre-release of the client. Please do not share it outside of your team.
5. An instance needs to run in CI "for a while" so that a customer feels confident that all of the code that might run has actually run. How long is "a while"? It depends:
    - A few minutes to hours, will tell you what's used when the app starts. This also tells you when you come back later you'll have more. You generally wouldn't act on this report. Do this for this lab.
    - A few days to a week, will tell you what's used generally. The app will have gotten a little traffic so an engineer can find what they were maybe suspicious about or get a feel for what would happen if they ran the report for longer. Do this for a pilot.
    - A week or more will provide a more complete picture of things. The longer you wait the more "firsts" you'll see, up to a point. One week of data should be sufficient for a pilot -- the customer would then just do the same thing for longer. Customer environments should watch for longer, generally across month-end or periods of special time/peak usage.
6. Stash the icclient jarfile in a peer directory to your PetClinic project (i.e. create a subdirectory off the main project directory into which you cloned this repository)
7. Log into AIC and copy your API Key from https://azul.crs-prod.azul.com/code-inventory/api. You can either use your existing key or generate a new one.
8. From the directory where you stashed the icclient.jar, run the client like this:

java -jar ./icclient.jar unused-code-diff --appenv=**YourAppEnvTag** --ic=**Your AIC URL**/public --key=**YourAPIKey** --output=./PetClinicCodeInventory

Make sure you substitute in your AppEnv tag, your Public Endpoint, and your API key.

9. The result should be a new subdirectory called **PetClinicCodeInventory**. There should be an index.html file in there - open it in your favorite browser. Read through the contents. You will also find a json file in the subdirectory called **all-code.json** - in the left nav of the index.html file there is a link to **Load JSON**. Use it to import all-code.json into the browser. The result will be a graphical analysis of the used and unused code for Petclinic. Click into the report and examine the details.

## Lab 4 - Method Level Code Inventory

If method-level CI is desired, do the following:

1. Make sure Zulu is your current active JDK.
2. Edit the file **runWithAICZulu-SETME** and add **notifyFirstCall=true** to the **AZ_CRS_ARGUMENTS** environment variable.
3. Restart PetClinic
4. Wait a few minutes and then re-run the IC Client jarfile as above, adding **--methods** to the end of the command.

## Lab 5 - Creating a Spreadsheet from an AVD Report - TBD

## Lab 6 - API Examples

The **curlExamples** folder contains some sample curl scripts for you to play with to exercise a few of the API calls.







