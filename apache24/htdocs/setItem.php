<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$sell_idx = $_POST['sell_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select model.year, mileage, fuel_name as fuel, price, id, phone, detail,img,name,opt_grade_name, car_market.agree
from car,car_market,model,car_own,user,connection,fuel,opt_grade where car.car_idx = car_own.car_idx and car_own.user_idx = user.user_idx 
and model.model_idx = connection.model_idx and connection.connection_idx = car.connection_idx and car.car_idx = car_market.car_idx and connection.opt_grade_idx = opt_grade.opt_grade_idx
and fuel.fuel_idx = car.fuel_idx and sell_idx = $sell_idx";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>