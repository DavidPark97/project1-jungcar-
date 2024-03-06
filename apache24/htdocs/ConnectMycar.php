<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$user_idx =  $_POST['user_idx'];
$plnumber =  $_POST['plnumber'];



$now = date("Y-m-d H:i:s");



if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "select car_idx from car where plnumber = '$plnumber'");

$rowCnt= mysqli_num_rows($result);

if($rowCnt!=0){
    $car = mysqli_fetch_array($result);
    $car_idx = $car['car_idx'];
}else{
    $result3=mysqli_query($con, "select * from refer where vhrno='$plnumber'");
    $refer = mysqli_fetch_array($result3);

    $fuel_name = $refer['use_fuel_nm']." ".$refer['dspval']."cc";

    $result2=mysqli_query($con, "select max(car_idx)+1 as car_idx from car");
    $car = mysqli_fetch_array($result2);
    $car_idx = $car['car_idx'];

    $vhrno = $refer['vhrno'];
    $cnm = $refer['cnm'];
    $trvl_dstnc = $refer['trvl_dstnc'];

    $result4=mysqli_query($con,"insert into car (car_idx,mileage,fuel_idx,connection_idx,plnumber) values 
    ($car_idx,$trvl_dstnc,(select fuel_idx from fuel where fuel_name = '$fuel_name'),(select connection_idx from connection where model_idx = (select model_idx from model where year ='$cnm') and opt_grade_idx=0),
    '$vhrno')");  

    $result5=mysqli_query($con, "insert into car_own (user_idx,car_idx,regdate) values ($user_idx,$car_idx,'$now')");
    
    
}

$arr=array("car_idx" => $car_idx);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>