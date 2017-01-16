# AMS
AMS Module - Internship Managment System - 5IL Project PDL/SOA - Murez, Adelin, Ciruzzi, Andrianoasy, Nguyen

## Other modules
See the [OMS Module](https://github.com/pierromumu/PartnerOffers).

## REST API
In this module, in order to access to it, we have developed several Web Services using REST protocol. For this reason, we had to define our Restful API:

_We use the next abbreviations: HP=Header Parameter, QP=Query Parameter, B=Body (In JSON format)_

_In all the cases, a userId must be provided as a HP named `id`_

### Applications
  - **Obtain all applications for user:** `GET` to `/applications`
    + _id (QP)_ - ID of the user whom applications want to obtain
  - **Obtain all applications for offerID:** `GET` to `/applications/offers/<offerID>`
  - **Create application:** `POST` to `/applications`
    + _studentID (QP)_
    + _partnerID (QP)_
    + _offerID (QP)_
    + _cvID (QP)_
    + _coverLetter (B)_ - Text plain string (**Not JSON**)
  - **Obtain application state:** `GET` to `/applications/<applicationID>/state`
  - **Accept/refuse application:** `PUT` to `/applications/<applicationID>`
    + _accept (QP)_ - Boolean indicating if it's an acceptance or a rejection

### Internship Agreements
  _Note: The agreementID is the same as the applicationID_
  - **Obtain all agreements for user:** `GET` to `/agreements`
    + _id (QP)_ - ID of the user whom agreements want to obtain
  - **Download agreement PDF:** `GET` to `/agreements/<agreementID>`
  - **Obtain agreement state:** `GET` to `/agreements/<agreementID>/state`
  - **Accept/refuse agreement:** `PUT` to `/agreements/<agreementID>`
    + _accept (QP)_ - Boolean indicating if it's an acceptance or a rejection

### Actors
  - **Obtain actor's profile:** `GET` to `/actors/<actorID>`

### Class Coordinators
  - **Obtain class coordinator's profile:** `GET` to `/classCoordinators/<coordinatorID>`
  - **Create class coordinator:** `POST` to `/classCoordinators`
    + _name (B)_ - Class coordinator's name
    + _password (B)_ - Class coordinator's password
    + _email (B)_ - Class coordinator's email
    + _year (B)_ - Class coordinator's year
    + _pathway (B)_ - Class coordinator's pathway

### FSD
  - **Obtain FSD's profile:** `GET` to `/fsd`
  - **Create FSD:** `POST` to `/fsd`
    + _password (B)_ - FSD's password
    + _email (B)_ - FSD's email

### Partners
  - **Obtain partner's profile:** `GET` to `/partners/<partnerID>`
  - **Obtain partner's profile by name:** `GET` to `/partners/names/<partnerName>`
  - **Create partner:** `POST` to `/partners`
    + _name (B)_ - Partner's name
    + _password (B)_ - Partner's password
    + _email (B)_ - Partner's email
    + _address (B)_ - Partner's address
    + _telephone (B)_ - Partner's telephone
    + _location (B)_ - Partner's location

### Students
  - **Obtain student's profile:** `GET` to `/students/<studentID>`
  - **Create student:** `POST` to `/students`
    + _name (B)_ - Student's name
    + _password (B)_ - Student's password
    + _email (B)_ - Student's email
    + _year (B)_ - Student's year
    + _pathway (B)_ - Student's pathway
    + _address (B)_ - Student's address
    + _telephone (B)_ - Student's telephone

#### CVs
  - **Download CV:** `GET` to `/students/cvs/<cvID>`
  - **Get CVs list:** `GET` to `/students/<studentID>/cvs`
  - **Upload CV:** `POST` to `/students/<studentID>/cvs`
	+ _name (QP)_ - CV's name
  - **Rename CV:** `PUT` to `/students/cvs/<cvID>`
    + _name (QP)_ - CV's new name
  - **Delete CV:** `DELETE` to `/students/cvs/<cvID>`

### Database (ONLY FOR TESTING)
  - **Close database session:** `DELETE` to `/database`
  - **Open _TEST_ database session:** `PUT` to `/database`
