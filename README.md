# Knowledge Article Record Management Application (KARMA) - Technical Approach

**Site URL:  https://acceptance.bigbluesign.com**

Stanfield Systems applies a [Scrum-Based, Disciplined Agile Delivery (DAD)](https://github.com/StanfieldSystems/KARMA/wiki) life-cycle for product development and release.  Stanfield Systems' DAD life-cycle is referenced throughout this documented with links to relevant process descriptions in the [KARMA GitHub Wiki](https://github.com/StanfieldSystems/KARMA/wiki).

## Inception

Stanfield Systems begins product development with a short [Inception Phase](https://github.com/StanfieldSystems/KARMA/wiki/Inception)  to achieve concurrence on the direction the team will take to deliver the product.  For the KARMA prototype, this phase lasted a single sprint (Sprint 0) and included the following activities.

### Form Team
(_RFI Requirements a, b_)

Applying our [guidelines for forming agile teams](https://github.com/StanfieldSystems/KARMA/wiki/Form_and_Evolve_Team), Stanfield Systems established a six person team to develop the KARMA prototype and prepare documentation for the RFI response.  This multidisciplinary and collaborative team includes 8 of the PQVP AD-DS Labor Categories as specified below.

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
(_RFI Requirements c, d_)

_(Tie in [Explore Initial Scope](https://github.com/StanfieldSystems/kmt/wiki/Explore_Initial_Scope) process description)_

Applying user-centered design techniques Stanfield Systems worked directly with users to understand what the users need to perform their tasks.  These user-centered techniques address the entire user experience from start to finish.

_(Summarize techniques and results here.  Reference process and result artifacts.)_

Based on our user-centered research and the prototype requirements from the RFI, we identified high-level user stories which were documented in our Jira project as epics forming the initial product backlog.

### Technical Strategy and Work Environment
(_RFI Requirements e, f, g, h, k, l, m, o, p, r, t_)

For the KARMA prototype, Stanfield Systems' technical strategy is based on their [Work Environment Standards](https://github.com/StanfieldSystems/KARMA/wiki/Work_Environment_Standards).  

#### Technical Architecture and Technologies

Stanfield Systems implements a multi-tiered architecture as shown in the figure below. Our standard architecture and corresponding technologies are described in our [Technical Architecture](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#technical-architecture) along with examples.

![Architecture](docs/Architecture.jpg)

The Presentation tier runs in a client browser and is implemented with [Model-View-Controller JavaScript patterns using Angular 5](https://github.com/StanfieldSystems/KARMA/wiki/Technical_Architecture#angular4client-project). The Angular Client uses HTTP to make calls to the RESTful API on the application server, using JSON to exchange information between the client and the application server.

RESTful web services are implemented on the application server as Java components using the [Spring Boot framework](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#springbootangularintegration-project). Application server includes several application tiers, as follows.

* The **application** tier implements controller classes with methods that define the rest end points and services. Controller objects control application activity that occurs when a rest services is invoked via the JSON interface. Controller objects implement the Spring Web MVC Controller API.

* The **service** tier implements service classes with methods that define business  data management services. Service objects define the scope of business transactions within the application, defining the steps to complete a transaction and persist or retrieve the results as appropriate. Service objects validate data inputs and relationships, enforce user authorization, and handle exceptions. Service objects implement the Spring Service API.

* The **data access** tier implements repository classes to manage the retrieval and storage of business information from the persistent data repository.  Repository objects leverage the object-relational mapping annotations in the Entity Objects to map business objects to database tables using the Java Persistence API (JPA). Repository objects implement the Spring Data Repository API.

Information is exchanged between components in the different tiers using entity objects, data transfer objects, and additional parameters as necessary.

* **Entity Objects** are in-memory representations of business entities that are persisted in the database. Relational database tables and columns are mapped to entity objects using the Java Persistence API (JPA) 2.0 annotations. 

* **Data Transfer Objects (DTOs)** are convenience objects used to group data together for transfer between components or processes. More specifically, DTOs are used for converting data from one or more entity objects to JSON when the mapping from entities to JSON data structures is somewhat complex.

In this multi-tier architecture, some services are shared across multiple services. These are depicted in the figure above as **Infrastructure Components**.

* [Logging](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#logging) is implemented with **Log4j2**.
* [**Swagger**](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#swagger-2) is used to describe and document RESTful APIs

Business information is persisted in a [PostgreSQL relational database](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#postgresql).  

#### Version Control

All application code files, including database scripts, are stored in stored and managed in this **GitHub** version control repository.

[**Flyway**](https://github.com/StanfieldSystems/KARMA/wiki/Technical-Architecture#flyway) is used to migrate versioned database changes to developer, integration, and production database servers within the continuous integration and build pipeline.

#### Accessibility
#### Style Guide
#### Behavior (Test) Driven Development


#### Project Management and Collaboration

