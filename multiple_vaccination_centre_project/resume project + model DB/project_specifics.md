### MANAGEMENT OF MULTIPLE VACCINATION CENTRE

This webapp was designed and realised as an examination project for
\'WEB ARCHITECTURE AND SYSTEMS DESIGN (code 13581)\', a subject of the
university course of study \'COMPUTER ENGINEERING (code 2035)\', taught
by Professor Marco La Cascia, professor at the University of Palermo,
with an exam score of 30/30 cum laude.

### 

**Introduction and description of web-app functionality**

The web app includes four protected areas, and one publicly accessible
area.

The protected areas are respectively:

-   area utente

-   area medico

-   area operatore Asp

-   area accettazione.

These areas are accessible by registered users with the corresponding
role.

The non-protected area, apart from the welcome page and the login and
registration pages, which for obvious reasons can be accessed publicly,
is the . "Sala d'aspetto" page.

Protected areas:

**Area utente:**

In this area it is possible to:

-   Booking a vaccination

-   Access the data of a pending reservation

-   Access the history of bookings made

-   Access your vaccination data

-   Display the QR-Code of the green pass for the last vaccination
    carried out

***Reservation of vaccine:***

This functionality allows you to book a vaccine at an available
vaccination centre in the selected area.

A form allows you to select in order, the region, the province and
possibly the municipality in which you wish to search for an available
vaccination centre.

If only the province is selected it will search for vaccine centres,
entered in the appropriate table of the DB, by an ASP operator, for the
whole province, while if the municipality is specified the search will
be more specific.

If the search returns one or more vaccination centres, it is possible to
select one of them in order to see the days on which it is possible to
book the vaccine.

Each vaccination centre has a number of available places, which vary
depending on the day .

Realistically, many similar vaccine-booking systems provide for direct
interaction by an operator, who is in charge of defining the days when
the public is open and on which it is possible to make a reservation.

In addition to entering the days open for booking, the possibility of
specifying by the ASP operator the number of places available per day
has been provided for. This information was deliberately linked to the
individual opening day to allow greater flexibility on the number of
places, which varies not only on the basis of the vaccination centre but
also in relation to the opening day.

The checks carried out during the booking phase include a check for any
pending bookings (status associated with a booking that has a date
greater than or equal to today\'s date, taken as 00:00, and which is not
completed) or if the maximum number of permissible administrations has
been reached.

Once the booking has been made, the booking will be displayed with the
relevant information directly on the same page.

***Access to booking data:***

It is possible to have one \'valid\' pending booking at a time, where
valid means a booking that is in reference to today\'s or future date
and that has not been confirmed, i.e. has not already been administered
and thus confirmed by the doctor.

It is therefore possible to access the booking information, including
the vaccination centre and date, for both a pending and past booking.

In the case of a pending booking, the relevant booking code will be
visible, which can be used to trace the booking if necessary.

The created code is defined by means of the hash function of java on the
user\'s data, such as first name, surname and social security number,
with a few adjustments to ensure that a code is defined in an easily
readable format.

***Access to data administration:***

If there is at least one administration, the data for this or these will
be displayed. Then there will be a summary of the data of the vaccine
administered, entered by the doctor who carried out the procedure, the
data of the vaccination centre where the vaccine was administered and
the code of the doctor who carried out the administration.

The QR-Code for the last administration carried out will be visible in
large size.

The Qr-Code is generated using a specific jQuery library from the
user\'s personal information, such as name, surname, tax code and
booking code, which can be retrieved by scanning the latter.

**Area medico:**

In this area it is possible to:

-   Request user authorisation

-   Search for a user by booking code

-   Search a list of users by vaccination centre

-   View user information

-   Fill in the form for entering the data of the vaccine administered

-   Confirm administration

***Request user authorisation:***

In order to access the functions of the protected area, after
registering, the first time you log in you will be asked to select the
vaccination centre where the service will be carried out and to which
the account will be linked by means of a special table.

Once the vaccination centre has been confirmed, the doctor\'s user name
will be confirmed by an authorised ASP operator. Once the necessary
internal checks have been carried out, the operator will be able to
proceed with the confirmation and therefore with the enabling of the
doctor\'s user account.

***Cercare un utente per codice di prenotazione***

With this functionality, it is possible to search for a user by means of
the booking code provided by the user.

In this way, the doctor can search for the user if the code is valid.

The booking code is validated if:

-   existing

-   concerning a reservation in the vaccination centre where the doctor
    is working

-   whether with reference to today\'s date

-   whether he/she has followed the procedure for shift entry after
    acknowledgement in reception

If it is valid, the doctor can view the user\'s personal data and
proceed with the administration.

***Search a list of users by vaccination centre:***

Similar procedure to the one described above, with the difference that
the doctor can search for the patient in the list of users who have been
entered in turn by the receptionist in the vaccination centre where the
doctor works.

***Fill in the form to enter the data of the vaccine administered and
confirm the administration:***

