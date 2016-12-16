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
  - **Obtain all applications for offerID:** `GET` to `/applications/offers/<offerID>`
  - **Create application:** `POST` to `/applications`
    + _studentID (QP)_
    + _partnerID (QP)_
    + _offerID (QP)_
  - **Obtain application state:** `GET` to `/applications/<applicationID>/state`
  - **Accept/refuse application:** `PUT` to `/applications/<applicationID>`
    + _accept (QP)_ - Boolean indicating if it's an acceptance or a rejection

### Actors
  - **Obtain actor's profile:** `GET` to `/actors/<actorID>`

### Class Coordinators
  - **Obtain class coordinator's profile:** `GET` to `/classCoordinators/<coordinatorID>`
  - **Create class coordinator:** `POST` to `/classCoordinators`
    + _name (QP)_ - Class coordinator's name
    + _password (QP)_ - Class coordinator's password
    + _email (QP)_ - Class coordinator's email
    + _year (QP)_ - Class coordinator's year
    + _pathway (QP)_ - Class coordinator's pathway
    + _group (QP)_ - Class coordinator's group name

### FSD
  - **Obtain FSD's profile:** `GET` to `/fsd`
  - **Create FSD:** `POST` to `/fsd`
    + _password (QP)_ - FSD's password
    + _email (QP)_ - FSD's email
    + _group (QP)_ - FSD's group name

### Partners
  - **Obtain partner's profile:** `GET` to `/partners/<partnerID>`
  - **Create partner:** `POST` to `/partners`
    + _name (QP)_ - Partner's name
    + _password (QP)_ - Partner's password
    + _email (QP)_ - Partner's email
    + _address (QP)_ - Partner's address
    + _telephone (QP)_ - Partner's telephone
    + _location (QP)_ - Partner's location
    + _group (QP)_ - Partner's group name

### Students
  - **Obtain student's profile:** `GET` to `/students/<studentID>`
  - **Create student:** `POST` to `/students`
    + _name (QP)_ - Student's name
    + _password (QP)_ - Student's password
    + _email (QP)_ - Student's email
    + _year (QP)_ - Student's year
    + _pathway (QP)_ - Student's pathway
    + _address (QP)_ - Student's address
    + _telephone (QP)_ - Student's telephone
    + _group (QP)_ - Student's group name

#### CVs
  - **Download CV:** `GET` to `/students/cvs/<cvID>`
  - **Upload CV:** `POST` to `/students/<studentID>/cvs`
	+ _name (QP)_ - CV's name
  - **Rename CV:** `PUT` to `/students/cvs/<cvID>`
    + _name (QP)_ - CV's new name
  - **Delete CV:** `DELETE` to `/students/cvs/<cvID>`

### Database (ONLY FOR TESTING)
  - **Close database session:** `DELETE` to `/database`
  - **Open _TEST_ database session:** `PUT` to `/database`
