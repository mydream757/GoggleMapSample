package com.joonyoung.gms.controller;

import android.content.Context;
import android.util.Log;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;

/** 엑셀 파일을 다루는 컨트롤러 클래스
 * - 컨트롤러 클래스라 부르는 이유?
 *   - 프론트를 개발할 때 사용되는 가장 기본적인 '디자인 패턴'인 MVC패턴의 c에서 유래하였다.
 *   - Controller란, '데이터를 품는 클래스'(=Model) 객체와 '화면을 그려주는 클래스'(=View) 사이에서 둘을 조종한다.
 *   - 따라서 이 클래스는 Excel 파일을 조종하는 클래스라고 할 수 있다.
 */
public class ExcelController {
    Context context;

    /** 기본 생성자
     *  - 클래스가 default(기본으로) 가지고 있는 생성자
     *  - 생성자를 따로 '정의'하지 않은 경우, 자동 정의된다.
     *  - 아래 예시는 Context 객체를 인자로 받는 생성자를 '오버로드'했으므로
     *    이런 경우는 "기본 생성자를 사용하려면 직접 정의해주어야한다.
     */
    //기본 생성자
    public ExcelController(){

    }

    /** 오버로드한 생성자
     *  - 오버로드란, 이름이 같지만 취하는 인자(=파라미터)가 다르게 정의한 함수
     *  - 이름만 같을 뿐, 취하는 인자가 달라 따로 정의가 되기 때문에 둘은 '다른 함수'이다.
     * @param context : 실행되고 있는 안드로이드 애플리케이션 객체의 정보를 가지고 있다.
     *                  예를 들면, 프로젝트의 디렉토리 구조와 같은 정보로,
     *                  이를 통해 손쉽게 프로젝트 내부의 경로(디렉토리)에 접근할 수 있다.
     */
   public ExcelController(Context context){
        this.context = context;
    }

    /** `fileName`이름의 엑셀 파일을 읽어온다.
     *  - 엑셀 파일은 반드시 'assets' 디렉토리 하위에 위치해야한다.
     *  - 엑셀 파일의 데이터를 읽은 뒤, 가로 한 줄의 데이터를 HashMap<String,String> 객체에 담아 ArrayList 안에 저장하여 반환한다.
     *  - HashMap<String, String>을 쓰는 이유는 한 데이터 안의 field가 key, value 로 대응되기 때문이다.
     *  - 예를 들면 아래 엑셀 예시에서, '코드'가 `1`인 가로줄의 데이터는
     *   {
     *      코드 : '1',
     *      주소 : '서울시...',
     *      여부 : 'Y'
     *  }
     *  로 객체화(=여기선 HashMap 객체 하나에 위 데이터를 할당하는 행위) 할 수 있다.
     * @param fileName
     * @return ArrayList<HashMap<String,String>>
     */

