# Knowledge Article Record Management Application (KARMA) - Technical Approach

## The Essentials

The KARMA prototype is deployed on Amazon Web Services and can be accessed through the site URL: 

**<https://acceptance.bigbluesign.com>**  

Login information is available on the landing page.  The _User_ account will provide access to basic services for knowledge article creation and retrieval.  All common _User_ account services are accessible from the _Dashboard_ which serves as the _Home_ page and is automatically loaded upon successful login.  Additional _User_ account services for metadata management are available from the _Manage_ menu.  The _Admin_ account provides access to the _Administration_ menu which includes several application management and monitoring services.  For guidance on how to use KARMA, please see the [User Guide](docs/UserGuide.md)

This document references detailed documentation in the [KARMA GitHub Wiki](https://github.com/StanfieldSystems/KARMA/wiki) regarding processes and technologies used by Stanfield Systems. In addition, the document references artifacts created for the KARMA prototype with links to files in the _docs_ folder in this repository.  All references include navigable links.

## Product Vision

As a provider of custom application development solutions, the company creates a lot of knowledge regarding management processes, technical processes, and technical solutions.  Much of this knowledge can be applied across multiple products with some tailoring for different teams and solutions and some evolution for new techniques and technologies.  The company envisions a knowledge management application that can store this knowledge and make it easier to share, reuse, and adapt across products.  Initially, the application will facilitate efficient storage and retrieval of relevant knowledge; however, the ultimate vision is for the application to apply artifical intelligence techniques to suggest relevant knowledge and automatically adapt it to match product requirements and delivery teams.

# Prototype

Stanfield Systems applied their [Agile Delivery Process](#agile-delivery-process) to identify application features and define the application architecture and technologies.

## Application Features

Based on the results of our inception activities (in particular, [Explore Initial Scope](https://github.com/StanfieldSystems/KARMA/blob/master/README.md#explore-initial-scope) and [Develop Release Plan](https://github.com/StanfieldSystems/KARMA/blob/master/README.md#develop-release-plan)), the product team prioritized and selected user stories for the following key features to be implemented as the KARMA prototype release.

* Login to the application so that content can be tailored for a particular user.  
* Create and edit knowledge articles for sharing with other users
* Find and retrieve knowledge relevant to a particular user
* Provide a single location (dashboard) to access knowledge relevant to a particular user

## Application Architecture and Technologies

Stanfield Systems implemented KARMA using open source technologies in a multi-tiered architecture as described in our [Technical Architecture](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#technical-architecture).

KARMA uses the following open source technologies

* [**Angular 5**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#angular4client-project) - provides Model-View-Controller JavaScript/TypeScript patterns for defining user interface views on the client.  The Angular client uses HTTP to make calls to the RESTful API on the application server, using **JSON** to exchange information between the client and the application server.
* **Bootstrap** - integrated with the Angular 5 client to present a responsive design that works on multiple devices with varying screen resolutions.
*  [**Spring Boot framework**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#springbootangularintegration-project)** - used to develop RESTful web services for the API.
* [**Log4j2**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#logging) - used for application logging.
* [**Swagger**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#swagger-2) is used to describe and document RESTful APIs in accordance with the **OpenAPI Specification** _(RFI Requirement f)_.  To access this API documentation, login to KARMA as the admin user and select [_API_](https://acceptance.bigbluesign.com/#/docs) from the _Administration_ menu.
[**PostgreSQL**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#postgresql) - persists application data in a  relational database. 

#  Agile Delivery Process

Stanfield Systems applies a [Scrum-Based, Disciplined Agile Delivery (DAD)](https://github.com/StanfieldSystems/KARMA/wiki) life-cycle for product development and release.  

## Inception

Stanfield Systems begins product development with a short [Inception Phase](https://github.com/StanfieldSystems/KARMA/wiki/Inception)  to achieve concurrence on the direction the team will take to deliver the product.  For the KARMA prototype, this phase lasted a single sprint (Sprint 0) and included the following activities.

### Form Team
_(RFI Requirements a, b)_

Applying our [guidelines for forming agile teams](https://github.com/StanfieldSystems/KARMA/wiki/Form_and_Evolve_Team), Stanfield Systems established a six-person team to develop the KARMA prototype and prepare documentation for the RFI response.  This multidisciplinary and collaborative team includes 8 of the PQVP AD-DS Labor Categories as specified below.

- Product Manager (Christine Cox)
- Technical Architect (Patrick Grogan)
- Interaction Designer/User Researcher/Usability Tester (Diana Persell)
- DevOps Engineer (Patrick Grogan)
- Delivery Manager (Tim Jacobs)
- Agile Coach (Patrick Grogan)
- Business Analyst (Christine Cox)
- Full Stack Developer #1 (Kamal Singh)
- Full Stack Developer #2 (Aben Kebede)

The Product Manager is the team leader with authority and responsibility for successful delivery of a quality working prototype.

### Explore Initial Scope
_(RFI Requirements c, d)_

In [Exploring Initial Scope](https://github.com/StanfieldSystems/kmt/wiki/Explore_Initial_Scope) the team performs lightweight requirements envisioning - developing usage models, domain models, user interface models, process models, and other user-centric approaches. 

Applying [user-centered design](https://github.com/StanfieldSystems/KARMA/wiki/User_Centered_Design) techniques Stanfield Systems worked directly with users to understand what the users need to perform their tasks.  These user-centered techniques address the entire user experience from start to finish.

* **Personas**.  We identified an initial set of users and developed a persona for each class of user. Each persona identified a user by a name, a set of believable personal and professional attributes, and a list of typical tasks with scenarios.  This rich resource was used as reference and validation throughout the project lifecycle.  

    * [Consumer Persona](docs/PersonaConsumer.pdf)
    * [Project Manager Persona](docs/PersonaProjectManager.pdf)
    
* **Shadow Users**.  We observed people using similar tools, shadowing real users in their work space and letting them lead the conversation.   The potential KARMA users guided the researcher through the knowledge management tool they currently used and highlighted features, workflow and tools that worked well plus gave unfiltered feedback on what did not work for them.
* **Contextual Interviews**. Leveraging our observations from shadowing users, we conducted contextual interviews to further identify and refine common needs, tasks, and concerns.  
* **Affinity Diagrams**.  In response to the user research findings, the product team conducted a "popcorn-style" task analysis resulting in an affinity diagramming session to help priortize features for the first design iteration.  The outcome was used for validation in subsequent usability testing.

See [User Research Findings](docs/UserResearchFindings.pdf) for the results of these user research activities.

The result of this research included a list of pain points that KARMA could be designed to resolve.  By identifying these pain points, user experience research helped the team prioritize and focus on a couple key areas.  

1.	Get the user to their work right away
2.	Make “creating” an article easier

#### Initial Product Backlog

Based on our user-centered research and the prototype requirements from the RFI, we identified the following high-level user stories which were documented as epics for our initial product backlog.

1. Login to the application so that content can be tailored for a particular user.  
1. Create and edit knowledge articles for sharing with other users
1. Find and retrieve knowledge articles relevant to a particular user
1. Provide a single location (dashboard) to access knowledge relevant to a particular user
1. Define rules for creating knowledge articles in a consistent format
1. Approve and publish articles using a pre-defined workflow
1. Control access to knowledge articles based on roles and business rules
1. Notify users when the status of relevant articles is changed
1. Assess the value of knowledge articles based on usage and feedback
1. Define work flow rules for life-cycle management of knowledge articles
1. Review new or modified knowledge articles prior to approval
1. Provide access to system features for users with disabilities

#### User Experience Goals

While focused on actual users, user experience design benefits by knowledge from all stakeholders.  Not just the end user, but the development team and business stakeholders.  This knowledge helped shape the overall user experience goals of the project. 

* Delight the user (to be measured by quantitative activities such as interviews and surveys)
* Clean and uncluttered user interface (to be measured by qualitative and quantitative activities such as usability testing and contextual interviews)
* Efficient navigation throughout the workflow (to be measured by qualitative and quantitative activities such as usability testing and contextual interviews)

### Develop Release Plan

Since the delivery date for the prototype is fixed, release planning focused on determining the minimal viable product that could be developed by the delivery date.  We focused on key issues identified during our initial user research.  This led to a prioritization of the dashboard display to provide rapid access to articles of interest with dependencies on logging in, knowledge creation, and finding relevant knowledge.  These four epics (1-4 above) were the focus of our minimal viable product for the release.

### Technical Strategy and Work Environment
_(RFI Requirements e, f, g, h, k, l, m, o, p, r, t)_

Applying our [Technical Strategy](https://github.com/StanfieldSystems/KARMA/wiki/Define_Technical_Strategy) process be based the technical approach for the KARMA prototype on our established [Work Environment Standards](https://github.com/StanfieldSystems/KARMA/wiki/Work_Environment_Standards) and [Technical Architecture](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture).  

All technologies and platforms used to create and run the prototype are openly licensed and free of charge _(RFI Requirement t)_.  (Note:  Amazon Web Services and Atlassian Cloud Services to incur minimal hosting costs.)  

#### Version Control and Configuration Management
_(RFI Requirement e, p)_

All application code files, including database scripts, are stored and managed in this [**GitHub**](https://github.com/StanfieldSystems/KARMA) version control repository.

[**Liquibase**](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#liquibase) is used to migrate versioned database changes to developer, integration, and production database servers within the continuous integration and build pipeline.

#### Continuous Integration and Deployment
_(RFI Requirement m, o, p, q, r, s)_

KARMA is [continuously integrated and delivered to Amazon Web Services (AWS)](https://aws.amazon.com/blogs/opensource/mu-pipelines-container-applications/).

* Static resources such as the Angular front end (including html, typescropt, javascript, image, and configuration files) are pushed to an Amazon Simple Cloud Storage (S3) bucket that is fronted by the CloudFront Content Delivery Network (CDN). 
* The RESTful service API (e.g. the SpringBoot project) is deployed as an Amazon Elastic Container Service (ECS) using **Docker** containers.
* The PostgresSQL database is provisioned on Amazon Relational Data Service (RDS).

Stanfield Systems configures and manages the code deployment pipeline using the open source application [**mu**](https://stelligent.com/2017/04/11/mu-introduction-ecs-for-microservices/).  **mu** applies the same build configuration (e.g. Maven, npm) used in the development deployment, and then packages and deploys the application as configured in a **mu** script.  

KARMA can be deployed on any workstation or server using command line tools as described in [How to Deploy and Run Karma](https://github.com/StanfieldSystems/KARMA/wiki/How_to_Deploy_and_Run_Karma).

#### Continuous Monitoring

The Karma application generates several dashboards to [monitor](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#monitoring) the application.  These dashboards are available when you log into Karma using the admin user and admin password.  The dashboards are available at runtime, and are the easiest way to do some simple monitoring.

* The metrics dashboard uses Dropwizard metrics to give a detailed view of the application performance. 
* The health dashboard uses Spring Boot Actuator’s health endpoint to give health information on various parts of the application. 
* The audits dashboard monitors access to the application and provides the status of whether users were successful during the authentication process.

All Developers can monitor the status of Karma's code pipeline by looking at the [AWS Code Pipeline dashboard](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#aws-continuous-integration-and-delivery-dashboard).  The CI and CD processes are initiated every time a developer checks-in source code.  The dashboard shows the real-time status of retrieving source code, the build and unit tests, the deploy to acceptance, and the running of BDD acceptance tests.  Developers are notified whenever a build is completed.

#### Accessibility
_(RFI Requirement g, k)_

Accessibility and Section 508 compliance is incorporated into our agile delivery process as described in our [Accessibility](https://github.com/StanfieldSystems/KARMA/wiki/Accessibility) guidelines and procedures.  We check for proper implementation of accessibility guidelines through peer reviews, automated Behavioral Driven Development tests, static analyzers, and manual testing using accessibility browser tools.  

The Angular 5 client integrates **Bootstrap** libraries to present a responsive design that works on multiple devices with varying screen resolutions.

#### Style Guide
_(RFI Requirement h)_

Stanfield Systems applied User Interface style guidelines from the [U.S. Web Design Standards](https://designsystem.digital.gov/).  Specific standards for KARMA were defined and implemented based on feedback from our UX design expert.

* [General Guidelines](docs/KarmaStyles.png)
* [Create/Edit Page](docs/CreateScreen.png)
* [View Page](docs/ViewScreen.png)
* [List Page](docs/ListScreen.png)

Stanfield Systems also leveraged model-view-controller patterns provided in Angular 5 libraries and documentation.

#### Behavior (Test) Driven Development
_(RFI Requirement n)_

Stanfield Systems implements Test Driven development using an automated Behavior Driven Development process in accordance with our [Test Strategy](https://github.com/StanfieldSystems/KARMA/wiki/Initial_Test_Strategy).  We use [Cucumber](https://cucumber.io/docs) as the BDD framework, [Serenity](http://www.thucydides.info/) for reporting, [REST Assured](https://github.com/serenity-bdd/serenity-documentation/blob/master/src/asciidoc/serenity-rest.adoc) for making REST calls, and [Selenium](http://www.seleniumhq.org/) for driving the web.  

* [BDD Process and Configuration](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#prototypebdd-project)
* [KARMA BDD Test Results](https://s3-us-west-2.amazonaws.com/stanfield-systems-karma-ci/karma-angular/index.html)

Stanfield Systems also generates automated JUnit tests for complete coverage of the application code.  

* [Unit Testing Process and Configuration](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#unit-testing)
* [KARMA Unit Test Results]

#### Project Management and Collaboration

Stanfield Systems uses the [Jira](https://www.atlassian.com/software/jira) issue tracking system to manage product and sprint backlogs.

* Major features of the application are tracked as _Epics_ 
* Specific user requirements are tracked as _User Stories_.
* Design, development, administrative, or management activities are tracked as _Tasks_ or _Subtasks_
* Defects and technical debt are tracked as _Bugs_ or _Issues_
* Risks are tracked as _Risks_

Stanfield Systems uses the [Confluence](https://www.atlassian.com/software/confluence) project collaboration portal to manage artifacts created during the development process.  For the KARMA prototype, these artifacts are also incorporated into GitHub - as files in the _docs_ folder of the code repository or as markdown articles in the project _Wiki_.

## Construction

The core of Stanfield Systems' agile delivery process is the [Construction Phase](https://github.com/StanfieldSystems/KARMA/wiki/Construction) during which the team performs detailed planning, design, implementation, and testing activities in one week Sprints - with working code at the end of each sprint. 

### KARMA Prototype Sprint Summary

In developing the KARMA prototype, Stanfield Systems completed 4 Sprints.  The goals for each sprint are summarized here.

* Sprint 1
    * Implement knowledge article creation and editing for logged-in user 
    * Design a dashboard for a logged-in user to view relevant information (See [Initial Concept](docs/KarmaDashboardConcept.pdf))
* Sprint 2
    * Implement initial version of the dashboard
    * Create design guidelines for user interface to create or edit a knowledge article
* Sprint 3
    * Finish implementing dashboard by incorporating user interface design recommendations and guidelines
    * Implement features to create knowledge article templates and types 
    * Create test data
    * Develop usability test plan
* Sprint 4 (2 days for construction, 3 days for transition)
    * Conduct initial usability test
    * Implement remaining user interface guidelines
    * Address issues identified during usability test

As evidenced in these Sprint Goals, there were multiple design-build iterations with the findings from one sprint being incorporated into improved designs and implementations in subsequent sprints. A summary of this design evolution can be found in [Design Evolution](docs/KarmaDesignEvolution.pdf).

Detailed user stories and tasks performed during each sprint, along with the remaining product backlog, are listed in a [Jira Report].

### Usability Testing

Once a testable version was completed, our usability expert created a [Test Plan](docs/KarmaUsabilityTestPlan.pdf), which contained the objectives of the test, outlined the logistics and listed the tasks and questions to be used.

Usability testing was conducted in-person with three content consumer/editor users.  Each test participant was brought to the testing room where they used functioning software on a laptop to complete the test.  This usability test utilized both qualitative and quantitative research methods since the users were asked to accomplish a task, which had a value of pass/fail/struggle.  Test participants were also asked a series of follow up questions which provided quantitative data as to their satisfaction of the product and their experience.  [Usability Test Results](docs/KarmaDashboardUsabilityFindings.pdf) were documented, discussed with the product team, and used to generate new user stories in the product backlog to improve usabililty in future iterations.

### Sprint Analysis

Stanfield Systems estimated the relative size of work using [Story Points](https://github.com/StanfieldSystems/KARMA/wiki/Estimating_and_Monitoring_Performance_with_Story_Points).  Our average sprint velocity was 27 story points with a high of 31 in Sprint 1 and a low of 23 in Sprint 3.  In Sprint 3 we fell short of our Sprint Planning commitment of 35 story points.  During our Sprint Review and Assessment for Sprint 3, our analysis of the variance pointed to increased emphasis and time spent on usability - which we had not accurately estimated during our Backlog Refinement and Sprint Planning.

## Transition

Stanfield Systems agile delivery process completes each release with a short [Transition Phase](https://github.com/StanfieldSystems/KARMA/wiki/Transition) that confirms the product is ready for release.  For the KARMA, transition activities were incorporated into Sprint 4.  These activities were primarily focused on validating prototype behavior and preparing documentation in response to the PQVP RFI.
