<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$year = $_POST['year'];




header('content-type: text/html; charset=utf-8');



$query = "select ifnull((select sum(charge) from oil_history where car_idx=$car_idx and year(date)=$year),0) as oil,
ifnull((select sum(charge) from maintain_history where car_idx=$car_idx and year(date)=$year),0) as maintain, 
ifnull((select sum(charge) from oil_history where car_idx=$car_idx and year(date)=$year) +
(select sum(charge) from maintain_history where car_idx=$car_idx and year(date)=$year),0) as total,
ifnull((select sum(distance) from drive_history where car_idx=$car_idx and year(date)=$year),0) as distance,
ifnull((select sum(charge/unit) from oil_history where car_idx=$car_idx and year(date)=$year),0) as amount,
ifnull(round((select sum(distance) from drive_history where car_idx=$car_idx and year(date)=$year)/
(select sum(charge/unit) from oil_history where car_idx=$car_idx and year(date)=$year),2),0) as effi from car where car_idx = $car_idx group by car_idx;";


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