    /** !!!!!!! 심각한 사항 발견 !!!!!!!
     *  - jxl 라이브러리는 .xlsx 파일은 읽을 수 없다. 따라서 이를 위해서는 poi라이브러리로 다시 코딩해야한다.
     *  - 근데 poi를 안드로이드에서 사용할 때는 일부 에러가 발생한다고 한다.(호환성이 좋지 않다는 말)
     */
    public ArrayList readExcel(String fileName){
       //결과를 담아줄 ArrayList이다.
       ArrayList resultArrayList = new ArrayList<HashMap<String,String>>();
        try{
            /** - InputStream은 본인이 작성한 코드 상의 데이터가 아니라, 그 이외의 데이터 유형을 받기(파일을 읽는다고 함) 위한
             *  클래스이다.
             *  - 이와 반대로 데이터를 내보내고 싶을 때(파일을 쓰거나 생성한다고 함)는 OutputStream 클래스를 이용한다.
             * @from: https://coding-factory.tistory.com/281
             */
            InputStream inputStream = context.getResources().getAssets().open(fileName); //`fileName`의 이름을 가진 엑셀 파일을 '연다'


            /** 엑셀 파일을 '열어도' 엑셀의 형식대로 처리하려면 자바 자체로는 처리하기 힘들며, 따라서 외부 라이브러리인 'jxl'을 사용하였다.
             * @검색 : 구글 -> "안드로이드 엑셀 파일 읽기"
             */
            /** - Workbook 클래스는 '엑셀 파일을 읽을 수 있도록 처리해주는' 외부 라이브러리의 클래스이다.
             *  - 해당 프로젝트에 적용에 참고한 출처는 아래에 있다.
             * @from : https://aries574.tistory.com/35
             */
            Workbook workbook = Workbook.getWorkbook(inputStream);

            //`fileName` 이름의 엑셀 파일이 있다면?
            if(workbook!=null){

                /** TODO: 만약 존재하는 모든 시트의 데이터를 얻어야한다면 아래 코드를 수정해야한다.
                 *  - 간단한 동작 구현을 위해 getSheet 함수에 파라미터로 0(index)을 주어 '첫번째 시트'만 읽도록 되어있다.
                 *  - 만약 모든 시트를 read하려면,
                 *    - 엑셀 파일의 시트 개수를 구하고
                 *    - 반복문(for / while)을 실행하여 각각 데이터를 파싱(분석이란 의미로, 읽어온 데이터를 그에 맞게 분류하는 행위)한 후
                 *      배열이나 ArrayList같은 클래스 객체에 저장해주면 될 것 같다.
                 */


                //1. 엑셀 파일의 첫번째 sheet를 불러온다.
                Sheet sheet = workbook.getSheet(0);

                //sheet가 존재한다면
                if(sheet !=null){
                    /** 엑셀파일 예시
                     *  코드    |   주소    |   여부
                     *  ----------------------------
                     *    1     | 서울시..  |    Y
                     *  -----------------------------
                     *    2     | 경기도..  |    Y
                     *    ----------------------------
                     *    3     | 서울시..  |    Y
                     *    ----------------------------
                     *    ...
                     */

                    /** 먼저 전체 컬럼을 갯수를 가져온다.
                     *  - 컬럼이란 각각의 '세로줄'을 뜻한다.
                     *  - 보통 데이터의 field 를 컬럼으로 나타낸다.
                     *  - 위 예시에서 코드(세로줄), 주소(세로줄), 여부(세로줄)가 각각 컬럼이다.
                     *  - 전체 컬럼의 갯수를 가져오는 이유는, 데이터의 field가 몇개인 지 먼저 확인하기 위해서이다.
                     */
                    int colTotal = sheet.getColumns();
                    /** 이제 전체 row를 가져온다.
                     *  - row란 각각의 '가로줄'을 뜻한다.
                     *  - 여러 필드 데이터를 가진 '하나의 데이터'를 나타낸다.
                     *  - 위 예시에서 1(가로줄), 2(가로줄), 3(가로줄)이 각각 로우이다.
                     *  - 전체 row의 갯수를 가져오는 이유는, '데이터의 갯수'를 파악하기 위함이다.
                     */
                    int rowTotal = sheet.getColumn(colTotal-1).length;
                    /** 이 프로젝트에 사용된 엑셀파일은, 첫째 가로줄에 field 목록이 나열되어 있다.(일반적인 엑셀 구성)
                     *  이 field 목록을 저장해놓고, 두번째 가로줄부터 데이터를 저장할 때 field 이름을 불러오기 위해서
                     *  fieldNameList라는 객체를 초기화하여 사용한다.
                     *  - 왜 ArrayList를 사용하는가?
                     *     - 아래 코드를 보면 각각의 field(=column, 세로줄)의 값을 읽어올 때 field_index라는 특정 인덱스를 통해 불러온다.
                     *     - 따라서, index를 통해 값을 읽는 배열이나 ArrayList에 field 리스트를 저장해놓으면
                     *       field_index 하나로, 그에 대응하는 field의 이름과 값을 한번에 읽을 때 사용할 수 있다.
                      */
                    ArrayList<String> fieldNameList = new ArrayList<String>();

                    /** 가로줄 한 줄 씩 반복해서 읽는다.
                     *  예를 들어, 아래 for문이 실행되면 제일 먼저 첫번째 '가로줄'을 읽어온다.
                     *  그러면, 위 예시의 경우 [코드,주소,여부] 를 읽게 될 것이다.
                     *  그 다음부터는 각각 가로줄을 하나씩 읽어올 것이다.
                     */
                    Log.d("EXCEL_CONTROLLER:col:", colTotal +"");
                    Log.d("EXCEL_CONTROLLER:row:", rowTotal +"");

                    for(int data_index=0; data_index<rowTotal; data_index++){
                        /** 세로 한 줄 씩 반복해 읽는다.
                         *  - 여기서 '세로를 읽는다'는 의미는 가로 한줄의 세로(열)을 하나씩 읽는다는 것이다.
                         *  - 결론적으로, 엑셀의 한 셀을 읽게 된다.
                         */
                        HashMap a_data = new HashMap<String,String>();
                        for(int field_index=0; field_index<colTotal; field_index++){
                            //(column, row) 좌표의 셀의 값을 가져온다.
                            String cellValue = sheet.getCell(field_index, data_index).getContents();
                            Log.d("EXCEL_CONTROLLER:", cellValue);
                            /** 인덱스가 0일 때는 엑셀 파일의 첫번째 가로줄을 의미한다.
                             *  위에서 설명한 것처럼, 첫번째 줄은 field 이름이 나열되어있으므로
                             *  fieldNameList에 따로 저장하고
                             *  그 다음 줄부터는 결과를 저장할 ArrayList에 저장한다.
                             */
                            if(data_index==0){
                                fieldNameList.add(cellValue);
                            }else{
                                String fieldName = fieldNameList.get(field_index);
                                a_data.put(fieldName, cellValue);
                                //a_data.put(fieldNameList.get(field_index), cellValue); 이렇게 써도 위와 동일한 작동을 한다.
                            }
                            //결과를 저장할 arrayList에 HashMap객체를 저장(.add()함수로)한다.
                        }
                        if(data_index!=0) resultArrayList.add(a_data);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("EXCEL_CONTROLLER:", e.toString());
        }
        /** - for문을 정상적으로 수행했다면, resultArrayList 객체에는 엑셀 파일에서 읽은 데이터들이 HashMap 객체에 파싱되어
         *  저장되어있을 것이다.
         *  - 만약 실패했다면 위의 catch문으로 이동해 에러가 발생했을 것이다.
         *  - 따라서, for문을 도는 도중 에러가 발생했다면, resultArrayList에는 모든 결과가 담기지 않을 수 있다.
         */
        return resultArrayList;
    }
}
