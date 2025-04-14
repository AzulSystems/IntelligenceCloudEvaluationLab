# Azul Systems Evaluation Lab for Azul Intelligence Cloud

## Intro

Welcome to the Azul Systems Evaluation Lab for the Azul Intelligence Cloud (IC). This repo will help you quickly get started with IC and some of its key features.

The lab uses a version of the Petclinic application as the target. Azul has added a few additional packages and classes to the application for demonstration purposes. All of the source code is included in the repository.

## Goals

- Onboard our version of Petclinic into IC and verify that its running
- Generate a Vulnerability Detection report to see the Used vs Present CVEs and understand how this can help prioritize vulnerability triage and remediation
- If desired, generate a spreadsheet from the AVD report (your Azul Sales Engineer will assist)
- Generate a graphical Code Inventory report showing you which Petclinic code has never run
- Generate a JVM Inventory report showing recent JVM connections to IC
- Experiment with a few sample API calls via cURL
- Make sure you can accomplish these labs using:
    1. This README (please follow it end to end and don't skip any steps :))
    2. Our Public Docs at https://docs.azul.com/intelligence-cloud/

## Prerequisites

- Mac or Linux host or VM (or Windows with WSL) with Internet access
- Installed git, maven, and a recent JDK version 8 or later. This can be an Azul JDK but does not have to be.
- Azul Intelligence Cloud access (contact your Azul Account Manager if you dont have access)

## Getting Started

1. Clone this repo (% git clone https://github.com/AzulSystems/IntelligenceCloudEvaluationLab.git)
2. In your cloned repo, edit the file called **.env**. Log into IC and confirm you have access, then go to Settings -> Forwarders and click on the **default** forwarder, then click **Show Settings** in the middle of the page. Copy the values of **ic.host** and **forwarder.access.key** and paste them into the .env file. Now go to Explorer -> API and generate an API Key. Copy the key and paste it in the file for the value of the **APIKEY** environment variable. From the same screen, copy the API URL and paste it into the **.env** file for the **IC_API_URL** variable. Also set the **APPENV** variable to something like **AzulPetclinicLabV1-YourInitials** and the **APPNAME** to something like **AzulPetclinic-YourInitials**.
3. cd into the **Forwarder** subdirectory and type **./runForwarder.sh**. Verify that your forwarder starts up - you should shortly see a message that looks like this:

[INFO] c.a.c.f.Forwarder - Start forwarding *:443 to (your instance name).api.crs-prod.azul.com:443

Note: for ease of setup, this lab runs the Forwarder on the same machine as the Petclinic workload. In general deployments, the Forwarder will usually run on a different machine in the same network as the Java workloads. You can have multiple Forwarders for different geographies or network segments, and multiple JVMs can talk to a single Forwarder.

## Lab 1 - Onboard Petclinic into IC and Create an AVD Report

0. Make sure java --version returns a valid JDK version 8 or newer
1. Build Petclinic - **% ./build.sh** 
2. Run Petclinic and onboard it into AVD: run the **runWithIC.sh** script - verify that Petclinic comes up at localhost:8080 and that your Petclinic instance is there in the UI - it might take a minute or two to show up.
3. It may take another few minutes for initial CVE information to show up. Once you see it, make sure you are in the **Vulnerability Detection** area in the left nav of the IC UI. Create an **AVD report** against Petclinic - Give the report a descriptive name.
4. Wait a few minutes for the report to create. There is a graphical, sortable table for the report in the AVD UI: click on the Report ID link in the Vulnerability Detection UI and examine the CVEs. Try sorting on the columns.
5. Download the report as a JSON file. To share the value of AVD with other stakeholders, you can import the report into a spreadsheet - your Azul Sales Engineer can assist you with this.

## Lab 2 - Generate a Graphical Code Inventory Report Showing Unused Code

Make sure you have completed Lab 1 before attempting this lab.

1. If you recently did Lab 1, you should have a running instance, and you're ready to analyze your Code Inventory to see which code has never run.
2. You have already set the **AppEnv** tag in your **.env** file. **AppEnv** is a special tag that IC uses to query JVMs. You will need the AppEnv tag you set in Lab 1 for this lab.
3. Navigate to the **Code Inventory** area in the IC UI left nav area. Click the blue Create Report button on the right side. Give the report a name, and at the right edge make sure the **Use** dropdown is set to **ALL**.
4. In the **Filter** area, select **AppEnv** and the **CONTAINS** comparator. Type or paste in your **AppEnv** tag. Click the blue **CREATE** button.
5. The report may take a few minutes to create. When the report is created, click on the blue link for the **Report ID**.
6. Note that in a Production scenario, an instance might need to run in CI "for a while" in order for you to feel confident that all of the code that might run has actually run. For this lab, running Petclinic for just a few minutes will give you a general idea of what code runs when the application starts. Once you are running against your own applications in Production, you will probably want to run your instances for a week or more to provide a more complete picture of things. One week of data should be sufficient for a pilot -- in a production deployment you could wait even longer. A good practice might be to run the application across month-end or periods of special time/peak usage before generating a report on which to take action.

## Lab 3 - Create a JVM Inventory Report
1. In the left nav area, go to JVM Inventory
2. Click Create Report - give the report a name that starts with your initials 
3. On the right side, slide the Details slider to the right
4. In the filter area, type the word **vendor** so that you have access to the **inventory.systemProperties.java.vm.vendor** property
5. Use the **CONTAINS** comparator, and give this property a value that matches your JDK - e.g. Temurin, Azul, Adoptium, etc
6. Side **Create Template** to the right, then click **Create**
7. Let the report create and then examine the details by clicking on the blue **Report ID** link.

## Lab 4 (Optional) - API Examples

The **curlExamples** folder contains some sample curl scripts for you to play with to exercise a few of the API calls.

## Questions?

Reach out to your local Azul contact - we're here to help.
