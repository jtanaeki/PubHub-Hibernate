# PubHub-Hibernate 📖 <img align="right" width="300" height="200" src="src\main\webapp\resources\imgs/ebook3.jpg">
### Book Publishing System (refactored using Hibernate and Maven)
<br>
<a href="CODE_OF_CONDUCT.md">
    <img alt="Contributor Covenant" src="https://img.shields.io/badge/Contributor%20Covenant-2.0-4baaaa.svg" />
</a>

---

A self-publishing platform that allows anyone to write, publish, and sell their own books.

## Setting Up Local Environment
The following instructions will help you set up a local development environment, run the project on your machine for development and testing purposes, and deploy it on a live system. See [*Deployment*](https://github.com/jtanaeki/PubHub-Hibernate/blob/main/README.md#deployment) for notes on how to deploy the project. See [*How to Discuss and Contribute*](https://github.com/jtanaeki/PubHub#how-to-discuss-and-contribute) for notes on how to contribute to this repository.

### Prerequisites
Before building and testing the application, you must make sure to have the following installed:
- The [latest version of Java Runtime Environment (JRE)](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) (Using JRE 1.8, rather than JDK, maybe more suitable for this application to run on WildFly)
- Any IDE that supports JRE development, such as the [latest version of Eclipse](https://www.eclipse.org/downloads/packages/) (Download and install Eclipse IDE for Enterprise Java and Web Developers)
- Application Server, such as the [latest version of WildFly](https://www.wildfly.org/downloads/) (Please read [*Java Development Environment Setup*](Java%20Development%20Environment%20Setup.pdf) to see how to integrate Wildfly with Eclipse)
- Database, such as the [latest version of PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

For further instruction on setting up your environment for Java development, read [*Java Development Environment Setup*](Java%20Development%20Environment%20Setup.pdf).

\* *Please keep in mind that these instructions were constructed around 2016.*

### Cloning the Repository
After installing the necessary software, you can clone a local copy of the repository. A [ZIP file version](https://github.com/jtanaeki/PubHub-Hibernate/archive/refs/heads/main.zip) of the copy can be downloaded, or you can clone the repository directly over HTTPS from the command line:

```bash
git clone https://github.com/jtanaeki/PubHub-Hibernate.git
```

### Entity Relational Diagram of Tables in Database
![Image of Entity Relational Diagram](https://user-images.githubusercontent.com/55217672/122313759-c8b36f00-cee4-11eb-94a0-3367b37a694f.png)

## Running the Application
The following instructions will help you get the program running via Eclipse.

### Importing the Project
- The Import function can be accessed in three ways:
  - The most convenient and quickest way: **File > Import…** (shortcut: **Alt + F + I**):

       ![image](https://user-images.githubusercontent.com/55217672/118418913-5e5ea300-b688-11eb-8ce7-314bf4d58332.png)<br /><br />

  - Right click on any blank space in *Package Explorer* view, select **Import…** from the context menu:

       ![image](https://user-images.githubusercontent.com/55217672/118419010-c90fde80-b688-11eb-8b66-e87a79240128.png)<br /><br />

  - Right click on any blank space in _Project Explorer_ view, then select **Import > Import…** from the context menu:

       ![image](https://user-images.githubusercontent.com/55217672/118419021-d4fba080-b688-11eb-9536-fd28a18a5957.png)<br /><br />

- The _Import_ wizard should open:

    ![image](https://user-images.githubusercontent.com/55217672/118419042-e5ac1680-b688-11eb-8fc4-791adebada69.png)<br /><br />

- Under the *General* folder, select *Existing Projects into Workspace* and click **Next**. The next screen, _Import Projects_, appears.       

- If the application was downloaded as a zip file, click _Select archive file_ and click the **Browse** button to locate the zip file. Make sure the project is selected and click **Finish**:

    ![image](https://user-images.githubusercontent.com/55217672/118419097-0c6a4d00-b689-11eb-879d-4f1f29e1af4d.png)<br /><br />

- If the application was NOT downloaded as a zip file, click _Select root directory_ and click the **Browse** button to find the project’s directory path. Make sure the project and the *Copy projects into workspace* option is selected:

    ![image](https://user-images.githubusercontent.com/55217672/118419200-610dc800-b689-11eb-8629-ee35afe1a925.png)<br /><br />

- The archive will be extracted and the files will be copied into the workspace. The imported project should show in the *Project Explorer/Package Explorer* view:

    ![image](https://user-images.githubusercontent.com/55217672/118419242-6ec34d80-b689-11eb-8fb6-32f9cb417273.png)<br /><br />

## Deployment
### Adding JRE System Library to Java Build Path
If you don't see the JRE System Library with _JRE 1.8_ in the Java Build Path, under the project name, in the _Package Explorer_ tab, then it must be added.

- Right-click the project folder and click **Properties**

    ![image](https://user-images.githubusercontent.com/55217672/119072766-127a6980-b9ba-11eb-941f-f91fb74b9c18.png)<br /><br />

- Select *Java Build Path* on the left-hand side of the dialog box, then click the *Libraries* tab. 
- Select _JRE System Library_, then click **Edit**.

    ![image](https://user-images.githubusercontent.com/55217672/119073453-6043a180-b9bb-11eb-94d7-9d3d50b1add5.png)<br /><br />
    
- Select _Alternate JRE_, then click **Installed JREs**

    ![image](https://user-images.githubusercontent.com/55217672/119232258-9899cb80-baf2-11eb-97a5-ac95c7c491cb.png)<br /><br />
    
- Click **Add** next to the list of JREs

    ![image](https://user-images.githubusercontent.com/55217672/119275422-fad1f980-bbe2-11eb-879c-4da6f0c1929e.png)<br /><br />
    
- Make sure _Standard VM_ is selected and click **Next**

    ![image](https://user-images.githubusercontent.com/55217672/119426337-04d52480-bcd7-11eb-8993-9e1b5c937518.png)<br /><br />
    
- Select the directory where the JRE is installed and click **Finish**

    ![image](https://user-images.githubusercontent.com/55217672/119427213-a8730480-bcd8-11eb-9bb7-32d278670296.png)<br /><br />
    
- Now that the JRE has been added, click **Apply and Close**

    ![image](https://user-images.githubusercontent.com/55217672/119427525-2c2cf100-bcd9-11eb-805b-e4d65d2e4745.png)<br /><br />
    
- Click the dropdown menu next to _Alternate JRE_, select the newly added JRE, then click **Finish**

    ![image](https://user-images.githubusercontent.com/55217672/119427900-ed4b6b00-bcd9-11eb-85ac-7c9beb2a201e.png)<br /><br />

- Now that the JRE has been added to the Modulepath/Classpath, the compliance level must be modified. Select *Java Compiler* on the left-hand side of the dialog box. If prompted to save changes, click **Apply**

    ![image](https://user-images.githubusercontent.com/55217672/119429967-ea527980-bcdd-11eb-8302-3da98f27941a.png)<br /><br />

-   Click the dropdown on the right and change the _Compiler compliance level_ to **1.8**

    ![image](https://user-images.githubusercontent.com/55217672/119073496-74879e80-b9bb-11eb-9422-d206939ed155.png)<br /><br />
    
- Click **Apply and Close**

    ![image](https://user-images.githubusercontent.com/55217672/119428291-96926100-bcda-11eb-8d20-2caa91d7b1f3.png)
    
### Adding _javax.servlet_ JAR File
In order to compile the Java Servlets/Java Server Pages(JSP files), a _javax.servlet_ file must be added to the classpath. 
- Please [download the jar zip file](http://www.java2s.com/Code/Jar/j/Downloadjavaxservlet30jar.htm), unzip, and extract the file.
- Right-click the project folder and click **Properties**

    ![image](https://user-images.githubusercontent.com/55217672/119072766-127a6980-b9ba-11eb-941f-f91fb74b9c18.png)<br /><br />

- Select *Java Build Path* on the left-hand side of the dialog box, then click the *Libraries* tab.
- Click _Classpath_, then click **Add External JARs**
    
    ![image](https://user-images.githubusercontent.com/55217672/120088217-7be63080-c0bc-11eb-9ede-c1aaf739cf84.png)
    
- Select the extracted _javax.servlet_ jar file, then click **Apply and Close**

    ![image](https://user-images.githubusercontent.com/55217672/120406292-813fb700-c318-11eb-97a2-b3d0e76a3c59.png)    
  
### Integrating the Application Server (Wildfly) with the IDE (Eclipse)
If you have not done so yet, make sure Wildfly is set up on Eclipse for deployment. Please read [*Java Development Environment Setup*](Java%20Development%20Environment%20Setup.pdf). Follow the instructions in the *Integrating Your Application Server with Your IDE (JBoss Middleware)* section.

### Setting up the Database
Please read [*Java Development Environment Setup*](Java%20Development%20Environment%20Setup.pdf) to see how to set up PostgreSQL. Follow the instructions in the *Installing a Database (PostgreSQL)* section. Create [these tables](https://github.com/jtanaeki/PubHub-Hibernate#Entity-Relational-Diagram-of-Tables-in-Database) in the database and fill in random values.

## Functionalities
- The customer/author should log in or register before accessing homepage
- The customer should be able to see a list of books, which includes the isbn number, title, author, publish date, and price
- The customer should be able to download any book(s) from the list of published books and update the title, author, and/or price of any book(s)
- The customer should be able to type in the text box and search for books from the list by any category
- The author must be an authenticated user to upload a PDF version of their book and publish it, adding it to the list of published books

## Interface
### Login and Registration Page
<div>
    <img alt="Login Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/121787808-6251e800-cb96-11eb-9683-f21bf2926695.png">
    <img alt="Registration Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/121790132-47896e80-cbaa-11eb-90e9-34187e136813.png">
</div>
    
### Home and List of Published Books
<div>
    <img alt="Home Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/120939717-49a18800-c6e7-11eb-8b69-323a662a99c8.png">
    <img alt="List of Published Books Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/120939768-971df500-c6e7-11eb-91a2-6b9d105591bb.png">
</div>

### Authentication and Publishing Page
<div>
    <img alt="Authentication Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/121793181-7f081300-cbca-11eb-9e1c-51acc786982b.png">
    <img alt="Publishing Page" width="470" height="250" src="https://user-images.githubusercontent.com/55217672/120939807-c92f5700-c6e7-11eb-9145-5f2953d06f64.png">
</div>

## Tools Used
### Servers
- [Wildfly](https://www.wildfly.org/)

### Databases
- [PostgreSQL](https://www.postgresql.org/)

### Languages & Frameworks
- Frontend:
    - [HTML](https://developer.mozilla.org/en-US/docs/Web/HTML), [CSS](https://developer.mozilla.org/en-US/docs/Web/CSS), [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript), [jQuery](https://api.jquery.com/), [Bootstrap](https://getbootstrap.com/docs/4.1/getting-started/introduction/), [JavaServer Pages (JSPs)](https://docs.oracle.com/javaee/5/tutorial/doc/bnagx.html)
- Backend:
    - [Java Servlets](https://docs.oracle.com/javaee/5/tutorial/doc/bnafe.html)

## How to Discuss and Contribute
Some of the best ways to contribute are to try things out, file issues, join in design conversations, and make pull-requests. Please read [CONTRIBUTING.md](CONTRIBUTING.md) before you begin for details on the [code of conduct](CODE_OF_CONDUCT.md), as well as the process for submitting [pull requests](https://github.com/jtanaeki/PubHub-Hibernate/pulls). Proposals for changes and other issues specific to PubHub-Hibernate can be found [here](https://github.com/jtanaeki/PubHub-Hibernate/issues).
