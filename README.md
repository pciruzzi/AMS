# AMS
AMS Module - Internship Managment System - 5IL Project PDL/SOA - Murez, Adelin, Ciruzzi, Andrianoasy, Nguyen

## Other modules
See the [OMS Module](https://github.com/pierromumu/PartnerOffers).

## REST API
In this module, in order to access to it, we have developed several Web Services using REST protocol. For this reason, we had to define our Restful API:

_In all the cases, a userId must be provided as a header parameter named `id`_

### Applications
  - **Obtain all applications:** `GET` to `/applications`
  - **Obtain application state:** `GET` to `/applications/<applicationID>/state`
  - **Create application:** `PUT` to `/applications`
    + _studentID_
    + _coordinatorID_
    + _partnerID_
    + _offerID_

### Class Coordinators
  - **Obtain class coordinator's profile:** `GET` to `/classCoordinators/<coordinatorID>`
  - **Create class coordinator:** `PUT` to `/classCoordinators`
    + _name_ - Class coordinator's name
    + _year_ - Class coordinator's year
    + _pathway_ - Class coordinator's pathway

### Partners
  - **Obtain partner's profile:** `GET` to `/partners/<partnerID>`
  - **Create partner:** `PUT` to `/partners`
    + _name_ - Partner's name
    + _address_ - Partner's address
    + _telephone_ - Partner's telephone

### Students
  - **Obtain student's profile:** `GET` to `/students/<studentID>`
  - **Create student:** `PUT` to `/students`
    + _name_ - Student's name
    + _year_ - Student's year

### Database (ONLY FOR TESTING)
  - **Close database session:** `DELETE` to `/database`
  - **Open _test_ database session:** `PUT` to `/database`
