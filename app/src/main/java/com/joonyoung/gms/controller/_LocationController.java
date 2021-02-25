package com.joonyoung.gms.controller;

import android.content.Context;

import com.joonyoung.gms.model._Location;

import java.util.ArrayList;
import java.util.HashMap;

public class _LocationController {
    final String FILE_NAME = "sampleDataXLS.xls";
    final String COLUMN_CODE = "코드";
    final String COLUMN_LOCATION = "주소";
    final String COLUMN_ISEXIST = "여부";
    ExcelController excelController;
    public _LocationController(Context context){
        this.excelController = new ExcelController(context);
    }
    public ArrayList<_Location> getLocationData(){
        ArrayList<_Location> locations = new ArrayList<_Location>();
        ArrayList<HashMap<String,String>> excel_arrayList = this.excelController.readExcel(FILE_NAME);
        /** 경고: forEach는 sdk_version 24부터 지원한다. 따라서 build.gradle의 defaultConfig 항목에서
         *  minSdkVersion 24 로 설정해야한다.
         */

        /** HashMap<String, String> 으로 되어있는 데이터를 Location 클래스 모델로 맵핑하는 과정이다.
         *  - 맵핑이란, 원래 있던 것을 새로운 곳으로 '적절하게 맞춰' 옮긴다고 생각하면 된다. (정확한 의미는 '대응시킨다.'라는 의미)
         */
        //excel_arrayList의 원소를 각각 꺼내서(a_data) 각각을 Location 클래스 객체로 맵핑한다.
        excel_arrayList.forEach(a_data -> {
            _Location a_location = new _Location();
            /** HashMap 객체의 get()함수와 ArrayList 객체의 get()함수의 차이
             *  - HashMap은 key로 value를 가져온다. 따라서, HashMap은 내가 지정한 key의 자료형에 따라서
             *    get()함수 인자의 자료형이 달라진다.(여기서는 String 자료형)
             *  - ExcelController.java 파일의 153줄에서 사용된 ArrayList의 get()은 인덱스를 인자로 받는다.
             *    따라서 int 자료형을 받는다.
             */
            String code = a_data.get(COLUMN_CODE);
            String location = a_data.get(COLUMN_LOCATION);
            String isExist = a_data.get(COLUMN_ISEXIST);

            a_location.setCode(code);
            a_location.setLocation(location);
            //Location 객체의 IsExist는 Boolean으로 저장하기로 정했기 때문에, 아래와 같이 변환해준다.
            if(isExist!=null && isExist.equals("Y")){
                a_location.setIsExist(true);
            }else{
                a_location.setIsExist(false);
            }
            //HashMap -> Location 객체로 맵핑이 끝났으니, 이것을 결과ArrayList에 저장한다.
            locations.add(a_location);
        });

        return locations;
    }
}
