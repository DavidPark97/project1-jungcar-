package com.example.jungcar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parsing {
    String name="";
    String month="";
    String delivery="";
    String idx="";
    String count="";
    String mileage="";
    String price="";
    String date="";
    String country="";
    String label="";
    String img="";
    String user="";
    String address="";
    String distance="";
    String latitude="";
    String longitude="";
    String phone="";
    String detail="";
    String fuel="";
    String model="";
    String year="";
    String car="";
    String number="";
    String state="";
    String cond="";
    String total="";
    String effi="";
    String maintain="";
    String oil="";
    String charge="";
    String amount="";
    String memo="";
    String unit="";
    String center="";
    String diff="";
    String dist="";
    String rate="";
    String visit="";
    String brand="";
    String comment="";
    String plnumber="";
    String reco="";
    String content="";
    String type="";
    String next="";
    String pre="";
    String recomment_idx="";
    String comment_idx="";
    String user_idx="";
    String ranking="";
    String agree="";
    String board_idx="";
    String payment="";
    String place="";

    public void setCountParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        count = total.getString("cnt");

        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }

    public void editParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        detail = total.getString("detail");
        price = total.getString("price");

        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }

    public void optionparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("name");
        idx =recoObject.getString("idx");
    }

    public void setOptionParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("name");

    }



    public void connectionParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        idx = total.getString("connection_idx");
        name = total.getString("sell_idx");
        car = total.getString("car_idx");

        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }

    public void marketParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        idx = total.getString("market_idx");


    }



    public void countparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

         name=recoObject.getString("name");
         count=recoObject.getString("cnt");
    }

    public void SellCondParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("name");
    }

    public void itemParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray itemObject = jsonObject.getJSONArray("webnautes");
        JSONObject item = itemObject.getJSONObject(0);
        name=item.getString("name") + " " + item.getString("opt_grade_name");
        price=item.getString("price");
        mileage=item.getString("mileage");
        date=item.getString("year");
        img=item.getString("img");
        user=item.getString("id");
        phone=item.getString("phone");
        detail=item.getString("detail");
        fuel=item.getString("fuel");
        agree=item.getString("agree");

    }

    public void resParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx = recoObject.getString("sell_idx");
        name=recoObject.getString("name") + " " + recoObject.getString("opt_grade_name");
        price=recoObject.getString("price");
        mileage=recoObject.getString("mileage");
        date=recoObject.getString("year");
        country=recoObject.getString("country");
        label=recoObject.getString("label_idx");
        img=recoObject.getString("img");
        user=recoObject.getString("user_idx");
    }

    public void sellitemParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx = recoObject.getString("sell_idx");
        name=recoObject.getString("name") + " " + recoObject.getString("opt_grade_name");
        price=recoObject.getString("price");
        mileage=recoObject.getString("mileage");
        date=recoObject.getString("year");
        country=recoObject.getString("country");
        label=recoObject.getString("label_idx");
        img=recoObject.getString("img");
        count=recoObject.getString("cnt");
        if (count.equals("null")) {
            count = "0";
        }
    }

    public void sellpartParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx = recoObject.getString("market_idx");
        name=recoObject.getString("subject");
        price=recoObject.getString("price");
        date=recoObject.getString("date");
        img=recoObject.getString("img");
        count=recoObject.getString("cnt");
        if (count.equals("null")) {
            count = "0";
        }
    }

    public void mapParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx = recoObject.getString("center_idx");
        name=recoObject.getString("name");
        address=recoObject.getString("address");
        distance=recoObject.getString("distance");
        count=recoObject.getString("cnt");
        latitude=recoObject.getString("latitude");
        longitude=recoObject.getString("longitude");
        rate=recoObject.getString("rate");
        phone=recoObject.getString("tel");
        visit=recoObject.getString("visit");
    }

    public void imgParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray itemObject = jsonObject.getJSONArray("webnautes");
        JSONObject item = itemObject.getJSONObject(0);
        name=item.getString("name");
        idx=item.getString("cnt");

    }

    public void optableParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("option_type_name");
        detail=recoObject.getString("option_name");

    }

    public void mpPartParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");
        JSONObject recoObject = recoArray.getJSONObject(index);
        JSONArray itemObject = jsonObject.getJSONArray("cols");
        JSONObject item = itemObject.getJSONObject(0);

        name=recoObject.getString("subject");
        idx=recoObject.getString("market_idx");
        img=recoObject.getString("img");
        price=recoObject.getString("price");
        total=item.getString("cnt");
    }

    public void partboardparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");
        JSONObject recoObject = recoArray.getJSONObject(index);
        JSONArray itemObject = jsonObject.getJSONArray("cols");
        JSONObject item = itemObject.getJSONObject(0);


        name=recoObject.getString("subject");
        idx=recoObject.getString("market_idx");
        user=recoObject.getString("id");
        date=recoObject.getString("date");
        img=recoObject.getString("img");

        count=recoObject.getString("cnt");
        if(count.equals("null")){
            count="0";
        }
        number=recoObject.getString("number");
        if(number.equals("null")){
            number="0";
        }

        state=recoObject.getString("part_trade_idx");

        total=item.getString("cnt");
        cond=item.getString("type_name");

    }

    public void partSearchparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");
        JSONObject recoObject = recoArray.getJSONObject(index);


        name = recoObject.getString("subject");
        idx = recoObject.getString("market_idx");
        user = recoObject.getString("id");
        date = recoObject.getString("date");
        img = recoObject.getString("img");

        count = recoObject.getString("cnt");
        if (count.equals("null")) {
            count = "0";
        }
        number = recoObject.getString("number");
        if (number.equals("null")) {
            number = "0";
        }
        state = recoObject.getString("part_trade_idx");

    }

    public void imgparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        img=recoObject.getString("img");

    }

    public void shpartitemParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray itemObject = jsonObject.getJSONArray("webnautes");
        JSONObject item = itemObject.getJSONObject(0);
        name=item.getString("subject");
        idx=item.getString("part_trade_idx");
        user=item.getString("user_idx");
        img=item.getString("img");
        detail=item.getString("content");
        date=item.getString("date");
        price=item.getString("price");
        number=item.getString("number");
        cond = item.getString("type_name");
        state = item.getString("state");
        delivery=item.getString("delivery");
        board_idx=item.getString("board_idx");
    }

    public void setParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        idx = total.getString("car_idx");


        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }

    public void countParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        count = total.getString("cnt");


        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }

    public void setCarInfoParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        model = total.getString("year");
        name = total.getString("brand");
        number = total.getString("plnumber");

    }

    public void numparsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);


        count=recoObject.getString("num");
    }

    public void setDistParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        date = total.getString("date");
        count = total.getString("distance");
    }

    public void manageParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("type_name");
        distance=recoObject.getString("stdkm");
        date=recoObject.getString("stdmth");
        img=recoObject.getString("img");
        detail=recoObject.getString("type");
        idx=recoObject.getString("main_type");
    }

    public void manageParsing2(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("type_name");
        img=recoObject.getString("img");
        idx=recoObject.getString("main_type");
    }

    public void getCenterListParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("name");
        address=recoObject.getString("address");
        idx=recoObject.getString("center_idx");
    }

    public void addrParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
           address = total.getString("address");
    }

    public void toaddrParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
       longitude = total.getString("longitude");
        latitude = total.getString("latitude");
    }

    public void crashParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        idx = total.getString("crash_idx");


    }

    public void recyearParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("year");
        JSONObject total = totalObject.getJSONObject(0);
        effi= total.getString("effi");
        distance= total.getString("distance");
        charge= total.getString("charge");
        amount= total.getString("amount");
    }

    public void recmthParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("month");
        JSONObject total = totalObject.getJSONObject(0);
        effi= total.getString("effi");
        distance= total.getString("distance");
        charge= total.getString("charge");
        amount= total.getString("amount");
    }

    public void oilitemParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        total=recoObject.getString("total");
        date=recoObject.getString("date");
        memo=recoObject.getString("memo");
        charge=recoObject.getString("charge");
        amount=recoObject.getString("amount");
        unit=recoObject.getString("unit");
        if(memo.equals("null")){
            memo="";
        }
        idx=recoObject.getString("oil_idx");
    }

    public void maintainitemParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        name=recoObject.getString("type_name");
        date=recoObject.getString("date");
        charge=recoObject.getString("charge");
        center=recoObject.getString("name");
        idx=recoObject.getString("main_idx");
    }


    public void driveitemParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        total=recoObject.getString("total");
        date=recoObject.getString("date");
        memo=recoObject.getString("memo");
        distance=recoObject.getString("distance");
        if(memo.equals("null")){
            memo="";
        }
        idx=recoObject.getString("drive_idx");
    }

    public void crashitemParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        address=recoObject.getString("location");
        date=recoObject.getString("date");
        memo=recoObject.getString("memo");
        if(memo.equals("null")){
            memo="";
        }
        idx=recoObject.getString("crash_idx");
    }


    public void yearParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        year=recoObject.getString("year");

    }

    public void barChartParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        month=recoObject.getString("month");
        maintain =recoObject.getString("maintain");
        oil=recoObject.getString("oil");

    }

    public void lineChartParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        month=recoObject.getString("month");
        effi =recoObject.getString("effi");


    }

    public void setStatParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        effi= total.getString("effi");
        distance= total.getString("distance");
        oil= total.getString("oil");
        maintain = total.getString("maintain");
        number = total.getString("total");
        amount= total.getString("amount");
    }

    public void serSchParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("type_name");
        distance=recoObject.getString("stdkm");
        date=recoObject.getString("stdmth");
        img=recoObject.getString("img");
        detail=recoObject.getString("type");
        idx=recoObject.getString("main_type");
        diff=recoObject.getString("diff");
        dist=recoObject.getString("dist");
    }

    public void centerRevParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        comment=recoObject.getString("comment");
        date=recoObject.getString("date");
        visit=recoObject.getString("visit");
        rate=recoObject.getString("star");

    }

    public void BoardTypeParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        idx=recoObject.getString("board_type");
        name=recoObject.getString("name");
    }


    public void plnumberParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        plnumber= total.getString("plnumber");
        brand = total.getString("brand");
        model = total.getString("model");
        fuel = total.getString("fuel");
    }

    public void boardIdxParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        idx= total.getString("board_idx");
    }

    public void boardParsing(String json,int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx = recoObject.getString("board_idx");
        name=recoObject.getString("subject");
        date=recoObject.getString("date");
        number=recoObject.getString("shnum");
        count=recoObject.getString("cmtcnt");
        user=recoObject.getString("user");
    }

    public void shBoardParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);

        reco = total.getString("reco");
        name=total.getString("subject");
        date=total.getString("date");
        number=total.getString("shnum");
        count=total.getString("cmtcnt");
        user=total.getString("user");
        state=total.getString("state");
        content=total.getString("content");
    }

    public void boardContentParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        content=recoObject.getString("cont");
        type=recoObject.getString("type");

    }

    public void chgRecoParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        JSONObject flag = totalObject.getJSONObject(1);

        reco = total.getString("reco");
        state = flag.getString("flag");
    }

    public void chgBoardParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);


        pre = total.getString("pre");
        next = total.getString("next");
    }

    public void loginParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);


            idx = total.getString("user_idx");
    }
    public void commentParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        idx=recoObject.getString("comment_idx");
        user=recoObject.getString("id");
        state=recoObject.getString("state");
        reco=recoObject.getString("reco");
        comment=recoObject.getString("comment");
        date=recoObject.getString("date");
        ranking=recoObject.getString("ranking");
        user_idx=recoObject.getString("user_idx");
        recomment_idx=recoObject.getString("recomment_idx");



    }

    public void bestParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);
        idx=recoObject.getString("board_idx");
        name=recoObject.getString("subject");
        type=recoObject.getString("name");
        count=recoObject.getString("count");
        ranking=recoObject.getString("ranking");
    }

    public void sellCommentParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        idx=recoObject.getString("sell_idx");
        user=recoObject.getString("id");
        comment=recoObject.getString("comment");
        date=recoObject.getString("date");
        user_idx=recoObject.getString("user_idx");
        comment_idx=recoObject.getString("sell_comment_idx");
        recomment_idx=recoObject.getString("sell_recomment_idx");



    }

    public void qnaParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("subject");
        img=recoObject.getString("img");



    }

    public void PhoneParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);


        phone = total.getString("phone");

    }

    public void hotParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        label=recoObject.getString("label");
        idx=recoObject.getString("label_idx");
        state=recoObject.getString("state");


    }


    public void priceParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        name= total.getString("name");
        price = total.getString("price");
         }


    public void paymentParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        date=recoObject.getString("date");
        payment =recoObject.getString("payment");
    }

    public void mailParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        user= total.getString("id");

    }

    public void editDistParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        date= total.getString("date");
        memo=total.getString("memo");
        distance=total.getString("distance");

    }
    public void oilParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        date= total.getString("date");
        memo=total.getString("memo");
        charge=total.getString("charge");
        unit=total.getString("unit");
        place=total.getString("place");


    }

    public void mainParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        date= total.getString("date");
        idx=total.getString("center_idx");
        place=total.getString("name");


    }

    public void editCrashParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        date= total.getString("date");
        memo=total.getString("memo");
        place=total.getString("location");


    }


    public void editmanageParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);

        name=recoObject.getString("type_name");
        type=recoObject.getString("main_type");
        img=recoObject.getString("img");
        idx=recoObject.getString("main_idx");
        charge=recoObject.getString("charge");
    }

    public void crashImgParsing(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");

        JSONObject recoObject = recoArray.getJSONObject(index);


            img=recoObject.getString("img");

    }

    public void ReciptParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        name= total.getString("name");



    }

    public void searchParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray totalObject = jsonObject.getJSONArray("webnautes");
        JSONObject total = totalObject.getJSONObject(0);
        name = total.getString("name");
        count = total.getString("cnt");


        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }
}
