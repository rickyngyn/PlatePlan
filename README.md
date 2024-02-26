# Project Repository Overview

Welcome to our project repository, where we compile all submissions related to our ongoing project. This repository is organized into various branches, each representing a different phase or iteration of our project, making it straightforward for stakeholders to navigate and review progress.

## Branch Organization

- **Main Branch:** The main branch serves as the most stable version of our project, encompassing fully integrated and functional iterations. It currently includes Iterations 0, 1, and the database integration for Iteration 2. While the main branch offers a working version of the project, please be aware that it may not contain comprehensive documentation. For detailed documentation, it's advisable to explore the specific iteration branches.
  
- **Iteration Branches:** We have segregated our work into iteration-specific branches, namely `ITR 0`, `ITR 1`, and `ITR 2`. These branches allow for a focused view of the project's development at each stage.

### Branch Details

- **ITR 0 (Iteration 0):** This branch is dedicated to the initial planning and documentation phase of our project. It includes our vision statement and records from customer meetings, setting the groundwork for the project.

- **ITR 1 (Iteration 1):** Here, we delve into the implementation of our first major user stories. This branch is enriched with a wiki, log files detailing meeting minutes, an overview of the project including architectural diagrams, setup instructions, technical details, and testing reports.

- **ITR 2 (Iteration 2):** The focus of Iteration 2 is on the ongoing development of features promised for this phase. It's important to note that some features in this branch may be incomplete. For evaluation purposes, the main branch should be referenced for a complete and functional iteration.

# Postgres Database Integration Setup

Follow these steps to set up the Postgres database integration for your project.

## Step 1: Download Postgres

Download Postgres 16 for your operating system:

- [Download for Mac](https://www.postgresql.org/download/macosx/)
- [Download for Windows](https://www.postgresql.org/download/windows/)

## Step 2: Create Database

Create a database named `PlatePlan` with `postgres` as the user.

## Step 3: Import Database Backup

1. Go into the query executor and select **Import from file**.
2. The database backup can be found under the directory `\PlatePlan\SQL Files` with a file named `PlatePlanBackup.sql`.

This backup will set up all the tables, including sample data for business and customer logins.
