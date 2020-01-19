# Assignment - Backend Developer
## Application
We would like you to create a RESTful Java based backend application. \
The application will allow a user to input data about a list of stocks. \
It should also track changes in a stocks price when the user updates it. \
It should contain the following endpoints:

* `GET /api/stocks`\
return a list of stocks

* `GET /api/stocks/{id}` \
 return one stock from the list

* `GET api/stocks/{id}/history` \
return the historical price changes of a stock

* `PUT /api/stocks/{id}` \
update the price of a single stock

* `POST /api/stocks` \
create a stock

The list of stocks should be created in memory on application startup.
The `Stock` object contains at least the fields:
- `id`: Number
- `name`: String
- `currentPrice`: Amount
- `lastUpdate`: Timestamp

Use the frameworks as you see fit to build and test this.
## Nice to have
We would also like you to create a front-end which shows the stock list.
NOTE: Please do not use a generator (like yeoman), because then it is very
difficult for us to determine what you have written yourself and what parts are
generated.
## Implementation
Treat this application as a real MVP that should go to production.