Once the user\'s data has been retrieved, it is possible to proceed with
filling in the form for the vaccine being administered.

Confirming the data confirms the administration of the vaccine to the
selected user.

Once confirmed, the user can access his private area to view the
administration data and the Qr-Code generated, which will act as a green
pass.

**Area Accettazione:**

In this area it is possible to:

-   Request user enablement

-   Place a user in turn

-   Put an unbooked user on shift

-   Display the shift in the room

***Requesting user authorisation:***

As with the doctor, before the functionality can be used, the
operator\'s user must be approved in reception by an authorised ASP
operator.

***Putting a user in turn:***

L'operatore può mettere a turno un utente che si presente in
accettazione.

Può quindi cercare tramite codice di prenotazione l'utente abilitato.

Il concetto di abilitato è uguale a quello descritto per la ricerca
dell'utente nell'area medico, considerando che la prenotazione dovrà
essere relativa a quella del centro vaccinale nella quale lavora
l'operatore dell'accettazione.

Una volta trovata la prenotazione è verificata quindi la validità
l'utente viene inserito a turno.

**Enter an unbooked user:**

If a user who is not booked but has an active user shows up, the
check-in operator can enter a new booking for today\'s date after
checking the validity of the user.

***Display the shift in the hall:***

The current shift number, confirmed and available room seats for the
reception operator\'s vaccination centre are shown.

The operator can then use this view by clicking on the appropriate icon,
which will open a sub-page showing the data on the screen. In this way,
the users in the room can see the shift number and get useful
information.

**Area ASP:**

In this area it is possible to::

-   Request user authorisation

-   Approve employee authorisation requests per vaccination centre

-   Enter new vaccination centre

-   Enter opening dates per vaccination centre

***Requesting authorisation of the user:***

Come per il medico e l'operatore in accettazione prima di poter
utilizzare le funzionalità deve essere approvata l'utente dell'operatore
da un altro operatore ASP abilitato.

***Cercare una lista di utenti per centro vaccinale:***

The ASP operator can search for any requests to enable employees from a
specific vaccination centre, or by province.

Once the request has been found, the operator can access the personal
data of the employee who is applying for authorisation, and once the
necessary checks have been made on the user, he or she can enable the
request.

***Enter a new vaccination centre:***

The Asp operator may enter a new vaccination centre, specifying the
region, province, municipality, name and address where it is located.

No two vaccination centres can have the same combination of information.

***Enter the opening dates per vaccination centre and number of places
available::***

The Asp operator can search for a vaccination centre and enter its
opening dates.

In addition, for each opening day entered he can specify the number of
places available on that date.

Unprotected areas:

**"Turno Sala" area:**

L'area turno sala visualizza le informazioni del tabellone della sala
d'attesa, per uno specifico centro vaccinale selezionato, con
aggiornamento della pagina ogni 10 secondi.

**Utility area of the web app:**

It includes the welcome page, log-in/log-out and registration.

### SYSTEM ARCHITECTURE

Technologies used:

1.  Java servlet classes, for the acceptance of http requests, for the
    construction of the corresponding response web pages and for the
    validation and processing of the data entered by the client

2.  JSP for the main views, home and category views, and for building
    JSON files used for AJAX calls

3.  REALMS for defining personal areas for doctor, user, asp, acceptance
    roles

4.  HTML, CSS and JavaScript, JQuery, for user interface management

5.  JQuery library jquery.qrcode.min.js, popular library for generating
    Qr_Codes

6.  Bootstrap and fontawesome for the layout

7.  Apache Tomcat, as middleware for web application deployment

8.  MySQL was used as relational database

9.  The MVC approach was used, for AJAX requests, login, registration
    and the main functionalities of the web app, especially the
    functionalities on the main pages of the various protected areas

10. JSON for AJAX calls as Controller response in view of the MVC
    approach

11. DAO (Data Access Object) pattern for defining the database access
    architecture and for loading SQL queries from a properties file. We
    proceeded by defining the DBManager class for opening the connection
    to the DB, loading the properties file containing the queries and
    retrieving them. We defined an abstract class called QueryAbstract,
    which takes care of requesting the connection to the DB via
    DBManager, defining the preparedStatement, \'unpacking\' the DB
    result by creating a list of a generic type, and also defining an
    abstract method which will be implemented by all the classes
    implementing QueryAbstract.

Finally, the above-mentioned classes were created to define the
interaction between the DB and the controller servlet, using the
corresponding Bean according to the MVC model as information transport.

12. Utility class for managing the date and time, such as the conversion
    of the timestamp format from string and vice versa, and others, and
    class for \'centralised\' management of exceptions, with logging in
    the console by means of the appropriate java Logger class.

13. XML for the vaccination data file for the ASP operator.

### DATA MODEL

DB logic diagram.

![](./images_md/media/image1.png){width="5.882638888888889in"
height="5.311111111111111in"}
