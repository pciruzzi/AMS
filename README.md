# AMS
AMS Module - Internship Managment System - 5IL Project PDL/SOA - Murez, Adelin, Ciruzzi, Andrianoasy, Nguyen

## Other modules
See the [OMS Module](https://github.com/pierromumu/PartnerOffers).

## REST API
In this module, in order to access to it, we have developed several Web Services using REST protocol. For this reason, we had to define our Restful API:

_We use the next abbreviations: HP=Header Parameter, QP=Query Parameter_

_In all the cases, a userId must be provided as a HP named `id`_

### Applications
  - **Obtain all applications for user:** `GET` to `/applications`
    + _id (QP)_ - ID of the user whom applications want to obtain
  - **Create application:** `POST` to `/applications`
    + _studentID (QP)_
    + _coordinatorID (QP)_
    + _partnerID (QP)_
    + _offerID (QP)_
  - **Obtain application state:** `GET` to `/applications/<applicationID>/state`
  - **Accept/refuse application:** `PUT` to `/applications/<applicationID>`
    + _accept (QP)_ - Boolean indicating if it's an acceptance or a rejection

### Class Coordinators
  - **Obtain class coordinator's profile:** `GET` to `/classCoordinators/<coordinatorID>`
    + _id (QP)_ - ID of the class coordinator whom profile want to obtain
  - **Create class coordinator:** `POST` to `/classCoordinators`
    + _name (QP)_ - Class coordinator's name
    + _password (QP)_ Class coordinator's password
    + _email (QP)_ Class coordinator's email
    + _year (QP)_ - Class coordinator's year
    + _pathway (QP)_ - Class coordinator's pathway

### FSD
  - **Obtain FSD's profile:** `GET` to `/fsd/<fsdID>`
    + _id (QP)_ - ID of the FSD whom profile want to obtain
  -**Create FSD:** `POST` to `/fsd`
    + _password (QP)_ FSD's password
    + _email (QP)_ FSD's email

### Partners
  - **Obtain partner's profile:** `GET` to `/partners/<partnerID>`
    + _id (QP)_ - ID of the partner whom profile want to obtain
  - **Create partner:** `POST` to `/partners`
    + _name (QP)_ - Partner's name
    + _password (QP)_ Partner's password
    + _email (QP)_ Partner's email
    + _address (QP)_ - Partner's address
    + _telephone (QP)_ - Partner's telephone
    + _location (QP)_ - Partner's location

### Students
  - **Obtain student's profile:** `GET` to `/students/<studentID>`
    + _id (QP)_ - ID of the student whom profile want to obtain
  - **Create student:** `POST` to `/students`
    + _name (QP)_ - Student's name
    + _password (QP)_ Student's password
    + _email (QP)_ Student's email
    + _year (QP)_ - Student's year
    + _pathway (QP)_ - Student's pathway
    + _address (QP)_ - Student's address
    + _telephone (QP)_ - Student's telephone

### Database (ONLY FOR TESTING)
  - **Close database session:** `DELETE` to `/database`
  - **Open _TEST_ database session:** `PUT` to `/database`
