package com.example.mobileassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call Function to get and save the coordinates in Database
        getAndSaveData();

    }

    public void getAndSaveData(){
        Context context = this;
            try {
                // Open the input file for reading
                InputStream inputStream = getResources().openRawResource(R.raw.coord);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                // Initialize Geocoder
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                // Process each line in the input file
                while ((line = reader.readLine()) != null) {
                    // Split the line into latitude and longitude
                    String[] latAndLong = line.split(",");
                    if (latAndLong.length == 2) {
                        double latitude = Double.parseDouble(latAndLong[0]);
                        double longitude = Double.parseDouble(latAndLong[1]);
                        // Geocode the coordinates to get the address
                        List<Address> ls = geocoder.getFromLocation(latitude, longitude, 1);
                        //Get the address of the given two values
                        for(Address addr: ls){
                            String address = addr.getAddressLine(0);
                            //Now that we have the address we call the database to save it
                            //Open Database to access
                            DatabaseServices dataSource = new DatabaseServices(context); // Initialize the data source
                            dataSource.open(); // Open the database
                            DataContainer data = new DataContainer(address,latitude,longitude);
                            //Insert note into database
                            long noteId = dataSource.insertData(data);
                            dataSource.close(); // Close the database when done
                        }
                    }
                }
                // Close the input file
                inputStream.close();

            }catch (IOException e){

            }
    }
    //Function to query the given address in Database and return lat and long values
    public void queryButtonClick(View v){
        //String array to capture longitudde and latitde
        String[] values;
        Context context = this;
        //Open Database to access
        DatabaseServices dataSource = new DatabaseServices(context); // Initialize the data source
        dataSource.open(); // Open the database
        //Get value from user
        EditText givenAddress = findViewById(R.id.queryInput);
        String address = givenAddress.getText().toString();
        //Get its values from database
        values = dataSource.getLatitudeAndLongitude(address);
        //Check if it exists or not
        if(values == null){
            Toast.makeText(getApplicationContext(),"Inputted Address not in Database", Toast.LENGTH_SHORT).show();
        }
        else{
            TextView lat = findViewById(R.id.displayLat);
            lat.setText("The latitude of the given address is: " + values[0]);
            TextView longitude = findViewById(R.id.displayLong);
            longitude.setText("The longitude of the given address is: " + values[1]);
        }
        givenAddress.setText("");
        dataSource.close(); // Close the database when done
    }

    public void addButtonClick(View v){
        Context context = this;
        // Initialize the Geocoder
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        //Get inputted address
        EditText inputAddress = findViewById(R.id.addressInput);
        String address = inputAddress.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);

            if (!addresses.isEmpty()) {
                Address selectedAddress = addresses.get(0);
                double latitude = selectedAddress.getLatitude();
                double longitude = selectedAddress.getLongitude();

                //Open Database to access
                DatabaseServices dataSource = new DatabaseServices(context); // Initialize the data source
                dataSource.open(); // Open the database
                DataContainer data = new DataContainer(address,latitude,longitude);
                //Insert note into database
                long noteId = dataSource.insertData(data);
                Toast.makeText(getApplicationContext(),"Address has been added", Toast.LENGTH_SHORT).show();
                inputAddress.setText("");
                dataSource.close(); // Close the database when done
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteButtonClick(View v){
        Context context = this;
        //Get the address to delete from user
        EditText givenAddress= findViewById(R.id.deleteAddressInput);
        String address =  givenAddress.getText().toString();
        //Open Database to access
        DatabaseServices dataSource = new DatabaseServices(context); // Initialize the data source
        dataSource.open(); // Open the database
        dataSource.deleteAddress(address);
        Toast.makeText(getApplicationContext(),"Address has been deleted", Toast.LENGTH_SHORT).show();
        givenAddress.setText("");
        dataSource.close(); // Close the database when done
    }

}