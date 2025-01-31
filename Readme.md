# Swipe Android Assignment App

This Android app is a product listing and product addition application, built as part of the Swipe Android Assignment. It allows users to view a list of products and add new products to the list. The app uses modern Android technologies and best practices, including MVVM architecture, Retrofit for REST API communication, KOIN for Dependency Injection, and Lifecycle for Kotlin coroutines.

## Features

- View a list of products with details like product name, product type, price, and tax rate.
- Search for products based on their names.
- Scroll through the list of products.
- Add a new product with the product name, product type, selling price, and tax rate.
- Optionally, add an image for the new product with a 1:1 ratio in JPEG or PNG format.

## Prerequisites

- Android Studio 4.1 or later
- Android SDK 27 or later
- Kotlin 1.3.72 or later

## Getting Started

Follow these steps to get the app up and running on your local machine.

1. Clone the repository to your local machine using Git:

```bash
git clone https://github.com/ajitpatel28/SwipeAndroidAssignment
```

2. Open Android Studio and select "Open an existing Android Studio project."

3. Navigate to the cloned project directory and select the `swipe-android-assignment` folder to open the project.

4. Wait for Gradle to sync and build the project.

5. Connect an Android device or start an Android emulator with API level 27 or later.

6. Run the app on the connected device or emulator using the Run button in Android Studio.

## How to Use the App

1. The app opens to the product listing screen, where you can see the list of products, please make sure your device has a proper internet connection.

2. Use the search bar at the top to search for products based on their names, type, price and tax.

3. Scroll through the list to view all products.

4. To add a new product, click on the "Add Product"  at the bottom navigation.
5. On the Add Product screen, enter the product name, select the product type from the dropdown, and enter the selling price and tax rate.

6. Optionally, click on the "Attachment Icon" button to select an image for the new product.

7. Click on the "Add Product" button to add the product. You will see a success message if the product is added successfully.



### Note
Currently Image is not being uploaded to api as I can't figure out where to upload the image in the cloud and retrieve image link to store cloud image address in Product object.

Please elaborate me about this so I could implement this functionality.
## API Endpoint



## Fetching Product Data API Endpoint

The app communicates with the following API endpoint to fetch the list of products:

- Endpoint: https://app.getswipe.in/api/public/get
- Method: GET

The expected response from the API endpoint should be an array of JSON objects, each representing a product with the following fields:

- `image`: The URL of the product image (in case no image is available, a default image URL can be used).
- `price`: The selling price of the product (a floating-point number).
- `product_name`: The name of the product (text).
- `product_type`: The type of the product (text).
- `tax`: The tax rate of the product (a floating-point number).

The app uses Retrofit to make the GET request and Gson to parse the JSON response into data objects.


## Adding Product Data API Endpoint

The app communicates with the following API endpoint for adding new products:

- Endpoint: https://app.getswipe.in/api/public/add
- Method: POST
- Body Type: form-data
- Parameters:
    - `product_name`: Product Name (text)
    - `product_type`: Product Type (text)
    - `price`: Selling Price (text)
    - `tax`: Tax Rate (text)
    - `files[]`: Images (OPTIONAL)

## Libraries and Technologies Used

- Retrofit: For making API calls.
- KOIN: For dependency injection.
- Android Lifecycle: For managing coroutines and asynchronous tasks.
- Glide: For image loading and caching.
- ViewModel and LiveData: For implementing MVVM architecture.

## Contribution

If you find any issues or have suggestions for improvement, feel free to open an issue or submit a pull request. Contributions are welcome!


