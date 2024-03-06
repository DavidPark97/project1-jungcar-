<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$user_idx = $_POST['user_idx'];
$brand = $_POST['brand'];
$plnumber= $_POST['plnumber'];
$year = $_POST['model'];
$fuel_name = $_POST['fuel_name'];
$opt_grade_name = $_POST['opt_grade_name'];
$mileage = $_POST['mileage'];
$price = $_POST['price'];
$country = $_POST['country'];
$own = $_POST['own'];
$agree = $_POST['agree'];

$detail = $_POST['detail'];



$now = date("Y-m-d H:i:s");
$date = date("Y-m-d");

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result3 = mysqli_query($con, "select model_idx from model where year='$year'");
$mo = mysqli_fetch_array($result3);
$model_idx = $mo['model_idx'];

$result2 = mysqli_query($con, "select connection_idx from model, opt_grade, connection where model.model_idx = connection.model_idx and opt_grade.opt_grade_idx = connection.opt_grade_idx and model.year = '$year' and opt_grade_name='$opt_grade_name'");
$connection = mysqli_fetch_array($result2);
$connection_idx = $connection['connection_idx'];

$result5 = mysqli_query($con, "select fuel_idx from fuel where fuel_name='$fuel_name'");
$fuel = mysqli_fetch_array($result5);
$fuel_idx = $fuel['fuel_idx'];

$result9=mysqli_query($con, "select car_idx from car where plnumber = '$plnumber'");
$rowCnt9= mysqli_num_rows($result9);

if($rowCnt9!=0){
    $car = mysqli_fetch_array($result9);
    $car_idx = $car['car_idx'];
    $result6 = mysqli_query($con, "update car set mileage=$mileage, fuel_idx = $fuel_idx, connection_idx = $connection_idx where car_idx = $car_idx");

    $result10 = mysqli_query($con, "select car_idx from car_own where car_idx=$car_idx");
    $rowCnt10=0;
    $rowCnt10= mysqli_num_rows($result10);
    if($rowCnt10==0){
        mysqli_query($con,"insert into car_own (user_idx,car_idx) values ($user_idx,$car_idx)");
    }

}else{
    $result4 = mysqli_query($con, "select ifnull(max(car_idx)+1,1) as cnt from car");
    $car = mysqli_fetch_array($result4);
    $car_idx = $car['cnt'];
    $result6 = mysqli_query($con, "insert into car (car_idx,mileage,fuel_idx,connection_idx,plnumber) values($car_idx,$mileage,$fuel_idx,$connection_idx,'$plnumber')");
    $result8 = mysqli_query($con, "insert into car_own (user_idx,car_idx,regdate) values($user_idx,$car_idx,'$now')");
}
$result=mysqli_query($con, "select ifnull(max(sell_idx)+1,1) as sell_idx from car_market");
$sell = mysqli_fetch_array($result);
$sell_idx = $sell['sell_idx'];



$result7 = mysqli_query($con, "insert into car_market (sell_idx,car_idx,price,country,own,detail,agree,date) values($sell_idx,$car_idx,$price,'$country','$own','$detail','$agree','$date')");


$arr=array("connection_idx" => $connection_idx,"sell_idx"=>$sell_idx,"car_idx"=>$car_idx);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>