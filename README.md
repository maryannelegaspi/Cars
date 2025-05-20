# Cars

Cars is an application that running on Java 17 and Springboot 3.4.5. This is a system that uploads data from both CarBrand.csv and CarType.xml file to build a working car model. Both files must be present in src/main/resources to be able to run the application.

CarApplication provides an API that can be tested and interacted with using Swagger UI and Postman.

## How to Use Swagger to Test the Application

### Prerequisites

- To be able to test this application you must clone the repository and run the application using your preferred IDE.
- Ensure the application is up and running.
- You must have access to the Swagger UI, which is available at `http://localhost:8088/car-inventory/swagger-ui/index.html`

### Steps to Use Swagger UI

1. **Access Swagger UI**
    - Open your browser and navigate to the Swagger UI endpoint:
      ```
      http://localhost:8088/car-inventory/swagger-ui/index.html
      ```

2. **Available Endpoints**
    - Once Swagger UI loads, you will see a list of available API endpoints which is also listed below:
   1. /car-inventory/v1/cars/search - This API accepts a JSON @ModelAttribute which is used as a filter to get the available cars uploaded in the system. The response body will be on JSON format.
   2. /car-inventory/v1/cars/output - This API accepts a JSON @ModelAttribute which is used as a filter to get the cars uploaded in the system. User can use different formats for the output type which are JSON, XML and Table Formats
       - Each endpoint uses the HTTP method GET and the required parameters.

3. **Test an Endpoint**
    - Click on the endpoint you want to test. For example, if you want to test a `GET /car-inventory/v1/cars/search` endpoint, go to the `GET /car-inventory/v1/cars/search` section.
    - You'll see a button to "Try it out". Clicking on this will open fields where you can provide required input (**RequestBody** please see below sample).

4. **Execute the Request**
    - After providing the necessary parameters(please see below for sample RequestBody), click on the "Execute" button to send the request.
    - Swagger UI will display the response directly in the browser, including:
        - The HTTP status code (e.g., `200 OK`, `404 Not Found`, `500 Internal Server Error`).
        - The response body, which could be in JSON, XML, or another format.

### Sample RequestBody
{
    "brand": "Nissan",
    "releaseDate": "2023-08-22",
    "price": 100,
    "currency": "USD",
    "sort": "brand",
    "order": "ASC"
}
A sample postman collection is also uploaded in the project root folder in **postman/Cars.postman_collection.json** which you can use to test the application.

### Troubleshooting

- **404 Not Found**: This means the requested endpoint does not exist. Double-check the endpoint path and method.
- **400 Bad Request**: This means there was an issue with the request body or parameters. Ensure the format and fields are correct.
