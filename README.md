# Hair Salon

#### Epicodus Java exercise to practice using a database

#### By Sandro Alvarez

## Description

This is a hair salon app that allows an employee to add stylists, clients and edit them.

## Specifications

Input Behavior | Input | Output |
---------------|-------|--------|
creates a new stylist| "Chase", 33, "10 years of experience" | name: "Chase", Age: 33, Background: "10 years of experience" |
creates a client, assigned to a stylist | "Sandro", 23, "buzz cut" | name: "Sandro", Age: 23, Preferences: "buzz cut", Stylist: "Chase" |
edits a stylist| Age: 30 | name: "Chase", Age: 30, Background: "10 years of experience" |
edits a client | Preferences: "full shave" | name: "Sandro", Age: 23, Preferences: "full shave" |

### Routing

| Behavior                                                              | Path                                   | HTTP Verb | App.java Example                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | Process                                                                                                                                                                            |
|-----------------------------------------------------------------------|----------------------------------------|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Displays home page                                                    | /                                      | GET       | get("/", (request, response) -> {Map<String, Object> model = new HashMap<>(); model.put("stylists", Stylist.all()); model.put("template", "templates/index.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                                                                                                                                 | User requests page. Server returns home page.                                          |
| Displays stylist details for selected stylist                         | /stylist/:id/clients                   | GET       | get("/stylist/:id/clients", (request, response) -> {Map<String, Object> model = new HashMap<>(); Stylist stylist = Stylist.find(Integer.parseInt(request.params("id"))); model.put("stylist", stylist); model.put("template", "templates/stylist.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                                           | User requests page. Server collects selected stylist, renders template. Velocity loops through clients and displays them.                                                         |
| Displays client details for selected client                           | /stylist/:stylistId/client/:id         | GET       | get("/stylist/:stylistId/client/:id", (request, response) -> {Map<String, Object> model = new HashMap<>(); Client client = Client.find(Integer.parseInt(request.params(":id"))); model.put("client", client); model.put("template", "templates/client.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                                      | User requests page. Server collects selected client, renders template. Velocity displays client details.    |
| Displays stylist edit page for selected stylist                       | /stylist/:id/edit                      | GET       | get("/stylist/:id/edit", (request, response) -> {Map<String, Object> model = new HashMap<>(); Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id"))); model.put("stylist", stylist); model.put("template", "templates/stylist-edit.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                                        | User requests stylist edit form. Server collects selected stylist, renders template. Velocity displays stylist edit form.                                      |
| Displays client edit page for selected client                         | /stylist/:stylistId/client/:id/edit    | GET       | get("/stylist/:stylistId/client/:id/edit", (request, response) -> {Map<String, Object> model = new HashMap<>(); Client client = Client.find(Integer.parseInt(request.params(":id"))); model.put("client", client); model.put("template", "templates/client-edit.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                            | User requests client edit page. Server collects selected client, renders template. Velocity displays stylist edit form.                                       |
| Adds new stylist when new stylist form is submitted                   | /stylist/new                           | POST      | post("/stylists/new", (request, response) -> {Map<String, Object> model = new HashMap<>(); String name = request.queryParams("name"); String background = request.queryParams("background"); int age = Integer.parseInt(request.queryParams("age")); Stylist newStylist = new Stylist(name, background, age); newStylist.save(); model.put("stylists", Stylist.all()); model.put("template", "templates/index.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                              | User submits new stylist form. Server grabs attributes from form. Uses them to create new stylist. Server renders page with all stylists.|
| Adds new client to selected stylist when new client form is submitted | /clients/new                           | POST      | post("/clients/new", (request, response) -> {Map<String, Object> model = new HashMap<>(); String name = request.queryParams("name"); String preferences = request.queryParams("preferences"); int age = Integer.parseInt(request.queryParams("age")); int stylistId = Integer.parseInt(request.queryParams("stylistId")); Client newClient = new Client(name, preferences, age, stylistId); newClient.save(); model.put("stylists", Stylist.all()); model.put("template", "templates/index.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine()); | User submits new client form. Server grabs attributes from form. Uses them to create new client for selected stylist. Server renders page with all stylists listed.|
| Edits selected stylist when stylist edit form is submitted            | /stylist/:id/edited                    | POST      | post("/stylist/:id/edited", (request, response) -> {Map<String, Object> model = new HashMap<>(); Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id"))); model.put("stylist", stylist); stylist.updateName(request.queryParams("name")); stylist.updateAge(Integer.parseInt(request.queryParams("age"))); stylist.updateBackground(request.queryParams("background")); model.put("template", "templates/stylist.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                           | User submits stylist edit form. Server grabs attributes from form. Uses them to update details for selected stylist. Server renders page with details of selected stylist.|
| Deletes selected stylist when stylist delete form is submitted        | /stylist/:id/deleted                   | POST      | post("/stylist/:id/deleted", (request, response) -> {Map<String, Object> model = new HashMap<>(); Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id"))); stylist.delete(); model.put("stylists", Stylist.all()); model.put("template", "templates/index.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                                                                                  | User submits stylist delete form. Server grabs attributes from form. Uses them to delete selected stylist. Server renders home page.|
| Edits selected client when client edit form is submitted              | /stylist/:stylistId/client/:id/edited  | POST      | post("/stylist/:stylistId/client/:id/edited", (request, response) -> {Map<String, Object> model = new HashMap<>(); Client client = Client.find(Integer.parseInt(request.params(":id"))); model.put("client", client); client.updateName(request.queryParams("name")); client.updateAge(Integer.parseInt(request.queryParams("age"))); client.updatePreferences(request.queryParams("preferences")); model.put("template", "templates/client.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                | User submits client edit form. Server grabs attributes from form. Uses them to update details for selected client. Server renders page with details of selected client.|
| Deletes selected client when client delete form is submitted          | /stylist/:stylistId/client/:id/deleted | POST      | post("/stylist/:stylistId/client/:id/deleted", (request, response) -> {Map<String, Object> model = new HashMap<>(); Client client = Client.find(Integer.parseInt(request.params(":id"))); Stylist stylist = Stylist.find(client.getStylistId()); client.delete(); model.put("stylist", stylist); model.put("template", "templates/stylist.vtl"); return new ModelAndView(model, layout);}, new VelocityTemplateEngine());                                                                                                                                                  | User submits client delete form. Server grabs attributes from form. Uses them to delete selected client. Server renders stylist details page of deleted client.|

## Setup

* Clone this repository

* In PSQL:
  CREATE DATABASE hair_salon;

* In Terminal:
  psql media < media.sql

* Type gradle run in terminal and go to localhost:4567 in browser

## Technologies Used

* Java

* Gradle

* JUnit

* Spark

* Postgres

* SQL

* VelocityTemplateEngine

## Support

If you run into any problems, contact me at sandromateo22@gmail.com

### Legal

Copyright (c) 2016 Sandro Alvarez.

Licensed under the MIT license
