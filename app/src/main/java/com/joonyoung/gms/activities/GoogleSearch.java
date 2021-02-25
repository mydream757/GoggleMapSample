package com.joonyoung.gms.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.joonyoung.gms.R;
import com.joonyoung.gms.adapter.LocationArrayAdapter;
import com.joonyoung.gms.controller.GoogleMapController;
import com.joonyoung.gms.controller._LocationController;
import com.joonyoung.gms.model._Location;

import java.util.ArrayList;

/** 선택한 항목의 구글맵을 표시해줄 View(화면) class
 * - View 클래스라 부르는 이유?
 *   - 프론트를 개발할 때 사용되는 가장 기본적인 '디자인 패턴'인 MVC패턴의 V에서 유래하였다.
 *   - View란, 화면을 보여주는 담당 클래스로, 필요에 따라 Controller를 통해 데이터(Model)를 다뤄
 *     화면에 표시하는 등의 역할을 한다.
 */

/** Google Map API 사용은 아래 출처를 참고하여 진행했습니다.
 * @from : https://webnautes.tistory.com/647
 */
public class GoogleSearch extends AppCompatActivity implements OnMapReadyCallback{
    _LocationController locationController;
    GoogleMapController googleMapController;
    ArrayList<_Location> locations;
    Spinner spinner_dropdown;
    TextView text_location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_search);
        /** excel 파일로부터 location 정보를 읽어와야한다.
         *  - 읽어온 정보는 locations 객체에 저장한다.
         */
        locationController = new _LocationController(this);
        locations = locationController.getLocationData();
        /** View 컴포넌트를 초기화해줘야한다.
         */
        spinner_dropdown = findViewById(R.id.code_spinner);
        /** 간단한 String을 목록화할 것이라면 ArrayAdapter 객체를 생성하여 setAdapter()의 인자로 넣어주면 된다.
         *  - 하지만 이번 케이스의 경우, Location 클래스가 목록화 되어있으므로, Custom ArrayAdapter를 정의하여 사용하였다.
         */
        spinner_dropdown.setAdapter(new LocationArrayAdapter(this, locations));
        text_location = findViewById(R.id.location_text);

        Log.d("MAIN_ACTIVITY:LOCATION_SIZE:", locations.size() + "");
        locations.forEach((location)->{
            Log.d("DATA:CHECK:", location.toString());
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        setEvent();
    }

    /** implement
     *  - implement는 인터페이스(interface)를 구현하는 것이다.
     *  - 상속과 유사하지만 다른 점은, 인터페이스에 선언된 함수들을 '반드시' 임플리멘터에서 구현해야한다.
     *  - 임플리멘터는 여기서, GoogleSearch 클래스를 말한다. 이때의 구현은 @Override(오버라이드=재정의)라는
     *    방법을 통해 이뤄져야한다.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapController = new GoogleMapController(getApplicationContext(), googleMap);
    }

    public void setEvent(){
        Log.d("CALL_SETEVENT:", "OK");
        spinner_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SPINNER:SELECTED:", ": " + position);
                // onCreate()에서 setAdapter()해준 adapter를 참조하여, location 객체의 값을 가져와야한다.
                LocationArrayAdapter adapter = (LocationArrayAdapter) parent.getAdapter();
                _Location selectedLocation = (_Location) adapter.getItem(position);

                if(selectedLocation!=null){
                    /** 선택된 location을 참조하여 다음을 구현한다.
                     *  1. location 객체의 'location(=주소)'값을 텍스트뷰에 표시한다.
                     *  2. location 객체의 'location(=주소)'값을 가지고 Google map을 조작한다.
                     */
                    String locationValue = selectedLocation.getLocation();
                    //대상이 될 TextView를 참조한다.
                    Log.d("SPINNER:RESULT:", ": " + locationValue);
                    text_location.setText(locationValue);
                    //구글맵을 조작하여 장소를 보여준다.
                    googleMapController.Search(locationValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}